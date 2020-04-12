package me.smhc.modules.master.service.impl;

import me.smhc.modules.master.domain.ExchangeRate;
import me.smhc.modules.system.service.UserService;
import me.smhc.modules.system.service.dto.UserDto;
import me.smhc.utils.*;
import me.smhc.modules.master.repository.ExchangeRateRepository;
import me.smhc.modules.master.service.ExchangeRateService;
import me.smhc.modules.master.service.dto.ExchangeRateDto;
import me.smhc.modules.master.service.dto.ExchangeRateQueryCriteria;
import me.smhc.modules.master.service.mapper.ExchangeRateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author 布和
* @date 2020-03-19
*/
@Service
@CacheConfig(cacheNames = "exchangeRate")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    private final ExchangeRateMapper exchangeRateMapper;

    private final UserService userService;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository, ExchangeRateMapper exchangeRateMapper, UserService userService) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.exchangeRateMapper = exchangeRateMapper;
        this.userService = userService;
    }

    @Override
    @Cacheable
    public Map<String,Object> queryAll(ExchangeRateQueryCriteria criteria, Pageable pageable){
        Page<ExchangeRate> page = exchangeRateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(exchangeRateMapper::toDto));
    }

    @Override
    @Cacheable
    public List<ExchangeRateDto> queryAll(ExchangeRateQueryCriteria criteria){
        return exchangeRateMapper.toDto(exchangeRateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public ExchangeRateDto findById(Long id) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id).orElseGet(ExchangeRate::new);
        ValidationUtil.isNull(exchangeRate.getId(),"ExchangeRate","id",id);
        return exchangeRateMapper.toDto(exchangeRate);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public ExchangeRateDto create(ExchangeRate resources) {
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        resources.setCreateUserId(userDto.getId());
        resources.setUpdateUserId(userDto.getId());
        return exchangeRateMapper.toDto(exchangeRateRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(ExchangeRate resources) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(resources.getId()).orElseGet(ExchangeRate::new);
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        resources.setUpdateUserId(userDto.getId());
        ValidationUtil.isNull( exchangeRate.getId(),"ExchangeRate","id",resources.getId());
        exchangeRate.copy(resources);
        exchangeRateRepository.save(exchangeRate);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            exchangeRateRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ExchangeRateDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ExchangeRateDto exchangeRate : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("开始日", exchangeRate.getStartDate());
            map.put("终了日", exchangeRate.getEndDate());
            map.put("ISO", exchangeRate.getIso());
            map.put("レート", exchangeRate.getRate());
            map.put("作成日時", exchangeRate.getCreateTime());
            map.put("更新日時", exchangeRate.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public BigDecimal findRateByIsoAndToday(String iso, String today) {
        return exchangeRateRepository.findRateByIsoAndToday(iso,today);
    }
}
