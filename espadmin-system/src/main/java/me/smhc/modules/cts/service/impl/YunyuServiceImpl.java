package me.smhc.modules.cts.service.impl;

import me.smhc.modules.cts.domain.Yunyu;
import me.smhc.utils.ValidationUtil;
import me.smhc.utils.FileUtil;
import me.smhc.modules.cts.repository.YunyuRepository;
import me.smhc.modules.cts.service.YunyuService;
import me.smhc.modules.cts.service.dto.YunyuDto;
import me.smhc.modules.cts.service.dto.YunyuQueryCriteria;
import me.smhc.modules.cts.service.mapper.YunyuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
// 默认不使用缓存
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.smhc.utils.PageUtil;
import me.smhc.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author jhf
* @date 2020-04-23
*/
@Service
@CacheConfig(cacheNames = "yunyu")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class YunyuServiceImpl implements YunyuService {

    private final YunyuRepository yunyuRepository;

    private final YunyuMapper yunyuMapper;

    public YunyuServiceImpl(YunyuRepository yunyuRepository, YunyuMapper yunyuMapper) {
        this.yunyuRepository = yunyuRepository;
        this.yunyuMapper = yunyuMapper;
    }

    @Override
    @Cacheable
    public Map<String,Object> queryAll(YunyuQueryCriteria criteria, Pageable pageable){
        Page<Yunyu> page = yunyuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(yunyuMapper::toDto));
    }

    @Override
    @Cacheable
    public List<YunyuDto> queryAll(YunyuQueryCriteria criteria){
        return yunyuMapper.toDto(yunyuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public YunyuDto findById(String yunyuCd) {
        Yunyu yunyu = yunyuRepository.findById(yunyuCd).orElseGet(Yunyu::new);
        ValidationUtil.isNull(yunyu.getYunyuCd(),"Yunyu","yunyuCd",yunyuCd);
        return yunyuMapper.toDto(yunyu);
    }

    @Override
    @Cacheable(key="#p0")
    public List<YunyuDto> findByKensakuTel(String kensakuTel){
        List<Yunyu> yunyus = yunyuRepository.findByKensakuTel(kensakuTel);
        return yunyuMapper.toDto(yunyus);
    }

    @Override
    @Cacheable(key = "#p0-#p1")
    public YunyuDto findByYunyuCdOrHoujinNo(String yunyuCd, String houjinNo){
        Yunyu yunyu = yunyuRepository.findByYunyuCdOrHoujinNo(yunyuCd,houjinNo);
        return yunyuMapper.toDto(yunyu);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public YunyuDto create(Yunyu resources) {
        resources.setYunyuCd(IdUtil.simpleUUID());
        return yunyuMapper.toDto(yunyuRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Yunyu resources) {
        Yunyu yunyu = yunyuRepository.findById(resources.getYunyuCd()).orElseGet(Yunyu::new);
        ValidationUtil.isNull( yunyu.getYunyuCd(),"Yunyu","id",resources.getYunyuCd());
        yunyu.copy(resources);
        yunyuRepository.save(yunyu);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll(String[] ids) {
        for (String yunyuCd : ids) {
            yunyuRepository.deleteById(yunyuCd);
        }
    }

    @Override
    public void download(List<YunyuDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (YunyuDto yunyu : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" houjinNo",  yunyu.getHoujinNo());
            map.put(" yunyuNmE",  yunyu.getYunyuNmE());
            map.put(" yunyuYuubinNo",  yunyu.getYunyuYuubinNo());
            map.put(" yunyuAddE1",  yunyu.getYunyuAddE1());
            map.put(" yunyuAddE2",  yunyu.getYunyuAddE2());
            map.put(" yunyuAddE3",  yunyu.getYunyuAddE3());
            map.put(" yunyuAddE4",  yunyu.getYunyuAddE4());
            map.put(" yunyuNmW",  yunyu.getYunyuNmW());
            map.put(" yunyuAddW",  yunyu.getYunyuAddW());
            map.put(" yunyuTel",  yunyu.getYunyuTel());
            map.put(" yunyuFax",  yunyu.getYunyuFax());
            map.put(" kensakuTel",  yunyu.getKensakuTel());
            map.put(" noukigenEncyoCd",  yunyu.getNoukigenEncyoCd());
            map.put(" noufuHouhou",  yunyu.getNoufuHouhou());
            map.put(" kouzaNo",  yunyu.getKouzaNo());
            map.put(" tanpoNo",  yunyu.getTanpoNo());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
