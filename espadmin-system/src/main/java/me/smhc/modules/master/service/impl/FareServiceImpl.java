package me.smhc.modules.master.service.impl;

import me.smhc.modules.master.domain.Fare;
import me.smhc.modules.system.service.UserService;
import me.smhc.modules.system.service.dto.UserDto;
import me.smhc.utils.*;
import me.smhc.modules.master.repository.FareRepository;
import me.smhc.modules.master.service.FareService;
import me.smhc.modules.master.service.dto.FareDto;
import me.smhc.modules.master.service.dto.FareQueryCriteria;
import me.smhc.modules.master.service.mapper.FareMapper;
import org.hibernate.mapping.DependantValue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
* @author 布和
* @date 2020-03-20
*/
@Service
//@CacheConfig(cacheNames = "fare")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FareServiceImpl implements FareService {

    private final FareRepository fareRepository;

    private final FareMapper fareMapper;

    private final UserService userService;


    public FareServiceImpl(FareRepository fareRepository, FareMapper fareMapper, UserService userService) {
        this.fareRepository = fareRepository;
        this.fareMapper = fareMapper;
        this.userService = userService;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(FareQueryCriteria criteria, Pageable pageable){
        Page<Fare> page = fareRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
//        Page
        return PageUtil.toPage(page.map(fareMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<FareDto> queryAll(FareQueryCriteria criteria){
        return fareMapper.toDto(fareRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public FareDto findById(Long id) {
        Fare fare = fareRepository.findById(id).orElseGet(Fare::new);
        ValidationUtil.isNull(fare.getId(),"Fare","id",id);
        return fareMapper.toDto(fare);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public FareDto create(Fare resources) {
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        resources.setCreateUserId(userDto.getId());
        resources.setUpdateUserId(userDto.getId());
        return fareMapper.toDto(fareRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Fare resources) {
        Fare fare = fareRepository.findById(resources.getId()).orElseGet(Fare::new);
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        resources.setUpdateUserId(userDto.getId());
        ValidationUtil.isNull( fare.getId(),"Fare","id",resources.getId());
        fare.copy(resources);
        fareRepository.save(fare);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            fareRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<FareDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (FareDto fare : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("部门名称", fare.getDeptId());
            map.put("重量", fare.getWeight());
            map.put("価格", fare.getPrice());
            map.put("ISO", fare.getCurrency());
            map.put("创建时间", fare.getCreateTime());
            map.put("更新时间", fare.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
