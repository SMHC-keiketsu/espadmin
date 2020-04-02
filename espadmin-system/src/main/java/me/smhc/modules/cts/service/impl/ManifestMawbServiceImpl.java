package me.smhc.modules.cts.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import me.smhc.exception.BadRequestException;
import me.smhc.modules.cts.domain.ManifestHawb;
import me.smhc.modules.cts.domain.ManifestMawb;
import me.smhc.modules.cts.repository.ManifestMawbRepository;
import me.smhc.modules.cts.service.ManifestMawbService;
import me.smhc.modules.cts.service.dto.ManifestMawbDto;
import me.smhc.modules.cts.service.dto.ManifestMawbQueryCriteria;
import me.smhc.modules.cts.service.mapper.ManifestMawbMapper;
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
//@CacheConfig(cacheNames = "manifestMawb")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ManifestMawbServiceImpl implements ManifestMawbService {

    private final ManifestMawbRepository manifestMawbRepository;

    private final ManifestMawbMapper manifestMawbMapper;

    private final UserService userService;

    private final DeptService deptService;

    @Value("${file.path}")
    private String path;

    @Value("${file.maxSize}")
    private long maxSize;

    public ManifestMawbServiceImpl(ManifestMawbRepository manifestMawbRepository, ManifestMawbMapper manifestMawbMapper, UserService userService, DeptService deptService) {
        this.manifestMawbRepository = manifestMawbRepository;
        this.manifestMawbMapper = manifestMawbMapper;
        this.userService = userService;
        this.deptService = deptService;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(ManifestMawbQueryCriteria criteria, Pageable pageable){
        Page<ManifestMawb> page = manifestMawbRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(manifestMawbMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<ManifestMawbDto> queryAll(ManifestMawbQueryCriteria criteria){
        return manifestMawbMapper.toDto(manifestMawbRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public ManifestMawbDto findById(Long id) {
        ManifestMawb ManifestMawb = manifestMawbRepository.findById(id).orElseGet(ManifestMawb::new);
        ValidationUtil.isNull(ManifestMawb.getId(),"Manifest","id",id);
        return manifestMawbMapper.toDto(ManifestMawb);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public ManifestMawbDto create(ManifestMawb resources) {
        // if Agency is not setted
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        if(ObjectUtil.isNull(resources.getDept().getId())){
           throw new BadRequestException("代理店不能为空");
        }
        resources.setCreateUserId(userDto.getId());
        resources.setUpdateUserId(userDto.getId());
        return manifestMawbMapper.toDto(manifestMawbRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(Long deptId, MultipartFile multipartFile) {
        // check File Size
        FileUtil.checkSize(maxSize, multipartFile.getSize());

        // if Agency dept is not setted
        if(ObjectUtil.isNull(deptId)){
            throw new BadRequestException("代理店不能为空");
        }

        ExcelReader reader = ExcelUtil.getReader(FileUtil.toFile(multipartFile));
        List<Map<String,Object>> readAll = reader.readAll();
        if(ObjectUtil.isNull(readAll)){
            throw new BadRequestException("Excel解析失敗");
        }
        try {
            // get excel config
            UserDto userDto = userService.findByName(SecurityUtils.getUsername());
            DeptDto deptDto = deptService.findById(deptId);
            Agency agency = deptDto.getAgency();
            ExcelConfig excelConfig = agency.getExcelConfig();

            if(ObjectUtil.isNotNull(excelConfig.getMainFestExcel())){
                JSONObject jsonObject =  new JSONObject(excelConfig.getMainFestExcel());
                Map<String,ManifestMawb> manifestMawbMap = new HashMap<>();
                for (Map<String,Object> row: readAll) {
                    if(ObjectUtil.isNull(manifestMawbMap) || !manifestMawbMap.containsKey(row.get(jsonObject.getStr("mawbNo")).toString())) {
                        ManifestMawb manifestMawb = new ManifestMawb();
                        Dept dept = new Dept();
                        dept.setId(deptId);
                        manifestMawb.setDept(dept);
                        manifestMawb.setMawbNo(row.get(jsonObject.getStr("mawbNo")).toString());
                        manifestMawb.setFlightNo(row.get(jsonObject.getStr("flightNo")).toString());
                        Date date = DateUtil.parse(row.get(jsonObject.getStr("flightDate")).toString());
                        manifestMawb.setFlightDate(date);
                        List<ManifestHawb> manifestHawbList = new ArrayList<>();
                        manifestMawb.setManifestHawbList(manifestHawbList);
                        manifestMawbMap.put(manifestMawb.getMawbNo(),manifestMawb);
                    }
                    ManifestHawb manifestHawb = new ManifestHawb();
                    manifestHawb.setHawbNo(row.get(jsonObject.getStr("hawbNo")).toString());
                    manifestHawb.setPcs(Integer.parseInt(row.get(jsonObject.getStr("pcs")).toString()));
                    manifestHawb.setWeight(new BigDecimal(row.get(jsonObject.getStr("weight")).toString()));
                    manifestHawb.setWeightCode(row.get(jsonObject.getStr("weightCode")).toString());
                    manifestHawb.setProductName(row.get(jsonObject.getStr("productName")).toString());
                    if(jsonObject.getStr("invoiceConditionCode") != null){
                        manifestHawb.setInvoiceConditionCode(row.get(jsonObject.getStr("invoiceConditionCode")).toString());
                    }
                    if(jsonObject.getStr("invoiceConditionCode") != null) {
                        manifestHawb.setInvoiceIso(row.get(jsonObject.getStr("invoiceIso")).toString());
                    }
                    if(jsonObject.getStr("invoiceValue") != null) {
                        manifestHawb.setInsuranceValue(new BigDecimal(row.get(jsonObject.getStr("invoiceValue")).toString()));
                    }
                    if(jsonObject.getStr("fareClassificationCode") != null) {
                        manifestHawb.setFareClassificationCode(row.get(jsonObject.getStr("fareClassificationCode")).toString());
                    }
                    if(jsonObject.getStr("fareIso") != null) {
                        manifestHawb.setFareIso(row.get(jsonObject.getStr("fareIso")).toString());
                    }
                    if(jsonObject.getStr("fareValue") != null) {
                        manifestHawb.setFareValue(new BigDecimal(row.get(jsonObject.getStr("fareValue")).toString()));
                    }
                    if(jsonObject.getStr("insuranceIso") != null) {
                        manifestHawb.setInsuranceIso(row.get(jsonObject.getStr("insuranceIso")).toString());
                    }
                    if(jsonObject.getStr("insuranceValue") != null) {
                        manifestHawb.setInsuranceValue(new BigDecimal(row.get(jsonObject.getStr("insuranceValue")).toString()));
                    }
                    if(jsonObject.getStr("importerName") != null) {
                        manifestHawb.setImporterName(row.get(jsonObject.getStr("importerName")).toString());
                    }
                    if(jsonObject.getStr("importerPosterCode") != null) {
                        manifestHawb.setImporterPosterCode(row.get(jsonObject.getStr("importerPosterCode")).toString());
                    }
                    if(jsonObject.getStr("importerTel") != null) {
                        manifestHawb.setImporterTel(row.get(jsonObject.getStr("importerTel")).toString());
                    }
                    if(jsonObject.getStr("importerAddr1") != null) {
                        manifestHawb.setImporterAddr1(row.get(jsonObject.getStr("importerAddr1")).toString());
                    }
                    if(jsonObject.getStr("importerAddr2") != null) {
                        manifestHawb.setImporterAddr2(row.get(jsonObject.getStr("importerAddr2")).toString());
                    }
                    if(jsonObject.getStr("importerAddr3") != null) {
                        manifestHawb.setImporterAddr3(row.get(jsonObject.getStr("importerAddr3")).toString());
                    }
                    if(jsonObject.getStr("importerAddr4") != null) {
                        manifestHawb.setImporterAddr4(row.get(jsonObject.getStr("importerAddr4")).toString());
                    }
                    if(jsonObject.getStr("shipperName") != null) {
                        manifestHawb.setShipperName(row.get(jsonObject.getStr("shipperName")).toString());
                    }
                    if(jsonObject.getStr("shipperPosterCode") != null) {
                        manifestHawb.setShipperPosterCode(row.get(jsonObject.getStr("shipperPosterCode")).toString());
                    }
                    if(jsonObject.getStr("shipperTel") != null) {
                        manifestHawb.setShipperTel(row.get(jsonObject.getStr("shipperTel")).toString());
                    }
                    if(jsonObject.getStr("shipperAddrAll") != null) {
                        manifestHawb.setShipperAddrAll(row.get(jsonObject.getStr("shipperAddrAll")).toString());
                    }
                    if(jsonObject.getStr("origin") != null) {
                        manifestHawb.setOrigin(row.get(jsonObject.getStr("origin")).toString());
                    }
                    if(jsonObject.getStr("trackingNo") != null) {
                        manifestHawb.setTrackingNo(row.get(jsonObject.getStr("trackingNo")).toString());
                    }
                    if(jsonObject.getStr("deliveryPosterCode") != null) {
                        manifestHawb.setDeliveryPosterCode(row.get(jsonObject.getStr("deliveryPosterCode")).toString());
                    }
                    if(jsonObject.getStr("deliveryDestination") != null) {
                        manifestHawb.setDeliveryDestination(row.get(jsonObject.getStr("deliveryDestination")).toString());
                    }
                    if(jsonObject.getStr("deliveryTel") != null) {
                        manifestHawb.setDeliveryTel(row.get(jsonObject.getStr("deliveryTel")).toString());
                    }
                    if(jsonObject.getStr("deliveryContact") != null) {
                        manifestHawb.setDeliveryContact(row.get(jsonObject.getStr("deliveryContact")).toString());
                    }
                    if(jsonObject.getStr("deliveryAddrAll") != null) {
                        manifestHawb.setDeliveryAddrAll(row.get(jsonObject.getStr("deliveryAddrAll")).toString());
                    }
                    if(jsonObject.getStr("deliveryAddr1") != null) {
                        manifestHawb.setDeliveryAddr1(row.get(jsonObject.getStr("deliveryAddr1")).toString());
                    }
                    if(jsonObject.getStr("deliveryAddr2") != null) {
                        manifestHawb.setDeliveryAddr2(row.get(jsonObject.getStr("deliveryAddr2")).toString());
                    }
                    if(jsonObject.getStr("deliveryAddr3") != null) {
                        manifestHawb.setDeliveryAddr3(row.get(jsonObject.getStr("deliveryAddr3")).toString());
                    }
                    if(jsonObject.getStr("deliveryAddr4") != null) {
                        manifestHawb.setDeliveryAddr4(row.get(jsonObject.getStr("deliveryAddr4")).toString());
                    }
                    if(jsonObject.getStr("billingMethod") != null) {
                        manifestHawb.setBillingMethod(row.get(jsonObject.getStr("billingMethod")).toString());
                    }
                    if(jsonObject.getStr("packaging") != null) {
                        manifestHawb.setPackaging(row.get(jsonObject.getStr("packaging")).toString());
                    }
                    if(jsonObject.getStr("cashOnDeliveryAmount") != null) {
                        manifestHawb.setCashOnDeliveryAmount(new BigDecimal(row.get(jsonObject.getStr("cashOnDeliveryAmount")).toString()));
                    }
                    manifestHawb.setCreateUserId(userDto.getId());
                    manifestHawb.setUpdateUserId(userDto.getId());
                    // add to manifestHawb List
                    manifestMawbMap.get(row.get(jsonObject.getStr("mawbNo")).toString()).getManifestHawbList().add(manifestHawb);

                }
                // save manifest data
                if(ObjectUtil.isNotNull(manifestMawbMap)){
                    for(String key:manifestMawbMap.keySet()){
                        manifestMawbRepository.save(manifestMawbMap.get(key));
                    }
                }
                return true;
            }else {
                throw new BadRequestException( deptDto.getName() +"はエクセルフォーマットの設定がない");
            }
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(ManifestMawb resources) {
        ManifestMawb ManifestMawb = manifestMawbRepository.findById(resources.getId()).orElseGet(ManifestMawb::new);
        ValidationUtil.isNull( ManifestMawb.getId(),"Manifest","id",resources.getId());
        ManifestMawb.copy(resources);
        manifestMawbRepository.save(ManifestMawb);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            manifestMawbRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ManifestMawbDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ManifestMawbDto manifestMawb : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("代理商名", manifestMawb.getDept().getName());
            map.put("MAWB番号", manifestMawb.getMawbNo());
            map.put("FlightNO", manifestMawb.getFlightNo());
            map.put("FlightDate", manifestMawb.getFlightDate());
           /* map.put("HAWB番号", manifestMawb.getHawbNo());
            map.put("個数", manifestMawb.getPcs());
            map.put("重量", manifestMawb.getWeight());
            map.put("重量コード", manifestMawb.getWeightCode());
            map.put("品名", manifestMawb.getProductName());
            map.put("インボイス価格条件コード", manifestMawb.getInvoiceConditionCode());
            map.put("INVOICE通貨 CURRENCY", manifestMawb.getInvoiceIso());
            map.put("INVOICE金額 DECLARED VALUE OF CUSTOMS", manifestMawb.getInvoiceValue());
            map.put("運賃区分コード", manifestMawb.getFareClassificationCode());
            map.put("運賃通貨", manifestMawb.getFareIso());
            map.put("運賃金額", manifestMawb.getFareValue());
            map.put("保険通貨", manifestMawb.getInsuranceIso());
            map.put("保険金額", manifestMawb.getInsuranceValue());
            map.put("荷受人名", manifestMawb.getImporterName());
            map.put("荷受人郵便番号", manifestMawb.getImporterPosterCode());
            map.put("荷受人一括住所", manifestMawb.getImporterAddrAll());
            map.put("荷受人住所１", manifestMawb.getImporterAddr1());
            map.put("荷受人住所２", manifestMawb.getImporterAddr2());
            map.put("荷受人住所３", manifestMawb.getImporterAddr3());
            map.put("荷受人住所４", manifestMawb.getImporterAddr4());
            map.put("荷受人TEL", manifestMawb.getImporterTel());
            map.put("荷送人名", manifestMawb.getShipperName());
            map.put("荷送人郵便番号", manifestMawb.getShipperPosterCode());
            map.put("荷送人一括住所", manifestMawb.getShipperAddrAll());
            map.put("荷送人TEL", manifestMawb.getShipperTel());
            map.put("原産国", manifestMawb.getOrigin());
            map.put("送り状No/問い合わせ番号", manifestMawb.getTrackingNo());
            map.put("納品先郵便番号", manifestMawb.getDeliveryPosterCode());
            map.put("納品先一括住所", manifestMawb.getDeliveryAddrAll());
            map.put("住所１", manifestMawb.getDeliveryAddr1());
            map.put("住所２", manifestMawb.getDeliveryAddr2());
            map.put("住所３", manifestMawb.getDeliveryAddr3());
            map.put("住所４", manifestMawb.getDeliveryAddr4());
            map.put("納品先名", manifestMawb.getDeliveryDestination());
            map.put("納品先担当者/配送業者", manifestMawb.getDeliveryContact());
            map.put("納品先TEL", manifestMawb.getDeliveryTel());
            map.put("請求方式", manifestMawb.getBillingMethod());
            map.put("包装", manifestMawb.getPackaging());
            map.put("着払い金額", manifestMawb.getCashOnDeliveryAmount());*/
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
