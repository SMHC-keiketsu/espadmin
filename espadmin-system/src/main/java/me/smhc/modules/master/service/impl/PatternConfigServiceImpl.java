package me.smhc.modules.master.service.impl;

import me.smhc.modules.master.domain.PatternConfig;
import me.smhc.utils.ValidationUtil;
import me.smhc.utils.FileUtil;
import me.smhc.modules.master.repository.PatternConfigRepository;
import me.smhc.modules.master.service.PatternConfigService;
import me.smhc.modules.master.service.dto.PatternConfigDto;
import me.smhc.modules.master.service.dto.PatternConfigQueryCriteria;
import me.smhc.modules.master.service.mapper.PatternConfigMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
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
* @author 布和
* @date 2020-04-08
*/
@Service
//@CacheConfig(cacheNames = "patternConfig")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PatternConfigServiceImpl implements PatternConfigService {

    private final PatternConfigRepository patternConfigRepository;

    private final PatternConfigMapper patternConfigMapper;

    public PatternConfigServiceImpl(PatternConfigRepository patternConfigRepository, PatternConfigMapper patternConfigMapper) {
        this.patternConfigRepository = patternConfigRepository;
        this.patternConfigMapper = patternConfigMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(PatternConfigQueryCriteria criteria, Pageable pageable){
        Page<PatternConfig> page = patternConfigRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(patternConfigMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<PatternConfigDto> queryAll(PatternConfigQueryCriteria criteria){
        return patternConfigMapper.toDto(patternConfigRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public PatternConfigDto findById(Long id) {
        PatternConfig patternConfig = patternConfigRepository.findById(id).orElseGet(PatternConfig::new);
        ValidationUtil.isNull(patternConfig.getId(),"PatternConfig","id",id);
        return patternConfigMapper.toDto(patternConfig);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public PatternConfigDto create(PatternConfig resources) {
        return patternConfigMapper.toDto(patternConfigRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(PatternConfig resources) {
        PatternConfig patternConfig = patternConfigRepository.findById(resources.getId()).orElseGet(PatternConfig::new);
        ValidationUtil.isNull( patternConfig.getId(),"PatternConfig","id",resources.getId());
        patternConfig.copy(resources);
        patternConfigRepository.save(patternConfig);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            patternConfigRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<PatternConfigDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PatternConfigDto patternConfig : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" deptId",  patternConfig.getDeptId());
            map.put("同一部门内，名字不可重复", patternConfig.getName());
            map.put("IDA配置", patternConfig.getIdaConfig());
            map.put("MIC配置", patternConfig.getMicConfig());
            map.put("HCH配置", patternConfig.getHchConfig());
            map.put(" createTime",  patternConfig.getCreateTime());
            map.put(" createUserId",  patternConfig.getCreateUserId());
            map.put(" updateTime",  patternConfig.getUpdateTime());
            map.put(" updateUserId",  patternConfig.getUpdateUserId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
