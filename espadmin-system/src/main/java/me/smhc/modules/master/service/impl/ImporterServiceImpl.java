package me.smhc.modules.master.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import me.smhc.modules.cts.domain.ManifestHawb;
import me.smhc.modules.master.domain.Importer;
import me.smhc.modules.master.repository.ImporterRepository;
import me.smhc.modules.master.service.ImporterService;
import me.smhc.modules.master.service.dto.ImporterDto;
import me.smhc.modules.master.service.dto.ImporterQueryCriteria;
import me.smhc.modules.master.service.mapper.ImporterMapper;
import me.smhc.modules.system.service.UserService;
import me.smhc.modules.system.service.dto.UserDto;
import me.smhc.utils.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
* @author 布和
* @date 2020-03-25
*/
@Service
@CacheConfig(cacheNames = "importer")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ImporterServiceImpl implements ImporterService {

    private final ImporterRepository importerRepository;

    private final ImporterMapper importerMapper;

    private final UserService userService;

    private final RedisUtils redisUtils;

    public ImporterServiceImpl(ImporterRepository importerRepository, ImporterMapper importerMapper, UserService userService, RedisUtils redisUtils) {
        this.importerRepository = importerRepository;
        this.importerMapper = importerMapper;
        this.userService = userService;
        this.redisUtils = redisUtils;
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
    @Cacheable(key = "#p0")
    public List<ImporterDto> findByTel(String tel) {
        List<Importer> importers = importerRepository.findByTel(tel);
        return  importerMapper.toDto(importers);
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
        String pattern = "dept::*";
        List<String> keys = redisUtils.scan(pattern);
        redisUtils.del(keys.toArray(new String[keys.size()]));
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
            map.put("輸入者符号", importer.getImc());
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

    /**
     * 保存manifest的输入者
     * @param manifestHawbList hawbList
     */
    public void saveImporter(List<ManifestHawb> manifestHawbList){
        if(ObjectUtil.isNotEmpty(manifestHawbList)){
            UserDto userDto = userService.findByName(SecurityUtils.getUsername());
            for (ManifestHawb temp: manifestHawbList) {
                List<Importer> importerList = importerRepository.findByTel(temp.getImporterTel());
                Importer importer = new Importer();
                if(ObjectUtil.isNotEmpty(importerList) && importerList.size() == 1){
                    // update
                    importer = importerList.get(0);
                    importer.setImc(temp.getImc());
                    importer.setCorporateNumber(temp.getImporterName());
                    importer.setPostalCode(temp.getImporterPosterCode());
                    importer.setEnAddressAll(temp.getImporterAddrAll());
                    importer.setEnAddress1(temp.getImporterAddr1());
                    importer.setEnAddress2(temp.getImporterAddr2());
                    importer.setEnAddress3(temp.getImporterAddr3());
                    importer.setEnAddress4(temp.getImporterAddr4());
                    importer.setUpdateUserId(userDto.getId());
                    importerRepository.save(importer);
                }else if(ObjectUtil.isEmpty(importerList)){
                    // add
                    importer.setImc(temp.getImc());
                    importer.setCorporateNumber(temp.getImporterName());
                    importer.setPostalCode(temp.getImporterPosterCode());
                    importer.setEnAddressAll(temp.getImporterAddrAll());
                    importer.setEnAddress1(temp.getImporterAddr1());
                    importer.setEnAddress2(temp.getImporterAddr2());
                    importer.setEnAddress3(temp.getImporterAddr3());
                    importer.setEnAddress4(temp.getImporterAddr4());
                    importer.setCreateUserId(userDto.getId());
                    importer.setUpdateUserId(userDto.getId());
                    importerRepository.save(importer);
                }
            }
        }

    }
}
