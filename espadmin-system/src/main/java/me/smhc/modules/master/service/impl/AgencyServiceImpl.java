package me.smhc.modules.master.service.impl;

import me.smhc.modules.master.domain.Agency;
import me.smhc.modules.master.domain.ExcelConfig;
import me.smhc.modules.master.repository.AgencyRepository;
import me.smhc.modules.master.repository.ExcelConfigRepository;
import me.smhc.modules.master.service.AgencyService;
import me.smhc.modules.master.service.ExcelConfigService;
import me.smhc.modules.master.service.dto.AgencyDto;
import me.smhc.modules.master.service.dto.AgencyQueryCriteria;
import me.smhc.modules.master.service.mapper.AgencyMapper;
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
* @date 2020-03-24
*/
@Service
@CacheConfig(cacheNames = "agency")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AgencyServiceImpl implements AgencyService {

    private final AgencyRepository agencyRepository;

    private final AgencyMapper agencyMapper;

    private final UserService userService;


    public AgencyServiceImpl(AgencyRepository agencyRepository, AgencyMapper agencyMapper, UserService userService) {
        this.agencyRepository = agencyRepository;
        this.agencyMapper = agencyMapper;
        this.userService = userService;
    }

    @Override
    public Map<String,Object> queryAll(AgencyQueryCriteria criteria, Pageable pageable){
        Page<Agency> page = agencyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(agencyMapper::toDto));
    }

    @Override
    @Cacheable
    public List<AgencyDto> queryAll(AgencyQueryCriteria criteria){
        return agencyMapper.toDto(agencyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public AgencyDto findById(Long id) {
        Agency agency = agencyRepository.findById(id).orElseGet(Agency::new);
        ValidationUtil.isNull(agency.getId(),"Agency","id",id);
        return agencyMapper.toDto(agency);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public AgencyDto create(Agency resources) {

        UserDto userDto = userService.findByName(SecurityUtils.getUsername());

        ExcelConfig excelConfig = new ExcelConfig();
        excelConfig.setCreateUserId(userDto.getId());
        excelConfig.setUpdateUserId(userDto.getId());

        resources.setExcelConfig(excelConfig);
        resources.setCreateUserId(userDto.getId());
        resources.setUpdateUserId(userDto.getId());
        return agencyMapper.toDto(agencyRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Agency resources) {
        Agency agency = agencyRepository.findById(resources.getId()).orElseGet(Agency::new);
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        resources.setUpdateUserId(userDto.getId());
        ValidationUtil.isNull( agency.getId(),"Agency","id",resources.getId());
        agency.copy(resources);
        agencyRepository.save(agency);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            agencyRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<AgencyDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AgencyDto agency : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("代理店名", agency.getCompanyName());
            map.put("担当者名", agency.getContact());
            map.put("メールアドレス", agency.getEmail());
            map.put("電話番号", agency.getTel());
            map.put("郵便番号", agency.getPostalCode());
            map.put("住所", agency.getAddressAll());
            map.put("作成日時",  agency.getCreateTime());
            map.put("更新日時",  agency.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
