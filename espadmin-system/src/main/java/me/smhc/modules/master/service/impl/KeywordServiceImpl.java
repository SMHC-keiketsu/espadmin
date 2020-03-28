package me.smhc.modules.master.service.impl;

import me.smhc.modules.master.domain.Keyword;
import me.smhc.modules.master.repository.KeywordRepository;
import me.smhc.modules.master.service.KeywordService;
import me.smhc.modules.master.service.dto.KeywordDto;
import me.smhc.modules.master.service.dto.KeywordQueryCriteria;
import me.smhc.modules.master.service.mapper.KeywordMapper;
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

/**
* @author 布和
* @date 2020-03-19
*/
@Service
@CacheConfig(cacheNames = "keyword")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;

    private final KeywordMapper keywordMapper;

    private final UserService userService;

    public KeywordServiceImpl(KeywordRepository keywordRepository, KeywordMapper keywordMapper, UserService userService) {
        this.keywordRepository = keywordRepository;
        this.keywordMapper = keywordMapper;
        this.userService = userService;
    }

    @Override
    public Map<String,Object> queryAll(KeywordQueryCriteria criteria, Pageable pageable){
        Page<Keyword> page = keywordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(keywordMapper::toDto));
    }

    @Override
    @Cacheable(key="#criteria+''+#pageable")
    public List<KeywordDto> queryAll(KeywordQueryCriteria criteria){
        return keywordMapper.toDto(keywordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public KeywordDto findById(Long id) {
        Keyword keyword = keywordRepository.findById(id).orElseGet(Keyword::new);
        ValidationUtil.isNull(keyword.getId(),"Keyword","id",id);
        return keywordMapper.toDto(keyword);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public KeywordDto create(Keyword resources) {
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        resources.setCreateUserId(userDto.getId());
        resources.setUpdateUserId(userDto.getId());
        return keywordMapper.toDto(keywordRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Keyword resources) {
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        resources.setUpdateUserId(userDto.getId());
        Keyword keyword = keywordRepository.findById(resources.getId()).orElseGet(Keyword::new);
        ValidationUtil.isNull( keyword.getId(),"Keyword","id",resources.getId());
        keyword.copy(resources);
        keywordRepository.save(keyword);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            keywordRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<KeywordDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (KeywordDto keyword : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("キーワード", keyword.getProductName());
            map.put("作成日時", keyword.getCreateTime());
            map.put("更新日時", keyword.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
