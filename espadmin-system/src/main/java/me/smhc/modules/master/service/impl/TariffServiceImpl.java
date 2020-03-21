package me.smhc.modules.master.service.impl;

import me.smhc.modules.master.domain.Tariff;
import me.smhc.modules.system.service.UserService;
import me.smhc.modules.system.service.dto.UserDto;
import me.smhc.utils.*;
import me.smhc.modules.master.repository.TariffRepository;
import me.smhc.modules.master.service.TariffService;
import me.smhc.modules.master.service.dto.TariffDto;
import me.smhc.modules.master.service.dto.TariffQueryCriteria;
import me.smhc.modules.master.service.mapper.TariffMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author 布和
* @date 2020-03-17
*/
@Service
@CacheConfig(cacheNames = "tariff")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;

    private final TariffMapper tariffMapper;

    private final UserService userService;

    public TariffServiceImpl(TariffRepository tariffRepository, TariffMapper tariffMapper, UserService userService) {
        this.tariffRepository = tariffRepository;
        this.tariffMapper = tariffMapper;
        this.userService = userService;
    }

    @Override
    @Cacheable
    public Map<String,Object> queryAll(TariffQueryCriteria criteria, Pageable pageable){

        Page<Tariff> page = tariffRepository.findAll(
                (root, criteriaQuery, criteriaBuilder) ->
                        QueryHelp.getPredicate(root,criteria,criteriaBuilder)
                ,pageable);
        return PageUtil.toPage(page.map(tariffMapper::toDto));
    }

    @Override
    @Cacheable
    public List<TariffDto> queryAll(TariffQueryCriteria criteria){
        return tariffMapper.toDto(tariffRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public TariffDto findById(Long id) {
        Tariff tariff = tariffRepository.findById(id).orElseGet(Tariff::new);
        ValidationUtil.isNull(tariff.getId(),"Tariff","id",id);
        return tariffMapper.toDto(tariff);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public TariffDto create(Tariff resources) {
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        resources.setCreateUserId(userDto.getId());
        resources.setUpdateUserId(userDto.getId());
        return tariffMapper.toDto(tariffRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Tariff resources) {
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        resources.setUpdateUserId(userDto.getId());
        Tariff tariff = tariffRepository.findById(resources.getId()).orElseGet(Tariff::new);
        ValidationUtil.isNull( tariff.getId(),"Tariff","id",resources.getId());
        tariff.copy(resources);
        tariffRepository.save(tariff);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            tariffRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TariffDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TariffDto tariff : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("関税名", tariff.getCustoms());
            map.put("ISO", tariff.getCifIso());
            map.put("CIF（円）", tariff.getCifValue());
            map.put("CIF条件", tariff.getCifLogic()? "以上" : "以下");
            map.put("重量（KG）", tariff.getWeightAmount());
            map.put("重量单位", tariff.getWeightUnit());
            map.put("重量条件（KG）", tariff.getWeightLogic()? "以上" : "以下");
            map.put("作成日時",  tariff.getCreateTime());
            map.put("更新日時", tariff.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
