package me.smhc.modules.cts.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import me.smhc.modules.cts.domain.Permit;
import me.smhc.modules.cts.domain.PermitMic;
import me.smhc.modules.cts.repository.PermitMicRepository;
import me.smhc.modules.cts.service.PermitMicService;
import me.smhc.modules.cts.service.dto.PermitMicDto;
import me.smhc.modules.cts.service.mapper.PermitMicMapper;
import me.smhc.modules.system.service.UserService;
import me.smhc.modules.system.service.dto.UserDto;
import me.smhc.utils.SecurityUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

@Service
@CacheConfig(cacheNames = "permitMic")
public class PermitMicServiceImpl implements PermitMicService {

    private final UserService userService;
    private final PermitMicMapper permitMicMapper;
    private final PermitMicRepository permitMicRepository;
    private PermitMicDto permitMicDto;

    public PermitMicServiceImpl(UserService userService, PermitMicMapper permitMicMapper, PermitMicRepository permitMicRepository) {
        this.userService = userService;
        this.permitMicMapper = permitMicMapper;
        this.permitMicRepository = permitMicRepository;
    }


    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public PermitMicDto create(PermitMic resources) {
        return permitMicMapper.toDto(permitMicRepository.save(resources));
    }

    @Override
    public PermitMicDto addPermitMic(File file) {
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
                permitMicDto = this.create(permitMic);
            }else{
                new ResponseEntity<>("找不到指定的文件", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            new ResponseEntity<>("读取文件内容出错",HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
        return permitMicDto;
    }
}
