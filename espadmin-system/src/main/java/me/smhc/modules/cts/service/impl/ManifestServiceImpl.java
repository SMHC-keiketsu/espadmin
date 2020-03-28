package me.smhc.modules.cts.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import me.smhc.exception.BadRequestException;
import me.smhc.modules.cts.domain.Manifest;
import me.smhc.modules.cts.repository.ManifestRepository;
import me.smhc.modules.cts.service.ManifestService;
import me.smhc.modules.cts.service.dto.ManifestDto;
import me.smhc.modules.cts.service.dto.ManifestQueryCriteria;
import me.smhc.modules.cts.service.mapper.ManifestMapper;
import me.smhc.modules.master.domain.Agency;
import me.smhc.modules.master.domain.ExcelConfig;
import me.smhc.modules.system.domain.Dept;
import me.smhc.modules.system.service.DeptService;
import me.smhc.modules.system.service.UserService;
import me.smhc.modules.system.service.dto.DeptDto;
import me.smhc.modules.system.service.dto.UserDto;
import me.smhc.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
* @author jhf
* @date 2020-03-24
*/
@Service
//@CacheConfig(cacheNames = "manifest")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ManifestServiceImpl implements ManifestService {

    private final ManifestRepository manifestRepository;

    private final ManifestMapper manifestMapper;

    private final UserService userService;

    private final DeptService deptService;

    @Value("${file.path}")
    private String path;

    @Value("${file.maxSize}")
    private long maxSize;

    public ManifestServiceImpl(ManifestRepository manifestRepository, ManifestMapper manifestMapper, UserService userService, DeptService deptService) {
        this.manifestRepository = manifestRepository;
        this.manifestMapper = manifestMapper;
        this.userService = userService;
        this.deptService = deptService;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(ManifestQueryCriteria criteria, Pageable pageable){
        Page<Manifest> page = manifestRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(manifestMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<ManifestDto> queryAll(ManifestQueryCriteria criteria){
        return manifestMapper.toDto(manifestRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public ManifestDto findById(Long id) {
        Manifest manifest = manifestRepository.findById(id).orElseGet(Manifest::new);
        ValidationUtil.isNull(manifest.getId(),"Manifest","id",id);
        return manifestMapper.toDto(manifest);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public ManifestDto create(Manifest resources) {
        // if Agency is not setted
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        if(ObjectUtil.isNull(resources.getDept().getId())){
            Dept dept = new Dept();
            dept.setId(userDto.getDept().getId());
            resources.setDept(dept);
        }
        resources.setCreateUserId(userDto.getId());
        resources.setUpdateUserId(userDto.getId());
        return manifestMapper.toDto(manifestRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(Long deptId, MultipartFile multipartFile) {
        // check File Size
        FileUtil.checkSize(maxSize, multipartFile.getSize());

        ExcelReader reader = ExcelUtil.getReader(FileUtil.toFile(multipartFile));
        List<Map<String,Object>> readAll = reader.readAll();
        if(ObjectUtil.isNull(readAll)){
            throw new BadRequestException("Excel解析失敗");
        }
        try {
            // get excel config
            UserDto userDto = userService.findByName(SecurityUtils.getUsername());
            // if Agency dept is not setted
            if(ObjectUtil.isNull(deptId)){
                deptId = userDto.getDept().getId();
            }
            DeptDto deptDto = deptService.findById(deptId);
            if(ObjectUtil.isNotNull(deptDto)){
                Agency agency = deptDto.getAgency();
                ExcelConfig excelConfig = agency.getExcelConfig();
                JSONObject jsonObject =  new JSONObject(excelConfig.getMainFestExcel());
                for (Map<String,Object> row: readAll) {
                    Manifest manifest = new Manifest();
                    Dept dept = new Dept();
                    dept.setId(deptId);
                    manifest.setDept(dept);
                    manifest.setMawbNo(row.get(jsonObject.getStr("mawbNo")).toString());
                    manifest.setFlightNo(row.get(jsonObject.getStr("flightNo")).toString());
                    Date date = DateUtil.parse(row.get(jsonObject.getStr("flightDate")).toString());
                    manifest.setFlightDate(date);
                    manifest.setHawbNo(row.get(jsonObject.getStr("hawbNo")).toString());
                    manifest.setPcs(Integer.parseInt(row.get(jsonObject.getStr("pcs")).toString()));
                    manifest.setWeight(new BigDecimal(row.get(jsonObject.getStr("weight")).toString()));
                    manifest.setWeightCode(row.get(jsonObject.getStr("weightCode")).toString());
                    manifest.setProductName(row.get(jsonObject.getStr("productName")).toString());
                    if(jsonObject.getStr("invoiceConditionCode") != null){
                        manifest.setInvoiceConditionCode(row.get(jsonObject.getStr("invoiceConditionCode")).toString());
                    }
                    if(jsonObject.getStr("invoiceConditionCode") != null) {
                        manifest.setInvoiceIso(row.get(jsonObject.getStr("invoiceIso")).toString());
                    }
                    if(jsonObject.getStr("invoiceValue") != null) {
                        manifest.setInsuranceValue(new BigDecimal(row.get(jsonObject.getStr("invoiceValue")).toString()));
                    }
                    if(jsonObject.getStr("fareClassificationCode") != null) {
                        manifest.setFareClassificationCode(row.get(jsonObject.getStr("fareClassificationCode")).toString());
                    }
                    if(jsonObject.getStr("fareIso") != null) {
                        manifest.setFareIso(row.get(jsonObject.getStr("fareIso")).toString());
                    }
                    if(jsonObject.getStr("fareValue") != null) {
                        manifest.setFareValue(new BigDecimal(row.get(jsonObject.getStr("fareValue")).toString()));
                    }
                    if(jsonObject.getStr("insuranceIso") != null) {
                        manifest.setInsuranceIso(row.get(jsonObject.getStr("insuranceIso")).toString());
                    }
                    if(jsonObject.getStr("insuranceValue") != null) {
                        manifest.setInsuranceValue(new BigDecimal(row.get(jsonObject.getStr("insuranceValue")).toString()));
                    }
                    if(jsonObject.getStr("importerName") != null) {
                        manifest.setImporterName(row.get(jsonObject.getStr("importerName")).toString());
                    }
                    if(jsonObject.getStr("importerPosterCode") != null) {
                        manifest.setImporterPosterCode(row.get(jsonObject.getStr("importerPosterCode")).toString());
                    }
                    if(jsonObject.getStr("importerTel") != null) {
                        manifest.setImporterTel(row.get(jsonObject.getStr("importerTel")).toString());
                    }
                    if(jsonObject.getStr("importerAddr1") != null) {
                        manifest.setImporterAddr1(row.get(jsonObject.getStr("importerAddr1")).toString());
                    }
                    if(jsonObject.getStr("importerAddr2") != null) {
                        manifest.setImporterAddr2(row.get(jsonObject.getStr("importerAddr2")).toString());
                    }
                    if(jsonObject.getStr("importerAddr3") != null) {
                        manifest.setImporterAddr3(row.get(jsonObject.getStr("importerAddr3")).toString());
                    }
                    if(jsonObject.getStr("importerAddr4") != null) {
                        manifest.setImporterAddr4(row.get(jsonObject.getStr("importerAddr4")).toString());
                    }
                    if(jsonObject.getStr("shipperName") != null) {
                        manifest.setShipperName(row.get(jsonObject.getStr("shipperName")).toString());
                    }
                    if(jsonObject.getStr("shipperPosterCode") != null) {
                        manifest.setShipperPosterCode(row.get(jsonObject.getStr("shipperPosterCode")).toString());
                    }
                    if(jsonObject.getStr("shipperTel") != null) {
                        manifest.setShipperTel(row.get(jsonObject.getStr("shipperTel")).toString());
                    }
                    if(jsonObject.getStr("shipperAddrAll") != null) {
                        manifest.setShipperAddrAll(row.get(jsonObject.getStr("shipperAddrAll")).toString());
                    }
                    if(jsonObject.getStr("origin") != null) {
                        manifest.setOrigin(row.get(jsonObject.getStr("origin")).toString());
                    }
                    if(jsonObject.getStr("trackingNo") != null) {
                        manifest.setTrackingNo(row.get(jsonObject.getStr("trackingNo")).toString());
                    }
                    if(jsonObject.getStr("deliveryPosterCode") != null) {
                        manifest.setDeliveryPosterCode(row.get(jsonObject.getStr("deliveryPosterCode")).toString());
                    }
                    if(jsonObject.getStr("deliveryDestination") != null) {
                        manifest.setDeliveryDestination(row.get(jsonObject.getStr("deliveryDestination")).toString());
                    }
                    if(jsonObject.getStr("deliveryTel") != null) {
                        manifest.setDeliveryTel(row.get(jsonObject.getStr("deliveryTel")).toString());
                    }
                    if(jsonObject.getStr("deliveryContact") != null) {
                        manifest.setDeliveryContact(row.get(jsonObject.getStr("deliveryContact")).toString());
                    }
                    if(jsonObject.getStr("deliveryAddrAll") != null) {
                        manifest.setDeliveryAddrAll(row.get(jsonObject.getStr("deliveryAddrAll")).toString());
                    }
                    if(jsonObject.getStr("deliveryAddr1") != null) {
                        manifest.setDeliveryAddr1(row.get(jsonObject.getStr("deliveryAddr1")).toString());
                    }
                    if(jsonObject.getStr("deliveryAddr2") != null) {
                        manifest.setDeliveryAddr2(row.get(jsonObject.getStr("deliveryAddr2")).toString());
                    }
                    if(jsonObject.getStr("deliveryAddr3") != null) {
                        manifest.setDeliveryAddr3(row.get(jsonObject.getStr("deliveryAddr3")).toString());
                    }
                    if(jsonObject.getStr("deliveryAddr4") != null) {
                        manifest.setDeliveryAddr4(row.get(jsonObject.getStr("deliveryAddr4")).toString());
                    }
                    if(jsonObject.getStr("billingMethod") != null) {
                        manifest.setBillingMethod(row.get(jsonObject.getStr("billingMethod")).toString());
                    }
                    if(jsonObject.getStr("packaging") != null) {
                        manifest.setPackaging(row.get(jsonObject.getStr("packaging")).toString());
                    }
                    if(jsonObject.getStr("cashOnDeliveryAmount") != null) {
                        manifest.setCashOnDeliveryAmount(new BigDecimal(row.get(jsonObject.getStr("cashOnDeliveryAmount")).toString()));
                    }
                    manifest.setCreateUserId(userDto.getId());
                    manifest.setUpdateUserId(userDto.getId());
                    manifestRepository.save(manifest);
                }
                return true;
            }else {
                throw new BadRequestException("Excel解析失敗");
            }
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Manifest resources) {
        Manifest manifest = manifestRepository.findById(resources.getId()).orElseGet(Manifest::new);
        ValidationUtil.isNull( manifest.getId(),"Manifest","id",resources.getId());
        manifest.copy(resources);
        manifestRepository.save(manifest);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            manifestRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ManifestDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ManifestDto manifest : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("代理商名", manifest.getDept().getName());
            map.put("MAWB番号", manifest.getMawbNo());
            map.put("FlightNO", manifest.getFlightNo());
            map.put("FlightDate", manifest.getFlightDate());
            map.put("HAWB番号", manifest.getHawbNo());
            map.put("個数", manifest.getPcs());
            map.put("重量", manifest.getWeight());
            map.put("重量コード", manifest.getWeightCode());
            map.put("品名", manifest.getProductName());
            map.put("インボイス価格条件コード", manifest.getInvoiceConditionCode());
            map.put("INVOICE通貨 CURRENCY", manifest.getInvoiceIso());
            map.put("INVOICE金額 DECLARED VALUE OF CUSTOMS", manifest.getInvoiceValue());
            map.put("運賃区分コード", manifest.getFareClassificationCode());
            map.put("運賃通貨", manifest.getFareIso());
            map.put("運賃金額", manifest.getFareValue());
            map.put("保険通貨", manifest.getInsuranceIso());
            map.put("保険金額", manifest.getInsuranceValue());
            map.put("荷受人名", manifest.getImporterName());
            map.put("荷受人郵便番号", manifest.getImporterPosterCode());
            map.put("荷受人一括住所", manifest.getImporterAddrAll());
            map.put("荷受人住所１", manifest.getImporterAddr1());
            map.put("荷受人住所２", manifest.getImporterAddr2());
            map.put("荷受人住所３", manifest.getImporterAddr3());
            map.put("荷受人住所４", manifest.getImporterAddr4());
            map.put("荷受人TEL", manifest.getImporterTel());
            map.put("荷送人名", manifest.getShipperName());
            map.put("荷送人郵便番号", manifest.getShipperPosterCode());
            map.put("荷送人一括住所", manifest.getShipperAddrAll());
            map.put("荷送人TEL", manifest.getShipperTel());
            map.put("原産国", manifest.getOrigin());
            map.put("送り状No/問い合わせ番号", manifest.getTrackingNo());
            map.put("納品先郵便番号", manifest.getDeliveryPosterCode());
            map.put("納品先一括住所", manifest.getDeliveryAddrAll());
            map.put("住所１", manifest.getDeliveryAddr1());
            map.put("住所２", manifest.getDeliveryAddr2());
            map.put("住所３", manifest.getDeliveryAddr3());
            map.put("住所４", manifest.getDeliveryAddr4());
            map.put("納品先名", manifest.getDeliveryDestination());
            map.put("納品先担当者/配送業者", manifest.getDeliveryContact());
            map.put("納品先TEL", manifest.getDeliveryTel());
            map.put("請求方式", manifest.getBillingMethod());
            map.put("包装", manifest.getPackaging());
            map.put("着払い金額", manifest.getCashOnDeliveryAmount());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
