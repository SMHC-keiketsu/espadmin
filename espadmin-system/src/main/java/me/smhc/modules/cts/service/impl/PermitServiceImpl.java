package me.smhc.modules.cts.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import me.smhc.modules.cts.domain.Permit;
import me.smhc.modules.cts.repository.PermitRepository;
import me.smhc.modules.cts.service.PermitService;
import me.smhc.modules.cts.service.dto.PermitDto;
import me.smhc.modules.cts.service.dto.PermitQueryCriteria;
import me.smhc.modules.cts.service.mapper.PermitMapper;
import me.smhc.modules.system.service.UserService;
import me.smhc.modules.system.service.dto.UserDto;
import me.smhc.utils.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;

/**
* @author 布和
* @date 2020-04-16
*/
@Service
//@CacheConfig(cacheNames = "permit")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PermitServiceImpl implements PermitService {

    private final PermitRepository permitRepository;

    private final PermitMapper permitMapper;

    private final UserService userService;

    public PermitServiceImpl(PermitRepository permitRepository, PermitMapper permitMapper, UserService userService) {
        this.permitRepository = permitRepository;
        this.permitMapper = permitMapper;
        this.userService = userService;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(PermitQueryCriteria criteria, Pageable pageable){
        Page<Permit> page = permitRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(permitMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<PermitDto> queryAll(PermitQueryCriteria criteria){
        return permitMapper.toDto(permitRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public PermitDto findById(Long id) {
        Permit permit = permitRepository.findById(id).orElseGet(Permit::new);
        ValidationUtil.isNull(permit.getId(),"Permit","id",id);
        return permitMapper.toDto(permit);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public PermitDto create(Permit resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId());
        return permitMapper.toDto(permitRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Permit resources) {
        Permit permit = permitRepository.findById(resources.getId()).orElseGet(Permit::new);
        ValidationUtil.isNull( permit.getId(),"Permit","id",resources.getId());
        permit.copy(resources);
        permitRepository.save(permit);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            permitRepository.deleteById(id);
        }
    }

    @Override
//    @Cacheable(key = "#fileName")
    public Permit findByFileName(String fileName) {
        return permitRepository.findByFileName(fileName);
    }
    @Override
    public void addPermit(File file) {
        Permit permit = new Permit();
        try {
            String encoding="GBK";
            //判断文件是否存在
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);

                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                List<String> list = new LinkedList<String>();

                while((lineTxt = bufferedReader.readLine()) != null){
                    list.add(lineTxt);
                }
                read.close();
                int[] lengthArray = new int[]{0, 3, 8, 15, 29, 34, 45, 51, 115, 179, 219, 245, 248, 249, 250, 253, 263, 363, 364, 392, 398};
                String commStr = list.get(0);
                permit.setRa1(commStr.substring(lengthArray[0], lengthArray[1]));
                permit.setBc(commStr.substring(lengthArray[1], lengthArray[2]));
                permit.setCic(commStr.substring(lengthArray[2], lengthArray[3]));
                permit.setMd(commStr.substring(lengthArray[3], lengthArray[4]));
                permit.setUc(commStr.substring(lengthArray[4], lengthArray[5]));
                permit.setRa2(commStr.substring(lengthArray[5], lengthArray[6]));
                permit.setLtn(commStr.substring(lengthArray[6], lengthArray[7]));
                permit.setRa3(commStr.substring(lengthArray[7], lengthArray[8]));
                permit.setSubject(commStr.substring(lengthArray[8], lengthArray[9]));
                permit.setRa4(commStr.substring(lengthArray[9], lengthArray[10]));
                permit.setMti(commStr.substring(lengthArray[10], lengthArray[11]));
                permit.setMciDsn(commStr.substring(lengthArray[11], lengthArray[12]));
                permit.setMciFd(commStr.substring(lengthArray[12], lengthArray[13]));
                permit.setMciMt(commStr.substring(lengthArray[13], lengthArray[14]));
                permit.setMciRa(commStr.substring(lengthArray[14], lengthArray[15]));
                permit.setIicn(commStr.substring(lengthArray[15], lengthArray[16]));
                permit.setIti(commStr.substring(lengthArray[16], lengthArray[17]));
                permit.setAf(commStr.substring(lengthArray[17], lengthArray[18]));
                permit.setRa5(commStr.substring(lengthArray[18], lengthArray[19]));
                permit.setTc(commStr.substring(lengthArray[19], lengthArray[20]));

                permit.setIc1(list.get(1));
                permit.setSkb(list.get(2));
                permit.setRii(list.get(3));
                permit.setChz(list.get(4));
                permit.setChb(list.get(5));
                permit.setIct(list.get(6));
                permit.setIcn(list.get(7));
                permit.setJyo(list.get(8));
                permit.setIcd(list.get(9));
                permit.setIce(list.get(10));
                permit.setImc(list.get(11));
                permit.setImn(list.get(12));
                permit.setImy(list.get(13));
                permit.setIma(list.get(14));
                permit.setIma2(list.get(15));
                permit.setIma3(list.get(16));
                permit.setIma4(list.get(17));
                permit.setIad(list.get(18));
                permit.setImt(list.get(19));
                permit.setZjy(list.get(20));
                permit.setZjj(list.get(21));
                permit.setZjn(list.get(22));
                permit.setEpc(list.get(23));
                permit.setEpn(list.get(24));
                permit.setEpy(list.get(25));
                permit.setEpa(list.get(26));
                permit.setEp2(list.get(27));
                permit.setEp3(list.get(28));
                permit.setEp4(list.get(29));
                permit.setEpo(list.get(30));
                permit.setEad(list.get(31));
                permit.setAgc(list.get(32));
                permit.setAgn(list.get(33));
                permit.setSn(list.get(34));
                permit.setTtc(list.get(35));
                permit.setHab(list.get(36));
                permit.setMab(list.get(37));
                permit.setDst(list.get(38));
                permit.setPn(list.get(39));
                permit.setPsc(list.get(40));
                permit.setPsn(list.get(41));
                permit.setVsn(list.get(42));
                permit.setArr(list.get(43));
                permit.setSct(list.get(44));
                permit.setWcd(list.get(45));
                permit.setNo(list.get(46));
                permit.setGw(list.get(47));
                permit.setNocc(list.get(48));
                permit.setNocn(list.get(49));
                permit.setCerc(list.get(50));
                permit.setCcr(list.get(51));
                permit.setCerc1(list.get(52));
                permit.setCcr1(list.get(53));
                permit.setCerc2(list.get(54));
                permit.setCcr2(list.get(55));
                permit.setIp1(list.get(56));
                permit.setIp2(list.get(57));
                permit.setIp3(list.get(58));
                permit.setIp4(list.get(59));
                permit.setFr1(list.get(60));
                permit.setFr2(list.get(61));
                permit.setFr3(list.get(62));
                permit.setIn1(list.get(63));
                permit.setIn2(list.get(64));
                permit.setIn3(list.get(65));
                permit.setCmn(list.get(66));
                permit.setDpr(list.get(67));
                permit.setTpi(list.get(68));
                permit.setOr(list.get(69));
                permit.setPo(list.get(70));
                permit.setNt1(list.get(71));
                permit.setImi(list.get(72));
                permit.setRef(list.get(73));
                permit.setNsc(list.get(74));
                permit.setNrn(list.get(75));
                permit.setNoc(list.get(76));
                permit.setIid(list.get(77));
                permit.setEor(list.get(78));
                permit.setFui(list.get(79));
                permit.setFileName(file.getName());

                UserDto userDto = userService.findByName(SecurityUtils.getUsername());
                permit.setCreateUserId(userDto.getId());
                permit.setUpdateUserId(userDto.getId());
                PermitDto permitDto=this.create(permit);
            }else{
                new ResponseEntity<>("找不到指定的文件", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            System.out.println("");
            new ResponseEntity<>("读取文件内容出错",HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    @Override
    public void download(List<PermitDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PermitDto permit : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("((予約エリア))", permit.getRa1());
            map.put("(業務コード)", permit.getBc());
            map.put("(出力情報コード)", permit.getCic());
            map.put("(電文受信日時)", permit.getMd());
            map.put("(利用者コード)", permit.getUc());
            map.put("((予約エリア))", permit.getRa2());
            map.put("(論理端末名)", permit.getLtn());
            map.put("((予約エリア))", permit.getRa3());
            map.put("(Subject)", permit.getSubject());
            map.put("((予約エリア))", permit.getRa4());
            map.put("(電文引継情報)", permit.getMti());
            map.put("(電文制御情報-分割通番)", permit.getMciDsn());
            map.put("(電文制御情報-最終表示)", permit.getMciFd());
            map.put("(電文制御情報-電文種別)", permit.getMciMt());
            map.put("(電文制御情報-(予約エリア))", permit.getMciRa());
            map.put("(入力情報特定番号)", permit.getIicn());
            map.put("(索引引継情報)", permit.getIti());
            map.put("(宛管形式)", permit.getAf());
            map.put("((予約エリア))", permit.getRa5());
            map.put("(電文長)", permit.getTc());
            map.put("(申告先種別コード)", permit.getIc1());
            map.put("(識別符号)", permit.getSkb());
            map.put("(審査検査区分識別)", permit.getRii());
            map.put("(あて先税関)", permit.getChz());
            map.put("(あて先部門コード)", permit.getChb());
            map.put("(申告年月日)", permit.getIct());
            map.put("(申告番号)", permit.getIcn());
            map.put("(申告条件コード)", permit.getJyo());
            map.put("(申告予定年月日)", permit.getIcd());
            map.put("(本申告表示)", permit.getIce());
            map.put("(輸入者コード)", permit.getImc());
            map.put("(輸入者名)", permit.getImn());
            map.put("(郵便番号)", permit.getImy());
            map.put("(住所１（都道府県）)", permit.getIma());
            map.put("(住所２（市区町村)", permit.getIma2());
            map.put("(住所３（町域名・番)", permit.getIma3());
            map.put("(住所４（ビル名ほ)", permit.getIma4());
            map.put("(輸入者住所)", permit.getIad());
            map.put("(輸入者電話番号)", permit.getImt());
            map.put("(税関事務管理人コー)", permit.getZjy());
            map.put("(税関事務管理人受理)", permit.getZjj());
            map.put("(税関事務管理人名)", permit.getZjn());
            map.put("(仕出人コード)", permit.getEpc());
            map.put("(仕出人名)", permit.getEpn());
            map.put("(郵便番号（Postcode)", permit.getEpy());
            map.put("(住所１（Street)", permit.getEpa());
            map.put("(住所２（Street)", permit.getEp2());
            map.put("(住所３（City)", permit.getEp3());
            map.put("(住所４（Country)", permit.getEp4());
            map.put("(国名コード)", permit.getEpo());
            map.put("(仕出人住所)", permit.getEad());
            map.put("(代理人コード)", permit.getAgc());
            map.put("(代理人名)", permit.getAgn());
            map.put("(通関士コード)", permit.getSn());
            map.put("(検査立会者)", permit.getTtc());
            map.put("(ＨＡＷＢ番号)", permit.getHab());
            map.put("(ＭＡＷＢ番号)", permit.getMab());
            map.put("(取卸港コード)", permit.getDst());
            map.put("(取卸港名)", permit.getPn());
            map.put("(積出地コード)", permit.getPsc());
            map.put("(積出地名)", permit.getPsn());
            map.put("(積載機名)", permit.getVsn());
            map.put("(入港年月日)", permit.getArr());
            map.put("(蔵置税関)", permit.getSct());
            map.put("(蔵置税関部門)", permit.getWcd());
            map.put("(貨物個数)", permit.getNo());
            map.put("(貨物重量（グロス）)", permit.getGw());
            map.put("(通関蔵置場コード)", permit.getNocc());
            map.put("(通関蔵置場名)", permit.getNocn());
            map.put("(通貨換算レート通貨コード)", permit.getCerc());
            map.put("(通貨換算レート)", permit.getCcr());
            map.put("(通貨換算レート通貨コード1)", permit.getCerc1());
            map.put("(通貨換算レート1)", permit.getCcr1());
            map.put("(通貨換算レート通貨コード2)", permit.getCerc2());
            map.put("(通貨換算レート2)", permit.getCcr2());
            map.put("(インボイス価格区分)", permit.getIp1());
            map.put("(インボイス価格条件)", permit.getIp2());
            map.put("(インボイス通貨コー)", permit.getIp3());
            map.put("(インボイス価格)", permit.getIp4());
            map.put("(運賃区分コード)", permit.getFr1());
            map.put("(運賃通貨コード)", permit.getFr2());
            map.put("(運賃)", permit.getFr3());
            map.put("(保険区分コード)", permit.getIn1());
            map.put("(保険通貨コード)", permit.getIn2());
            map.put("(保険金額)", permit.getIn3());
            map.put("(品名)", permit.getCmn());
            map.put("(課税価格)", permit.getDpr());
            map.put("(課税価格入力識別)", permit.getTpi());
            map.put("(原産地コード)", permit.getOr());
            map.put("(原産地名)", permit.getPo());
            map.put("(記事)", permit.getNt1());
            map.put("(輸入者（入力）)", permit.getImi());
            map.put("(社内整理用番号)", permit.getRef());
            map.put("(荷主セクションコー)", permit.getNsc());
            map.put("(荷主リファレンスナ)", permit.getNrn());
            map.put("(税関官署長名)", permit.getNoc());
            map.put("(輸入許可年月日)", permit.getIid());
            map.put("(審査終了年月日)", permit.getEor());
            map.put("(事後審査識別)", permit.getFui());
            map.put("電文ファイル名", permit.getFileName());
            map.put(" createTime",  permit.getCreateTime());
            map.put(" updateTime",  permit.getUpdateTime());
            map.put(" updateUserId",  permit.getUpdateUserId());
            map.put(" createUserId",  permit.getCreateUserId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
