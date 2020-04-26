package me.smhc.modules.cts.service.impl;

import me.smhc.modules.cts.domain.Permit;
import me.smhc.modules.cts.repository.PermitRepository;
import me.smhc.modules.cts.service.PermitService;
import me.smhc.modules.cts.service.dto.PermitDto;
import me.smhc.modules.cts.service.dto.PermitQueryCriteria;
import me.smhc.modules.cts.service.mapper.PermitMapper;
import me.smhc.modules.system.service.UserService;
import me.smhc.utils.PageUtil;
import me.smhc.utils.QueryHelp;
import me.smhc.utils.ValidationUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;

/**
* @author 布和
* @date 2020-04-16
*/
@Service
@CacheConfig(cacheNames = "permit")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PermitServiceImpl implements PermitService {

    private final PermitRepository permitRepository;

    private final PermitMapper permitMapper;

    private final UserService userService;

    public PermitServiceImpl(PermitRepository permitRepository, PermitMapper permitMapper, UserService userService) {
        this.permitRepository = permitRepository;
        this.permitMapper = permitMapper;
        this.userService = userService;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(PermitQueryCriteria criteria, Pageable pageable){
        Page<Permit> page = permitRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(permitMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<PermitDto> queryAll(PermitQueryCriteria criteria){
        return permitMapper.toDto(permitRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public PermitDto findById(Long id) {
        Permit permit = permitRepository.findById(id).orElseGet(Permit::new);
        ValidationUtil.isNull(permit.getId(),"Permit","id",id);
        return permitMapper.toDto(permit);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public PermitDto create(Permit resources) {
        return permitMapper.toDto(permitRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Permit resources) {
        Permit permit = permitRepository.findById(resources.getId()).orElseGet(Permit::new);
        ValidationUtil.isNull( permit.getId(),"Permit","id",resources.getId());
        permit.copy(resources);
        permitRepository.save(permit);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            permitRepository.deleteById(id);
        }
    }

    @Override
//    @Cacheable(key = "#fileName")
    public Permit findByFileName(String fileName) {
        return permitRepository.findByFileName(fileName);
    }
}
