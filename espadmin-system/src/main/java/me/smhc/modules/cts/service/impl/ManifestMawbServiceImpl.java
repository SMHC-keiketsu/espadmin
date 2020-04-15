package me.smhc.modules.cts.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.template.TemplateException;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import me.smhc.exception.BadRequestException;
import me.smhc.modules.cts.domain.ManifestHawb;
import me.smhc.modules.cts.domain.ManifestMawb;
import me.smhc.modules.cts.repository.ManifestMawbRepository;
import me.smhc.modules.cts.service.ManifestHawbService;
import me.smhc.modules.cts.service.ManifestMawbService;
import me.smhc.modules.cts.service.dto.ManifestMawbDto;
import me.smhc.modules.cts.service.dto.ManifestMawbQueryCriteria;
import me.smhc.modules.cts.service.mapper.ManifestMawbMapper;
import me.smhc.modules.master.domain.Agency;
import me.smhc.modules.master.domain.ExcelConfig;
import me.smhc.modules.master.service.*;
import me.smhc.modules.master.service.dto.PatternConfigDto;
import me.smhc.modules.master.service.dto.TariffDto;
import me.smhc.modules.system.domain.Dept;
import me.smhc.modules.system.service.DeptService;
import me.smhc.modules.system.service.UserService;
import me.smhc.modules.system.service.dto.DeptDto;
import me.smhc.modules.system.service.dto.UserDto;
import me.smhc.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private final ManifestHawbService manifestHawbService;

    private final UserService userService;

    private final DeptService deptService;

    private final PatternConfigService patternConfigDtoService;

    private final TariffService tariffService;

    private final ExchangeRateService exchangeRateService;

    private final FareService fareService;

    private final KeywordService keywordService;

    private final ImporterService importerService;

    @Value("${file.path}")
    private String path;

    @Value("${file.maxSize}")
    private long maxSize;

    public ManifestMawbServiceImpl(ManifestMawbRepository manifestMawbRepository, ManifestMawbMapper manifestMawbMapper, ManifestHawbService manifestHawbService, UserService userService, DeptService deptService, PatternConfigService patternConfigDtoService, TariffService tariffService, ExchangeRateService exchangeRateService, FareService fareService, KeywordService keywordService, ImporterService importerService) {
        this.manifestMawbRepository = manifestMawbRepository;
        this.manifestHawbService = manifestHawbService;
        this.manifestMawbMapper = manifestMawbMapper;
        this.userService = userService;
        this.deptService = deptService;
        this.patternConfigDtoService = patternConfigDtoService;
        this.tariffService = tariffService;
        this.exchangeRateService = exchangeRateService;
        this.fareService = fareService;
        this.keywordService = keywordService;
        this.importerService = importerService;
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
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(Long deptId, Long patternId, MultipartFile multipartFile) {
        // check File Size
        FileUtil.checkSize(maxSize, multipartFile.getSize());

        // if Agency dept is not setted
        if(ObjectUtil.isNull(deptId)){
            throw new BadRequestException("代理店不能为空");
        }
        // if Pattern is not setted
        if(ObjectUtil.isNull(patternId))
        {
            throw new BadRequestException("代理店的Pattern不能为空");
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
            // get pattern config
            PatternConfigDto patternConfigDto = patternConfigDtoService.findById(patternId);
            // データ解析
            if(ObjectUtil.isNotNull(excelConfig.getMainFestExcel())){
                JSONObject jsonObject =  new JSONObject(excelConfig.getMainFestExcel());
                Map<String,ManifestMawb> manifestMawbMap = new HashMap<>();
                for (Map<String,Object> row: readAll) {
                    if(ObjectUtil.isNull(manifestMawbMap) || !manifestMawbMap.containsKey(row.get(jsonObject.getStr("mawbNo")).toString().trim())) {
                        ManifestMawb manifestMawb = new ManifestMawb();
                        Dept dept = new Dept();
                        dept.setId(deptId);
                        manifestMawb.setDept(dept);
                        manifestMawb.setMawbNo(row.get(jsonObject.getStr("mawbNo")).toString().trim());
                        manifestMawb.setFlightNo(row.get(jsonObject.getStr("flightNo")).toString().trim());
                        Date date = DateUtil.parse(row.get(jsonObject.getStr("flightDate")).toString().trim());
                        manifestMawb.setFlightDate(date);
                        // IDA,MIC Default Config
                        manifestMawb.setJyo(patternConfigDto.getJyo());
                        manifestMawb.setIc1(patternConfigDto.getIc1());
                        manifestMawb.setIcb(patternConfigDto.getIcb());
                        manifestMawb.setLs(patternConfigDto.getLs());
                        manifestMawb.setCh(patternConfigDto.getCh());
                        manifestMawb.setChb(patternConfigDto.getChb());
                        manifestMawb.setIcd(patternConfigDto.getIcd());
                        manifestMawb.setVsn(patternConfigDto.getVsn());
                        manifestMawb.setArr(patternConfigDto.getArr());
                        manifestMawb.setDst(patternConfigDto.getDst());
                        manifestMawb.setPsc(patternConfigDto.getPsc());
                        manifestMawb.setSt(patternConfigDto.getSt());
                        // HCH Default Config
                        manifestMawb.setMkh(patternConfigDto.getMkh());
                        manifestMawb.setChc(patternConfigDto.getChc());
                        manifestMawb.setIbb(patternConfigDto.getIbb());
                        manifestMawb.setFl1(patternConfigDto.getFl1());
                        manifestMawb.setFl2(patternConfigDto.getFl2());
                        manifestMawb.setPot(patternConfigDto.getPot());
                        manifestMawb.setOrg(patternConfigDto.getOrg());
                        manifestMawb.setJnt(patternConfigDto.getJnt());

                        // hawbList
                        List<ManifestHawb> manifestHawbList = new ArrayList<>();
                        manifestMawb.setManifestHawbList(manifestHawbList);
                        // set status
                        manifestMawb.setStatus("1");
                        // set map
                        manifestMawbMap.put(manifestMawb.getMawbNo(),manifestMawb);
                    }
                    ManifestHawb manifestHawb = new ManifestHawb();
                    manifestHawb.setHawbNo(row.get(jsonObject.getStr("hawbNo")).toString().trim());
                    manifestHawb.setPcs(Integer.parseInt(row.get(jsonObject.getStr("pcs")).toString().trim()));
                    manifestHawb.setWeight(new BigDecimal(row.get(jsonObject.getStr("weight")).toString().trim()));
                    manifestHawb.setWeightCode(row.get(jsonObject.getStr("weightCode")).toString().trim().toUpperCase());
                    manifestHawb.setProductName(row.get(jsonObject.getStr("productName")).toString().trim().toUpperCase());
                    /* インボイス価格区分コード */
                    manifestHawb.setInvoiceClassificationCode(patternConfigDto.getIp1());
                    /* インボイス価格条件コード */
                    if(jsonObject.getStr("invoiceConditionCode") != null){
                        manifestHawb.setInvoiceConditionCode(row.get(jsonObject.getStr("invoiceConditionCode")).toString().trim().toUpperCase());
                    }else {
                        manifestHawb.setInvoiceConditionCode(patternConfigDto.getIp2());
                    }
                    /* インボイス通貨コード */
                    if(jsonObject.getStr("invoiceConditionCode") != null) {
                        manifestHawb.setInvoiceIso(row.get(jsonObject.getStr("invoiceIso")).toString().trim().toUpperCase());
                    }else {
                        manifestHawb.setInvoiceIso(patternConfigDto.getIp3());
                    }
                    /* インボイス価格 */
                    if(jsonObject.getStr("invoiceValue") != null) {
                        manifestHawb.setInvoiceValue(new BigDecimal(row.get(jsonObject.getStr("invoiceValue")).toString().trim()));
                    }else {
                        manifestHawb.setInvoiceValue(new BigDecimal(patternConfigDto.getIp4()));
                    }
                    // Set default Value
                    if(ObjectUtil.isEmpty(manifestHawb.getInvoiceValue())){
                        manifestHawb.setInvoiceValue(new BigDecimal(0));
                    }
                    /* 運賃区分コード */
                    if(jsonObject.getStr("fareClassificationCode") != null) {
                        manifestHawb.setFareClassificationCode(row.get(jsonObject.getStr("fareClassificationCode")).toString().trim().toUpperCase());
                    }else {
                        manifestHawb.setFareClassificationCode(patternConfigDto.getFr1());
                    }
                    /* 運賃通貨コード */
                    if(jsonObject.getStr("fareIso") != null) {
                        manifestHawb.setFareIso(row.get(jsonObject.getStr("fareIso")).toString().trim().toUpperCase());
                    }else {
                        manifestHawb.setFareIso(patternConfigDto.getFr2());
                    }
                    /* 運賃 */
                    if(jsonObject.getStr("fareValue") != null) {
                        manifestHawb.setFareValue(new BigDecimal(row.get(jsonObject.getStr("fareValue")).toString().trim()));
                    }else {
                        manifestHawb.setFareValue(new BigDecimal(patternConfigDto.getFr3()));
                    }
                    // Set default Value
                    if(ObjectUtil.isEmpty(manifestHawb.getFareValue())){
                        manifestHawb.setFareValue(new BigDecimal(0));
                    }

                    /* 保険区分コード */
                    manifestHawb.setInsuranceClassificationCode(patternConfigDto.getIn1());

                    /* 保険通貨コード */
                    if(jsonObject.getStr("insuranceIso") != null) {
                        manifestHawb.setInsuranceIso(row.get(jsonObject.getStr("insuranceIso")).toString().trim().toUpperCase());
                    }else{
                        manifestHawb.setInsuranceIso(patternConfigDto.getIn2());
                    }
                    /* 保険金額 */
                    if(jsonObject.getStr("insuranceValue") != null) {
                        manifestHawb.setInsuranceValue(new BigDecimal(row.get(jsonObject.getStr("insuranceValue")).toString().trim()));
                    }else {
                        manifestHawb.setInsuranceValue(new BigDecimal(patternConfigDto.getIn3()));
                    }
                    // Set default Value
                    if(ObjectUtil.isEmpty(manifestHawb.getInsuranceValue())){
                        manifestHawb.setInsuranceValue(new BigDecimal(0));
                    }

                    if(jsonObject.getStr("importerName") != null) {
                        manifestHawb.setImporterName(row.get(jsonObject.getStr("importerName")).toString().trim().toUpperCase());
                    }
                    if(jsonObject.getStr("importerPosterCode") != null) {
                        manifestHawb.setImporterPosterCode(row.get(jsonObject.getStr("importerPosterCode")).toString().trim());
                    }
                    if(jsonObject.getStr("importerTel") != null) {
                        manifestHawb.setImporterTel(row.get(jsonObject.getStr("importerTel")).toString().trim());
                    }
                    if(jsonObject.getStr("importerAddrAll") != null){
                        manifestHawb.setImporterAddrAll(row.get(jsonObject.getStr("importerAddrAll")).toString().trim());
                    }

                    if(jsonObject.getStr("importerAddr1") != null) {
                        manifestHawb.setImporterAddr1(row.get(jsonObject.getStr("importerAddr1")).toString().trim());
                    }
                    if(jsonObject.getStr("importerAddr2") != null) {
                        manifestHawb.setImporterAddr2(row.get(jsonObject.getStr("importerAddr2")).toString().trim());
                    }
                    if(jsonObject.getStr("importerAddr3") != null) {
                        manifestHawb.setImporterAddr3(row.get(jsonObject.getStr("importerAddr3")).toString().trim());
                    }
                    if(jsonObject.getStr("importerAddr4") != null) {
                        manifestHawb.setImporterAddr4(row.get(jsonObject.getStr("importerAddr4")).toString().trim());
                    }
                    if(jsonObject.getStr("shipperName") != null) {
                        manifestHawb.setShipperName(row.get(jsonObject.getStr("shipperName")).toString().trim());
                    }
                    if(jsonObject.getStr("shipperPosterCode") != null) {
                        manifestHawb.setShipperPosterCode(row.get(jsonObject.getStr("shipperPosterCode")).toString().trim());
                    }
                    if(jsonObject.getStr("shipperTel") != null) {
                        manifestHawb.setShipperTel(row.get(jsonObject.getStr("shipperTel")).toString().trim());
                    }
                    if(jsonObject.getStr("shipperAddrAll") != null) {
                        manifestHawb.setShipperAddrAll(row.get(jsonObject.getStr("shipperAddrAll")).toString().trim());
                    }
                    /* 原産地コード */
                    if(jsonObject.getStr("origin") != null) {
                        manifestHawb.setOrigin(row.get(jsonObject.getStr("origin")).toString().trim());
                    }else {
                        manifestHawb.setOrigin(patternConfigDto.getOr());
                    }
                    if(jsonObject.getStr("trackingNo") != null) {
                        manifestHawb.setTrackingNo(row.get(jsonObject.getStr("trackingNo")).toString().trim());
                    }

                    /* 納品先名 */
                    if(jsonObject.getStr("deliveryDestination") != null) {

                        manifestHawb.setDeliveryDestination(row.get(jsonObject.getStr("deliveryDestination")).toString().trim());

                        if(jsonObject.getStr("deliveryTel") != null) {
                            manifestHawb.setDeliveryTel(row.get(jsonObject.getStr("deliveryTel")).toString().trim());
                        }
                        if(jsonObject.getStr("deliveryPosterCode") != null) {
                            manifestHawb.setDeliveryPosterCode(row.get(jsonObject.getStr("deliveryPosterCode")).toString().trim());
                        }
                        if(jsonObject.getStr("deliveryAddrAll") != null) {
                            manifestHawb.setDeliveryAddrAll(row.get(jsonObject.getStr("deliveryAddrAll")).toString().trim());
                        }
                        if(jsonObject.getStr("deliveryAddr1") != null) {
                            manifestHawb.setDeliveryAddr1(row.get(jsonObject.getStr("deliveryAddr1")).toString().trim());
                        }
                        if(jsonObject.getStr("deliveryAddr2") != null) {
                            manifestHawb.setDeliveryAddr2(row.get(jsonObject.getStr("deliveryAddr2")).toString().trim());
                        }
                        if(jsonObject.getStr("deliveryAddr3") != null) {
                            manifestHawb.setDeliveryAddr3(row.get(jsonObject.getStr("deliveryAddr3")).toString().trim());
                        }
                        if(jsonObject.getStr("deliveryAddr4") != null) {
                            manifestHawb.setDeliveryAddr4(row.get(jsonObject.getStr("deliveryAddr4")).toString().trim());
                        }
                    }
                    // 納品先がない場合、輸入者の情報をコッピーします
                    if(ObjectUtil.isEmpty(manifestHawb.getDeliveryDestination())){
                        manifestHawb.setDeliveryDestination(manifestHawb.getImporterName());
                        manifestHawb.setDeliveryPosterCode(manifestHawb.getImporterPosterCode());
                        manifestHawb.setDeliveryTel(manifestHawb.getImporterTel());
                        manifestHawb.setDeliveryAddrAll(manifestHawb.getImporterAddrAll());
                        manifestHawb.setDeliveryAddr1(manifestHawb.getImporterAddr1());
                        manifestHawb.setDeliveryAddr2(manifestHawb.getImporterAddr2());
                        manifestHawb.setDeliveryAddr3(manifestHawb.getImporterAddr3());
                        manifestHawb.setDeliveryAddr4(manifestHawb.getImporterAddr4());
                    }
                    /* 納品先担当者/配送業者 */
                    if(jsonObject.getStr("deliveryContact") != null) {
                        manifestHawb.setDeliveryContact(row.get(jsonObject.getStr("deliveryContact")).toString().trim());
                    }
                    if(jsonObject.getStr("billingMethod") != null) {
                        manifestHawb.setBillingMethod(row.get(jsonObject.getStr("billingMethod")).toString().trim());
                    }
                    if(jsonObject.getStr("packaging") != null) {
                        manifestHawb.setPackaging(row.get(jsonObject.getStr("packaging")).toString().trim());
                    }
                    if(jsonObject.getStr("cashOnDeliveryAmount") != null) {
                        manifestHawb.setCashOnDeliveryAmount(new BigDecimal(row.get(jsonObject.getStr("cashOnDeliveryAmount")).toString().trim()));
                    }
                    /* 課税価格 CIF */
                    manifestHawb.setDpr( NumberUtil.round(new BigDecimal(patternConfigDto.getDpr()),2,RoundingMode.HALF_UP));
                    // Set Default Value
                    if(ObjectUtil.isEmpty(manifestHawb.getDpr())){
                        manifestHawb.setDpr(new BigDecimal(0));
                    }
                    /* 記事 */
                    manifestHawb.setNt1(patternConfigDto.getNt1());
                    /* 荷主セクションコード */
                    manifestHawb.setNsc(patternConfigDto.getNsc());
                    /* 荷主リファレンスナンバー */
                    manifestHawb.setNrn(patternConfigDto.getNrn());
                    manifestHawb.setSpc(patternConfigDto.getSpc());
                    manifestHawb.setHchDst(patternConfigDto.getHchDst());
                    manifestHawb.setIhw(patternConfigDto.getIhw());

                    /* Createor ID **/
                    manifestHawb.setCreateUserId(userDto.getId());
                    manifestHawb.setUpdateUserId(userDto.getId());
                    // add to manifestHawb List
                    manifestMawbMap.get(row.get(jsonObject.getStr("mawbNo")).toString().trim()).getManifestHawbList().add(manifestHawb);

                }
                // save manifest data
                List<String> errMawbNo = new ArrayList<>();

                if(ObjectUtil.isNotNull(manifestMawbMap)){
                    for(String key:manifestMawbMap.keySet()){
                        ManifestMawb temp = manifestMawbRepository.findByMawbNo(key);
                        // 主单不存在或主单未开始受托
                        if(ObjectUtil.isNull(temp) || Integer.parseInt(temp.getStatus()) < 2  ){
                            if(ObjectUtil.isNotNull(temp)){
                                // 先删除然后插入
                                manifestMawbRepository.deleteById(temp.getId());
                            }
                            manifestMawbRepository.save(manifestMawbMap.get(key));
                        }else {
                            errMawbNo.add(key);
                        }
                    }
                    if(ObjectUtil.isNotEmpty(errMawbNo)){
                        throw new BadRequestException( "mawbNo:"+ errMawbNo.toString() +"のデータは受託開始以上の状態ですのでインポートできませんでした");
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
        ManifestMawb manifestMawb = manifestMawbRepository.findById(resources.getId()).orElseGet(ManifestMawb::new);
        ValidationUtil.isNull( manifestMawb.getId(),"Manifest","id",resources.getId());
        // 更新対象のデータを取得する際にも、バージョンのチェックを行うこと
        if(!manifestMawb.getReserve1().equals(resources.getReserve1())){
            throw new ObjectOptimisticLockingFailureException(ManifestHawb.class,"mawb:" + resources.getMawbNo()+"数据并发");
        }
        manifestMawb.copy(resources);
        manifestMawbRepository.save(manifestMawb);
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
            map.put("インボイス価格区分コード", manifestMawb.getInvoiceConditionCode());
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

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void calculateCIF(Long manifestMawbId) {
        // manifestMawb取得
        ManifestMawb manifestMawb = manifestMawbRepository.findById(manifestMawbId).orElseGet(ManifestMawb::new);
        ValidationUtil.isNull(manifestMawb.getId(),"Manifest","id",manifestMawbId);
        if(ObjectUtil.isNotEmpty(manifestMawb.getManifestHawbList())){
            // 税関取得
            TariffDto tariffDto = tariffService.findByGovernmentCode(manifestMawb.getCh());
            // キーワード取得
            List<String> keywordList = keywordService.findAllPorductName();
            Long idaAmount = Long.parseLong("0");
            Long micAmount = Long.parseLong("0");
            for(ManifestHawb temp: manifestMawb.getManifestHawbList()){
                //INVの円貨算出
                BigDecimal invYenka = this.getINVYENKA(temp.getInvoiceIso(),temp.getInvoiceValue());
                //運賃算出
                BigDecimal frtValue = this.getFRTVALUE(manifestMawb.getDept().getId(),temp.getFareIso(),temp.getWeight(),temp.getFareValue());
                temp.setFareValue(frtValue);
                //運賃円貨
                BigDecimal frtYenka = this.getFRTYENKA(temp.getInvoiceConditionCode(),temp.getFareIso(),temp.getFareValue());
                // インボイス価格条件FOB以外の時運賃欄をブランクする
                if(temp.getInvoiceConditionCode().equals("C&F") || temp.getInvoiceConditionCode().equals("CIF")){
                    temp.setFareClassificationCode("");
                    temp.setFareIso("");
                    temp.setFareValue(new BigDecimal(0));
                }
                //保険算出
                BigDecimal insYenka = new BigDecimal(0);
                if(temp.getInsuranceClassificationCode().equals("A") && NumberUtil.equals(temp.getInsuranceValue(),new BigDecimal(0))){
                    temp.setInsuranceIso("JPY");
                    if(NumberUtil.isLessOrEqual(invYenka,new BigDecimal(1000000))){
                        temp.setInsuranceValue(new BigDecimal(3000));
                        insYenka = new BigDecimal(3000);
                    }else {
                        BigDecimal insValue = NumberUtil.mul(NumberUtil.add(invYenka,frtYenka),new BigDecimal(0.003));
                        temp.setInsuranceValue(NumberUtil.round(insValue,0));
                        insYenka = temp.getInsuranceValue();
                    }
                }
                // INSの円貨算出
                insYenka = this.getINSYENKA(temp.getInsuranceIso(),temp.getInsuranceValue());

                // CIF算出
                BigDecimal cifValue = NumberUtil.add(invYenka,frtYenka,insYenka);
                temp.setDpr(NumberUtil.round(cifValue,0));

                // 申告タイプ判定
                if(ObjectUtil.isNotNull(tariffDto)){
                    // iso 以下、重量以下
                    if(tariffDto.getCifLogic().equals(false) && tariffDto.getWeightLogic().equals(false)){
                        if(NumberUtil.isLessOrEqual(temp.getDpr(),tariffDto.getCifValue()) && NumberUtil.isLessOrEqual(temp.getWeight(),tariffDto.getWeightAmount())){
                            if(!keywordList.contains(temp.getProductName())){
                                temp.setMicType(1);
                                temp.setIdaType(0);
                                temp.setHchType(1);
                                micAmount++;
                            }else {
                                temp.setIdaType(1);
                                temp.setHchType(1);
                                temp.setMicType(0);
                                idaAmount++;
                            }
                        }else {
                            temp.setIdaType(1);
                            temp.setHchType(1);
                            temp.setMicType(0);
                            idaAmount++;
                        }
                    }else if(tariffDto.getCifLogic().equals(false) && tariffDto.getWeightLogic().equals(true)){
                        if(NumberUtil.isLessOrEqual(temp.getDpr(),tariffDto.getCifValue()) && NumberUtil.isGreaterOrEqual(temp.getWeight(),tariffDto.getWeightAmount())){
                            if(!keywordList.contains(temp.getProductName())){
                                temp.setMicType(1);
                                temp.setIdaType(0);
                                temp.setHchType(1);
                                micAmount++;
                            }else {
                                temp.setIdaType(1);
                                temp.setHchType(1);
                                temp.setMicType(0);
                                idaAmount++;
                            }
                        }else {
                            temp.setIdaType(1);
                            temp.setHchType(1);
                            temp.setMicType(0);
                            idaAmount++;
                        }
                    }else if(tariffDto.getCifLogic().equals(true) && tariffDto.getWeightLogic().equals(false)){
                        if(NumberUtil.isGreaterOrEqual(temp.getDpr(),tariffDto.getCifValue()) && NumberUtil.isLessOrEqual(temp.getWeight(),tariffDto.getWeightAmount())){
                            if(!keywordList.contains(temp.getProductName())){
                                temp.setMicType(1);
                                temp.setIdaType(0);
                                temp.setHchType(1);
                                micAmount++;
                            }else {
                                temp.setIdaType(1);
                                temp.setHchType(1);
                                temp.setMicType(0);
                                idaAmount++;
                            }
                        }else {
                            temp.setIdaType(1);
                            temp.setHchType(1);
                            temp.setMicType(0);
                            idaAmount++;
                        }
                    }else {
                        if(NumberUtil.isGreaterOrEqual(temp.getDpr(),tariffDto.getCifValue()) && NumberUtil.isGreaterOrEqual(temp.getWeight(),tariffDto.getWeightAmount())){
                            if(!keywordList.contains(temp.getProductName())){
                                temp.setMicType(1);
                                temp.setIdaType(0);
                                temp.setHchType(1);
                                micAmount++;
                            }else {
                                temp.setIdaType(1);
                                temp.setHchType(1);
                                temp.setMicType(0);
                                idaAmount++;
                            }
                        }else {
                            temp.setIdaType(1);
                            temp.setHchType(1);
                            temp.setMicType(0);
                            idaAmount++;
                        }
                    }
                }
            }
            // ida/mic/hch数
            manifestMawb.setIdaAmount(idaAmount);
            manifestMawb.setIdaLeftAmount(idaAmount);
            manifestMawb.setMicAmount(micAmount);
            manifestMawb.setMicLeftAmount(micAmount);
            manifestMawb.setHchAmount(NumberUtil.add(idaAmount,micAmount).longValue());
            manifestMawb.setHchLeftAmount(NumberUtil.add(idaAmount,micAmount).longValue());
            // status委託開始
            manifestMawb.setStatus("2");
            //manifest データ更新
            this.update(manifestMawb);
        }

    }

    @Override
    public void doNaccsTelegram(String type, Long manifestMawbId, List<Long> manifestHawbIdList, HttpServletRequest request, HttpServletResponse response) {
        // manifest data
        ManifestMawb manifestMawb = manifestMawbRepository.findById(manifestMawbId).orElseGet(ManifestMawb::new);
        List<ManifestHawb> manifestHawbList = manifestHawbService.findByIds(manifestHawbIdList);
        int maxCount = 1;
        if(type.equals("HCH")){
            maxCount =20;
        }
        //
        int loopCount = NumberUtil.div(new BigDecimal(manifestHawbList.size()), new BigDecimal(maxCount)).intValue();
        int cur = Math.floorMod(manifestHawbList.size(),maxCount);
        if(cur > 0){
            loopCount++;
        }
        if(loopCount == 0){
            loopCount = 1;
        }
        //
        String basePath = "";
        //
        try {
            switch (type) {
                case "IDA":
                    basePath = this.idaTelegram(loopCount, maxCount, manifestMawb, manifestHawbList);
                    break;
                case "MIC":
                    basePath = this.micTelegram(loopCount, maxCount, manifestMawb, manifestHawbList);
                    break;
                default:
                    basePath = this.hchTelegram(loopCount, maxCount, manifestMawb, manifestHawbList);
            }
            File file = new File(basePath);
            String zipPath = file.getPath() + ".zip";
            ZipUtil.zip(file.getPath(), zipPath);
            FileUtil.downloadFile(request, response, new File(zipPath), true);
            // 删除缓存
            FileUtil.del(basePath);
            FileUtil.del(zipPath);
        }catch (IOException e){
            throw new BadRequestException("打包失败");
        }
    }

    /**
     * ida電文
     * @param loopCount 循环
     * @param maxCount 条数
     * @param manifestMawb 主单
     * @param manifestHawbList 分单数组
     */
    private String idaTelegram(int loopCount,int maxCount, ManifestMawb manifestMawb,List<ManifestHawb> manifestHawbList){
        return "IDA";
    }

    /**
     * mic电文
     * @param loopCount 循环
     * @param maxCount 条数
     * @param manifestMawb 主单
     * @param manifestHawbList 分单数组
     */
    private String micTelegram(int loopCount,int maxCount, ManifestMawb manifestMawb,List<ManifestHawb> manifestHawbList){
        return  "MIC";
    }

    /**
     * hch电文
     * @param loopCount 循环
     * @param maxCount 条数
     * @param manifestMawb 主单
     * @param manifestHawbList 分单数组
     */
    private String hchTelegram(int loopCount,int maxCount, ManifestMawb manifestMawb,List<ManifestHawb> manifestHawbList) throws IOException {

        // base path
        String tempPath = System.getProperty("java.io.tmpdir") + "HCH-" + manifestMawb.getMawbNo()+"-"+ DateUtil.today() +File.separator;
        FileWriter writer = null;
        // make telegram
        for(int i = 0; i< loopCount; i++){
            try {
                String filePath = tempPath + "HCH-" + manifestMawb.getMawbNo() + "-" + String.format("%02d", i+1) + ".txt";
                File file = new File(filePath);
                // 存在覆盖
                if(FileUtil.exist(file)){
                    FileUtil.del(file);
                }
                FileUtil.touch(file);
                writer = new FileWriter(file);
                // 共同
                writer.write(StringUtils.leftPad(" ", 3) + "HCH01" + StringUtils.leftPad(" ", 390));
                //推荐使用，具有良好的跨平台性
                String newLine = System.getProperty("line.separator");
                writer.write(newLine);
                 // 委託元混載業
                if (StringUtils.isNotBlank(manifestMawb.getIbb())) {
                    writer.write(manifestMawb.getIbb());
                } else {
                    writer.write(StringUtils.leftPad(" ", 5));
                }
                writer.write(newLine);
                 // あて先官署コード
                if (StringUtils.isNotBlank(manifestMawb.getCh())) {
                    writer.write(manifestMawb.getCh());
                } else {
                    writer.write(StringUtils.leftPad(" ", 2));
                }
                writer.write(newLine);
                 // MAWB番号
                writer.write(manifestMawb.getMawbNo());
                writer.write(newLine);
                 // 孫混載表示
                if (StringUtils.isNotBlank(manifestMawb.getMkh())) {
                    writer.write(manifestMawb.getMkh());
                } else {
                    writer.write(StringUtils.leftPad(" ", 1));
                }
                writer.write(newLine);
                 // 到着便名１
                if (StringUtils.isNotBlank(manifestMawb.getFl1())) {
                    writer.write(manifestMawb.getFl1());
                } else {
                    writer.write(StringUtils.leftPad(" ", 6));
                }
                writer.write(newLine);
                 // 到着便名２
                if (StringUtils.isNotBlank(manifestMawb.getFl2())) {
                    writer.write(manifestMawb.getFl2());
                } else {
                    writer.write(StringUtils.leftPad(" ", 5));
                }
                writer.write(newLine);
                 // 到着空港
                if (StringUtils.isNotBlank(manifestMawb.getPot())) {
                    writer.write(manifestMawb.getPot());
                } else {
                    writer.write(StringUtils.leftPad(" ", 3));
                }
                writer.write(newLine);
                 // 仕出地
                if (StringUtils.isNotBlank(manifestMawb.getOrg())) {
                    writer.write(manifestMawb.getOrg());
                } else {
                    writer.write(StringUtils.leftPad(" ", 3));
                }
                writer.write(newLine);
                 // ジョイント混載
                if (StringUtils.isNotBlank(manifestMawb.getJnt())) {
                    writer.write(manifestMawb.getJnt());
                } else {
                    writer.write(StringUtils.leftPad(" ", 1));
                }
                writer.write(newLine);
                for (int j = 0; j < maxCount && j < manifestHawbList.size(); j++) {
                    writer.write(manifestHawbList.get(j).getHawbNo());
                    writer.write(newLine);
                     // 個数
                    writer.write(manifestHawbList.get(j).getPcs().toString());
                    writer.write(newLine);
                     // 重量
                    writer.write(manifestHawbList.get(j).getWeight().toString());
                    writer.write(newLine);
                     // 重量コード
                    writer.write(manifestHawbList.get(j).getWeightCode());
                    writer.write(newLine);
                     // 品名
                    writer.write(manifestHawbList.get(j).getProductName());
                    writer.write(newLine);
                     // 特殊貨物記号
                    if (StringUtils.isNotBlank(manifestHawbList.get(j).getSpc())) {
                        writer.write(manifestHawbList.get(j).getSpc());
                    } else {
                        writer.write(StringUtils.leftPad(" ", 3));
                    }
                    writer.write(newLine);
                    // 仕向地
                    if (StringUtils.isNotBlank(manifestHawbList.get(j).getHchDst())) {
                        writer.write(manifestHawbList.get(j).getHchDst());
                    } else {
                        writer.write(StringUtils.leftPad(" ", 3));
                    }
                    writer.write(newLine);
                     // 搬入保税蔵置場
                    if (StringUtils.isNotBlank(manifestHawbList.get(j).getIhw())) {
                        writer.write(manifestHawbList.get(j).getIhw());
                    } else {
                        writer.write(StringUtils.leftPad(" ", 5));
                    }
                    writer.write(newLine);
                      // 予備 使用しない
                    writer.write(StringUtils.leftPad(" ", 1));
                    writer.write(newLine);
                      // 予備 使用しない
                    writer.write(StringUtils.leftPad(" ", 3));
                    writer.write(newLine);
                     // 荷送人名
                    writer.write(StringUtils.rightPad(manifestHawbList.get(j).getShipperName(), 70));
                    writer.write(newLine);
                     // 一括住所
                    writer.write(StringUtils.rightPad(manifestHawbList.get(j).getShipperAddrAll(), 105));
                    writer.write(newLine);
                     // 荷送人TEL
                    writer.write(StringUtils.rightPad(manifestHawbList.get(j).getShipperTel(), 14));
                    writer.write(newLine);
                     // 輸入者コード法人番号/cnc　
                    if (StringUtils.isNotBlank(manifestHawbList.get(j).getImc())) {
                        writer.write(manifestHawbList.get(j).getImc());
                    } else {
                        writer.write(StringUtils.leftPad(" ", 17));
                    }
                    writer.write(newLine);
                     // 荷受人名
                    writer.write(StringUtils.rightPad(manifestHawbList.get(j).getImporterName(), 70));
                    writer.write(newLine);
                     // 一括住所
                    if (StringUtils.isNotBlank(manifestHawbList.get(j).getImporterAddrAll())) {
                        writer.write(StringUtils.rightPad(manifestHawbList.get(j).getImporterAddrAll(), 105));
                    } else {
                        writer.write(manifestHawbList.get(j).getImporterAddr1() + " "
                                + manifestHawbList.get(j).getImporterAddr2() + " "
                                + manifestHawbList.get(j).getImporterAddr3() + " "
                                + manifestHawbList.get(j).getImporterAddr4());
                    }
                    writer.write(newLine);
                     // 荷受人TEL
                    writer.write(StringUtils.rightPad(manifestHawbList.get(j).getImporterTel(), 14));
                    writer.write(newLine);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                assert writer != null;
                writer.close();
            }
        }
        return  tempPath;
    }
    /**
     * INVの円貨算出
     * @param invIso　通貨
     * @param invValue　価格
     * @return  円貨
     */
    private BigDecimal getINVYENKA(String invIso,BigDecimal invValue){
        // inv為替レート
        BigDecimal invRate;
        // inv 円貨
        BigDecimal invYenka;
        if(StringUtils.isNotBlank(invIso) && invIso.equals("JPY")){
            invYenka = invValue;
        }else {
            invRate = exchangeRateService.findRateByIsoAndToday(invIso,DateUtil.today());
            if(ObjectUtil.isNull(invRate)){
                invRate = new BigDecimal(0);
            }
            // 精度小数点2,四捨五入
            invYenka = NumberUtil.mul(invValue,invRate);
        }
        return  invYenka;
    }

    /**
     * 運賃算出
     * @param frtIso　通貨
     * @param frtValue　運賃
     * @return 運賃
     */
    private BigDecimal getFRTVALUE( Long deptId, String frtIso,BigDecimal weight, BigDecimal frtValue){
        BigDecimal retFrtValue = new BigDecimal(0);
        // 運賃マスターより運賃算出（セットされている場合はセットされているほうを優先)）
        if(StringUtils.isNotBlank(frtIso) && NumberUtil.equals(frtValue, new BigDecimal(0))){
            List<BigDecimal> priceList = fareService.findByDeptAndIsoAndWeightIsLessThanEqualOrderByWeightAsc(deptId,frtIso,weight);
            if(ObjectUtil.isNotEmpty(priceList)){
                retFrtValue = priceList.get(0);
            }else {
                // 該当重量が無ければ最大値をセット
                priceList = fareService.findHeightestPrice(deptId,frtIso);
                if(ObjectUtil.isNotEmpty(priceList)){
                    retFrtValue = priceList.get(0);
                }
            }
        }
        return retFrtValue;
    }

    /**
     * 運賃円貨
     * @param invCoinvoiceConditionCodede inv区分コード
     * @param frtIso　通貨
     * @param frtValue　価格
     * @return  円貨
     */
    private BigDecimal getFRTYENKA(String invCoinvoiceConditionCodede,String frtIso,BigDecimal frtValue){
        // frt為替レート
        BigDecimal frtRate;
        // frt円貨
        BigDecimal frtYenka = new BigDecimal(0);
        // インボイス価格条件FOB以外の時
        if(StringUtils.isBlank(invCoinvoiceConditionCodede) || (!invCoinvoiceConditionCodede.equals("C&F") && !invCoinvoiceConditionCodede.equals("CIF"))){
            if(StringUtils.isNotBlank(frtIso) && frtIso.equals("JPY")){
                frtYenka = frtValue;
            }else {
                frtRate = exchangeRateService.findRateByIsoAndToday(frtIso,DateUtil.today());
                if(ObjectUtil.isNull(frtRate)){
                    frtRate = new BigDecimal(0);
                }
                //
                frtYenka = NumberUtil.mul(frtValue,frtRate);
            }
        }
        return  frtYenka;
    }

    /**
     * ins円貨算出
     * @param insIso　通貨
     * @param insValue　価格
     * @return  保険
     */
   private BigDecimal getINSYENKA(String insIso, BigDecimal insValue){
        BigDecimal insRate;
        BigDecimal insYenka = new BigDecimal(0);
        if(StringUtils.isNotBlank(insIso) && insIso.equals("JPY")){
            insYenka = insValue;
        }else {
            insRate = exchangeRateService.findRateByIsoAndToday(insIso,DateUtil.today());
            if(ObjectUtil.isNull(insRate)){
                insRate = new BigDecimal(0);
            }
            insYenka = NumberUtil.mul(insValue,insRate);
        }
        return insYenka;
   }


}
