package me.smhc.modules.master.service.impl;

import me.smhc.modules.master.domain.Importer;
import me.smhc.modules.system.service.UserService;
import me.smhc.modules.system.service.dto.UserDto;
import me.smhc.utils.*;
import me.smhc.modules.master.repository.ImporterRepository;
import me.smhc.modules.master.service.ImporterService;
import me.smhc.modules.master.service.dto.ImporterDto;
import me.smhc.modules.master.service.dto.ImporterQueryCriteria;
import me.smhc.modules.master.service.mapper.ImporterMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author
* @date 2020-03-25
*/
@Service
@CacheConfig(cacheNames = "importer")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ImporterServiceImpl implements ImporterService {

    private final ImporterRepository importerRepository;

    private final ImporterMapper importerMapper;

    private final UserService userService;

    public ImporterServiceImpl(ImporterRepository importerRepository, ImporterMapper importerMapper, UserService userService) {
        this.importerRepository = importerRepository;
        this.importerMapper = importerMapper;
        this.userService = userService;
    }

    @Override
    //@Cacheable
    @Cacheable(key="#criteria+''+#pageable")
    public Map<String,Object> queryAll(ImporterQueryCriteria criteria, Pageable pageable){
        Page<Importer> page = importerRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(importerMapper::toDto));
    }

    @Override
    @Cacheable
    public List<ImporterDto> queryAll(ImporterQueryCriteria criteria){
        return importerMapper.toDto(importerRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public ImporterDto findById(Long id) {
        Importer importer = importerRepository.findById(id).orElseGet(Importer::new);
        ValidationUtil.isNull(importer.getId(),"Importer","id",id);
        return importerMapper.toDto(importer);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public ImporterDto create(Importer resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        resources.setUpdateUserId(userDto.getId());
        resources.setId(snowflake.nextId());
        return importerMapper.toDto(importerRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Importer resources) {
        Importer importer = importerRepository.findById(resources.getId()).orElseGet(Importer::new);
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        resources.setUpdateUserId(userDto.getId());
        ValidationUtil.isNull( importer.getId(),"Importer","id",resources.getId());
        importer.copy(resources);
        importerRepository.save(importer);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            importerRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ImporterDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ImporterDto importer : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("輸入者符号", importer.getJastproCode());
            map.put("法人番号", importer.getCorporateNumber());
            map.put("電話番号", importer.getTel());
            map.put("郵便番号", importer.getPostalCode());
            map.put("輸入者名（英文）", importer.getEnCompanyName());
            map.put("住所（英文）", importer.getEnAddressAll());
            map.put("住所_都道府県（英文）", importer.getEnAddress1());
            map.put("住所_市区町村（英文）", importer.getEnAddress2());
            map.put("住所_番地（英文）", importer.getEnAddress3());
            map.put("住所_建物（英文）", importer.getEnAddress4());
            map.put("輸入者名（和文）", importer.getJaCompanyName());
            map.put("住所（和文）", importer.getJaAddressAll());
            map.put("作成日時",  importer.getCreateTime());
            map.put("更新日時",  importer.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
