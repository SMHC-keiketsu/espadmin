package me.smhc.modules.cts.service.impl;

import me.smhc.modules.cts.domain.PermitMic;
import me.smhc.modules.system.service.UserService;
import me.smhc.modules.system.service.dto.UserDto;
import me.smhc.utils.*;
import me.smhc.modules.cts.repository.PermitMicRepository;
import me.smhc.modules.cts.service.PermitMicService;
import me.smhc.modules.cts.service.dto.PermitMicDto;
import me.smhc.modules.cts.service.dto.PermitMicQueryCriteria;
import me.smhc.modules.cts.service.mapper.PermitMicMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
* @author 布和
* @date 2020-04-16
*/
@Service
//@CacheConfig(cacheNames = "permitMic")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PermitMicServiceImpl implements PermitMicService {

    private final PermitMicRepository permitMicRepository;

    private final PermitMicMapper permitMicMapper;

    private final UserService userService;

    public PermitMicServiceImpl(PermitMicRepository permitMicRepository, PermitMicMapper permitMicMapper, UserService userService) {
        this.permitMicRepository = permitMicRepository;
        this.permitMicMapper = permitMicMapper;
        this.userService = userService;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(PermitMicQueryCriteria criteria, Pageable pageable){
        Page<PermitMic> page = permitMicRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(permitMicMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<PermitMicDto> queryAll(PermitMicQueryCriteria criteria){
        return permitMicMapper.toDto(permitMicRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public PermitMicDto findById(Long id) {
        PermitMic permitMic = permitMicRepository.findById(id).orElseGet(PermitMic::new);
        ValidationUtil.isNull(permitMic.getId(),"PermitMic","id",id);
        return permitMicMapper.toDto(permitMic);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public PermitMicDto create(PermitMic resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId());
        return permitMicMapper.toDto(permitMicRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(PermitMic resources) {
        PermitMic permitMic = permitMicRepository.findById(resources.getId()).orElseGet(PermitMic::new);
        ValidationUtil.isNull( permitMic.getId(),"PermitMic","id",resources.getId());
        permitMic.copy(resources);
        permitMicRepository.save(permitMic);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            permitMicRepository.deleteById(id);
        }
    }

    @Override
//    @Cacheable(key = "#fileName")
    public PermitMic findByFileName(String fileName) {
        return permitMicRepository.findByFileName(fileName);
    }

    @Override
    public void addPermitMic(File file) {
        PermitMic permitMic = new PermitMic();
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
                permitMic.setRa1(commStr.substring(lengthArray[0], lengthArray[1]));
                permitMic.setBc(commStr.substring(lengthArray[1], lengthArray[2]));
                permitMic.setCic(commStr.substring(lengthArray[2], lengthArray[3]));
                permitMic.setMd(commStr.substring(lengthArray[3], lengthArray[4]));
                permitMic.setUc(commStr.substring(lengthArray[4], lengthArray[5]));
                permitMic.setRa2(commStr.substring(lengthArray[5], lengthArray[6]));
                permitMic.setLtn(commStr.substring(lengthArray[6], lengthArray[7]));
                permitMic.setRa3(commStr.substring(lengthArray[7], lengthArray[8]));
                permitMic.setSubject(commStr.substring(lengthArray[8], lengthArray[9]));
                permitMic.setRa4(commStr.substring(lengthArray[9], lengthArray[10]));
                permitMic.setMti(commStr.substring(lengthArray[10], lengthArray[11]));
                permitMic.setMciDsn(commStr.substring(lengthArray[11], lengthArray[12]));
                permitMic.setMciFd(commStr.substring(lengthArray[12], lengthArray[13]));
                permitMic.setMciMt(commStr.substring(lengthArray[13], lengthArray[14]));
                permitMic.setMciRa(commStr.substring(lengthArray[14], lengthArray[15]));
                permitMic.setIicn(commStr.substring(lengthArray[15], lengthArray[16]));
                permitMic.setIti(commStr.substring(lengthArray[16], lengthArray[17]));
                permitMic.setAf(commStr.substring(lengthArray[17], lengthArray[18]));
                permitMic.setRa5(commStr.substring(lengthArray[18], lengthArray[19]));
                permitMic.setTc(commStr.substring(lengthArray[19], lengthArray[20]));

                permitMic.setIc1(list.get(1));
                permitMic.setSkb(list.get(2));
                permitMic.setRii(list.get(3));
                permitMic.setChz(list.get(4));
                permitMic.setChb(list.get(5));
                permitMic.setIct(list.get(6));
                permitMic.setIcn(list.get(7));
                permitMic.setJyo(list.get(8));
                permitMic.setIcd(list.get(9));
                permitMic.setIce(list.get(10));
                permitMic.setImc(list.get(11));
                permitMic.setImn(list.get(12));
                permitMic.setImy(list.get(13));
                permitMic.setIma(list.get(14));
                permitMic.setIma2(list.get(15));
                permitMic.setIma3(list.get(16));
                permitMic.setIma4(list.get(17));
                permitMic.setIad(list.get(18));
                permitMic.setImt(list.get(19));
                permitMic.setZjy(list.get(20));
                permitMic.setZjj(list.get(21));
                permitMic.setZjn(list.get(22));
                permitMic.setEpc(list.get(23));
                permitMic.setEpn(list.get(24));
                permitMic.setEpy(list.get(25));
                permitMic.setEpa(list.get(26));
                permitMic.setEp2(list.get(27));
                permitMic.setEp3(list.get(28));
                permitMic.setEp4(list.get(29));
                permitMic.setEpo(list.get(30));
                permitMic.setEad(list.get(31));
                permitMic.setAgc(list.get(32));
                permitMic.setAgn(list.get(33));
                permitMic.setSn(list.get(34));
                permitMic.setTtc(list.get(35));
                permitMic.setHab(list.get(36));
                permitMic.setMab(list.get(37));
                permitMic.setDst(list.get(38));
                permitMic.setPn(list.get(39));
                permitMic.setPsc(list.get(40));
                permitMic.setPsn(list.get(41));
                permitMic.setVsn(list.get(42));
                permitMic.setArr(list.get(43));
                permitMic.setSct(list.get(44));
                permitMic.setWcd(list.get(45));
                permitMic.setNo(list.get(46));
                permitMic.setGw(list.get(47));
                permitMic.setNocc(list.get(48));
                permitMic.setNocn(list.get(49));
                permitMic.setCerc(list.get(50));
                permitMic.setCcr(list.get(51));
                permitMic.setCerc1(list.get(52));
                permitMic.setCcr1(list.get(53));
                permitMic.setCerc2(list.get(54));
                permitMic.setCcr2(list.get(55));
                permitMic.setIp1(list.get(56));
                permitMic.setIp2(list.get(57));
                permitMic.setIp3(list.get(58));
                permitMic.setIp4(list.get(59));
                permitMic.setFr1(list.get(60));
                permitMic.setFr2(list.get(61));
                permitMic.setFr3(list.get(62));
                permitMic.setIn1(list.get(63));
                permitMic.setIn2(list.get(64));
                permitMic.setIn3(list.get(65));
                permitMic.setCmn(list.get(66));
                permitMic.setDpr(list.get(67));
                permitMic.setTpi(list.get(68));
                permitMic.setOr(list.get(69));
                permitMic.setPo(list.get(70));
                permitMic.setNt1(list.get(71));
                permitMic.setImi(list.get(72));
                permitMic.setRef(list.get(73));
                permitMic.setNsc(list.get(74));
                permitMic.setNrn(list.get(75));
                permitMic.setNoc(list.get(76));
                permitMic.setIid(list.get(77));
                permitMic.setEor(list.get(78));
                permitMic.setFui(list.get(79));
                permitMic.setFileName(file.getName());

                UserDto userDto = userService.findByName(SecurityUtils.getUsername());
                permitMic.setCreateUserId(userDto.getId());
                permitMic.setUpdateUserId(userDto.getId());
                PermitMicDto permitMicDto=this.create(permitMic);
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
    public void download(List<PermitMicDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PermitMicDto permitMic : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("((予約エリア))", permitMic.getRa1());
            map.put("(業務コード)", permitMic.getBc());
            map.put("(出力情報コード)", permitMic.getCic());
            map.put("(電文受信日時)", permitMic.getMd());
            map.put("(利用者コード)", permitMic.getUc());
            map.put("((予約エリア))", permitMic.getRa2());
            map.put("(論理端末名)", permitMic.getLtn());
            map.put("((予約エリア))", permitMic.getRa3());
            map.put("(Subject)", permitMic.getSubject());
            map.put("((予約エリア))", permitMic.getRa4());
            map.put("(電文引継情報)", permitMic.getMti());
            map.put("(電文制御情報-分割通番)", permitMic.getMciDsn());
            map.put("(電文制御情報-最終表示)", permitMic.getMciFd());
            map.put("(電文制御情報-電文種別)", permitMic.getMciMt());
            map.put("(電文制御情報-(予約エリア))", permitMic.getMciRa());
            map.put("(入力情報特定番号)", permitMic.getIicn());
            map.put("(索引引継情報)", permitMic.getIti());
            map.put("(宛管形式)", permitMic.getAf());
            map.put("((予約エリア))", permitMic.getRa5());
            map.put("(電文長)", permitMic.getTc());
            map.put("(申告先種別コード)", permitMic.getIc1());
            map.put("(識別符号)", permitMic.getSkb());
            map.put("(審査検査区分識別)", permitMic.getRii());
            map.put("(あて先税関)", permitMic.getChz());
            map.put("(あて先部門コード)", permitMic.getChb());
            map.put("(申告年月日)", permitMic.getIct());
            map.put("(申告番号)", permitMic.getIcn());
            map.put("(申告条件コード)", permitMic.getJyo());
            map.put("(申告予定年月日)", permitMic.getIcd());
            map.put("(本申告表示)", permitMic.getIce());
            map.put("(輸入者コード)", permitMic.getImc());
            map.put("(輸入者名)", permitMic.getImn());
            map.put("(郵便番号)", permitMic.getImy());
            map.put("(住所１（都道府県）)", permitMic.getIma());
            map.put("(住所２（市区町村)", permitMic.getIma2());
            map.put("(住所３（町域名・番)", permitMic.getIma3());
            map.put("(住所４（ビル名ほ)", permitMic.getIma4());
            map.put("(輸入者住所)", permitMic.getIad());
            map.put("(輸入者電話番号)", permitMic.getImt());
            map.put("(税関事務管理人コー)", permitMic.getZjy());
            map.put("(税関事務管理人受理)", permitMic.getZjj());
            map.put("(税関事務管理人名)", permitMic.getZjn());
            map.put("(仕出人コード)", permitMic.getEpc());
            map.put("(仕出人名)", permitMic.getEpn());
            map.put("(郵便番号（Postcode)", permitMic.getEpy());
            map.put("(住所１（Street)", permitMic.getEpa());
            map.put("(住所２（Street)", permitMic.getEp2());
            map.put("(住所３（City)", permitMic.getEp3());
            map.put("(住所４（Country)", permitMic.getEp4());
            map.put("(国名コード)", permitMic.getEpo());
            map.put("(仕出人住所)", permitMic.getEad());
            map.put("(代理人コード)", permitMic.getAgc());
            map.put("(代理人名)", permitMic.getAgn());
            map.put("(通関士コード)", permitMic.getSn());
            map.put("(検査立会者)", permitMic.getTtc());
            map.put("(ＨＡＷＢ番号)", permitMic.getHab());
            map.put("(ＭＡＷＢ番号)", permitMic.getMab());
            map.put("(取卸港コード)", permitMic.getDst());
            map.put("(取卸港名)", permitMic.getPn());
            map.put("(積出地コード)", permitMic.getPsc());
            map.put("(積出地名)", permitMic.getPsn());
            map.put("(積載機名)", permitMic.getVsn());
            map.put("(入港年月日)", permitMic.getArr());
            map.put("(蔵置税関)", permitMic.getSct());
            map.put("(蔵置税関部門)", permitMic.getWcd());
            map.put("(貨物個数)", permitMic.getNo());
            map.put("(貨物重量（グロス）)", permitMic.getGw());
            map.put("(通関蔵置場コード)", permitMic.getNocc());
            map.put("(通関蔵置場名)", permitMic.getNocn());
            map.put("(通貨換算レート通貨コード)", permitMic.getCerc());
            map.put("(通貨換算レート)", permitMic.getCcr());
            map.put("(通貨換算レート通貨コード1)", permitMic.getCerc1());
            map.put("(通貨換算レート1)", permitMic.getCcr1());
            map.put("(通貨換算レート通貨コード2)", permitMic.getCerc2());
            map.put("(通貨換算レート2)", permitMic.getCcr2());
            map.put("(インボイス価格区分)", permitMic.getIp1());
            map.put("(インボイス価格条件)", permitMic.getIp2());
            map.put("(インボイス通貨コー)", permitMic.getIp3());
            map.put("(インボイス価格)", permitMic.getIp4());
            map.put("(運賃区分コード)", permitMic.getFr1());
            map.put("(運賃通貨コード)", permitMic.getFr2());
            map.put("(運賃)", permitMic.getFr3());
            map.put("(保険区分コード)", permitMic.getIn1());
            map.put("(保険通貨コード)", permitMic.getIn2());
            map.put("(保険金額)", permitMic.getIn3());
            map.put("(品名)", permitMic.getCmn());
            map.put("(課税価格)", permitMic.getDpr());
            map.put("(課税価格入力識別)", permitMic.getTpi());
            map.put("(原産地コード)", permitMic.getOr());
            map.put("(原産地名)", permitMic.getPo());
            map.put("(記事)", permitMic.getNt1());
            map.put("(輸入者（入力）)", permitMic.getImi());
            map.put("(社内整理用番号)", permitMic.getRef());
            map.put("(荷主セクションコー)", permitMic.getNsc());
            map.put("(荷主リファレンスナ)", permitMic.getNrn());
            map.put("(税関官署長名)", permitMic.getNoc());
            map.put("(輸入許可年月日)", permitMic.getIid());
            map.put("(審査終了年月日)", permitMic.getEor());
            map.put("(事後審査識別)", permitMic.getFui());
            map.put("電文ファイル名", permitMic.getFileName());
            map.put(" createTime",  permitMic.getCreateTime());
            map.put(" updateTime",  permitMic.getUpdateTime());
            map.put(" updateUserId",  permitMic.getUpdateUserId());
            map.put(" createUserId",  permitMic.getCreateUserId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
