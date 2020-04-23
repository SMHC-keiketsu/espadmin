package me.smhc.modules.cts.service.impl;

import me.smhc.modules.cts.domain.ManifestHawb;
import me.smhc.modules.cts.repository.ManifestHawbRepository;
import me.smhc.modules.cts.service.ManifestHawbService;
import me.smhc.modules.cts.service.dto.ManifestHawbDto;
import me.smhc.modules.cts.service.dto.ManifestHawbImporterDto;
import me.smhc.modules.cts.service.dto.ManifestHawbQueryCriteria;
import me.smhc.modules.cts.service.mapper.ManifestHawbMapper;
import me.smhc.modules.system.service.UserService;
import me.smhc.modules.system.service.dto.UserDto;
import me.smhc.utils.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

// 默认不使用缓存

/**
* @author jhf
* @date 2020-03-24
*/
@Service
@CacheConfig(cacheNames = "manifestHawb")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ManifestHawbServiceImpl implements ManifestHawbService {

    private final ManifestHawbRepository manifestHawbRepository;

    private final ManifestHawbMapper manifestHawbMapper;

    private final RedisUtils redisUtils;

    private final UserService userService;


    public ManifestHawbServiceImpl(ManifestHawbRepository manifestHawbRepository, ManifestHawbMapper manifestHawbMapper, RedisUtils redisUtils, UserService userService) {
        this.manifestHawbRepository = manifestHawbRepository;
        this.manifestHawbMapper = manifestHawbMapper;
        this.redisUtils = redisUtils;
        this.userService = userService;
    }

    @Override
    @Cacheable
    public Map<String,Object> queryAll(ManifestHawbQueryCriteria criteria, Pageable pageable){
        Page<ManifestHawb> page = manifestHawbRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(manifestHawbMapper::toDto));
    }

    @Override
    @Cacheable
    public List<ManifestHawbDto> queryAll(ManifestHawbQueryCriteria criteria){
        return manifestHawbMapper.toDto(manifestHawbRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public List<ManifestHawb> findByIds(List<Long> ids) {
        return manifestHawbRepository.findByIdIn(ids);
    }

    @Override
    @Cacheable(key = "#p0")
    public ManifestHawbDto findById(Long id) {
        ManifestHawb manifestHawb = manifestHawbRepository.findById(id).orElseGet(ManifestHawb::new);
        ValidationUtil.isNull(manifestHawb.getId(),"Manifest","id",id);
        return manifestHawbMapper.toDto(manifestHawb);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public ManifestHawbDto create(ManifestHawb resources) {
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        resources.setCreateUserId(userDto.getId());
        resources.setUpdateUserId(userDto.getId());
        return manifestHawbMapper.toDto(manifestHawbRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(ManifestHawb resources) {
        ManifestHawb manifestHawb = manifestHawbRepository.findById(resources.getId()).orElseGet(ManifestHawb::new);
        ValidationUtil.isNull( manifestHawb.getId(),"Manifest","id",resources.getId());
        // 更新対象のデータを取得する際にも、バージョンのチェックを行うこと
        if(!manifestHawb.getReserve1().equals(resources.getReserve1())){
            throw new ObjectOptimisticLockingFailureException(ManifestHawb.class,"hawb:" + resources.getHawbNo()+"数据并发");
        }
        // 需要手动清理下manifestMawb缓存
        String pattern = "manifestMawb::*";
        List<String> keys = redisUtils.scan(pattern);
        redisUtils.del(keys.toArray(new String[keys.size()]));

        manifestHawb.copy(resources);
        manifestHawbRepository.save(manifestHawb);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            manifestHawbRepository.deleteById(id);
        }
    }

    @Override
    public List<ManifestHawbImporterDto> findDistinctImporter(List<Long> ids) {
        return manifestHawbRepository.findDistinctImporter(ids);
    }
}
