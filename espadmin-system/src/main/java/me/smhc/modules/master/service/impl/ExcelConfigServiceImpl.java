package me.smhc.modules.master.service.impl;

import me.smhc.modules.master.domain.ExcelConfig;
import me.smhc.modules.master.repository.ExcelConfigRepository;
import me.smhc.modules.master.service.ExcelConfigService;
import me.smhc.modules.master.service.dto.ExcelConfigDto;
import me.smhc.modules.master.service.dto.ExcelConfigQueryCriteria;
import me.smhc.modules.master.service.mapper.ExcelConfigMapper;
import me.smhc.modules.system.service.UserService;
import me.smhc.modules.system.service.dto.UserDto;
import me.smhc.utils.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


/**
* @author 布和
* @date 2020-03-26
*/
@Service
//@CacheConfig(cacheNames = "excelConfig")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ExcelConfigServiceImpl implements ExcelConfigService {

    private final ExcelConfigRepository excelConfigRepository;

    private final ExcelConfigMapper excelConfigMapper;
    private final RedisUtils redisUtils;
    private final UserService userService;

    public ExcelConfigServiceImpl(ExcelConfigRepository excelConfigRepository, ExcelConfigMapper excelConfigMapper, RedisUtils redisUtils, UserService userService) {
        this.excelConfigRepository = excelConfigRepository;
        this.excelConfigMapper = excelConfigMapper;
        this.redisUtils = redisUtils;
        this.userService = userService;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(ExcelConfigQueryCriteria criteria, Pageable pageable){
        Page<ExcelConfig> page = excelConfigRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(excelConfigMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<ExcelConfigDto> queryAll(ExcelConfigQueryCriteria criteria){
        return excelConfigMapper.toDto(excelConfigRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public ExcelConfigDto findById(Long id) {
        ExcelConfig excelConfig = excelConfigRepository.findById(id).orElseGet(ExcelConfig::new);
        ValidationUtil.isNull(excelConfig.getId(),"ExcelConfig","id",id);
        return excelConfigMapper.toDto(excelConfig);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public ExcelConfigDto create(ExcelConfig resources) {
        return excelConfigMapper.toDto(excelConfigRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(ExcelConfig resources) {
        ExcelConfig excelConfig = excelConfigRepository.findById(resources.getId()).orElseGet(ExcelConfig::new);
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        resources.setUpdateUserId(userDto.getId());
        ValidationUtil.isNull( excelConfig.getId(),"ExcelConfig","id",resources.getId());

        // 如果ExcelConfig改变了，需要手动清理下缓存
        String pattern = "agency::*";
        List<String> keys = redisUtils.scan(pattern);
        redisUtils.del(keys.toArray(new String[keys.size()]));
        pattern = "dept::*";
        keys = redisUtils.scan(pattern);
        redisUtils.del(keys.toArray(new String[keys.size()]));

        excelConfig.copy(resources);
        excelConfigRepository.save(excelConfig);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            excelConfigRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ExcelConfigDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ExcelConfigDto excelConfig : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" manifestExcel",  excelConfig.getManifestExcel());
            map.put(" createTime",  excelConfig.getCreateTime());
            map.put(" createUserId",  excelConfig.getCreateUserId());
            map.put(" updateTime",  excelConfig.getUpdateTime());
            map.put(" updateUserId",  excelConfig.getUpdateUserId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

}
