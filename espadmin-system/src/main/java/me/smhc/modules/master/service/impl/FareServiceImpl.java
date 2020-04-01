package me.smhc.modules.master.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import me.smhc.exception.BadRequestException;
import me.smhc.exception.EntityExistException;
import me.smhc.modules.master.domain.Fare;
import me.smhc.modules.master.repository.FareRepository;
import me.smhc.modules.master.service.FareService;
import me.smhc.modules.master.service.dto.FareDto;
import me.smhc.modules.master.service.dto.FareQueryCriteria;
import me.smhc.modules.master.service.mapper.FareMapper;
import me.smhc.modules.system.domain.Dept;
import me.smhc.modules.system.service.DeptService;
import me.smhc.modules.system.service.UserService;
import me.smhc.modules.system.service.dto.DeptDto;
import me.smhc.modules.system.service.dto.UserDto;
import me.smhc.utils.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @author 布和
* @date 2020-03-20
*/
@Service
@CacheConfig(cacheNames = "fare")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class FareServiceImpl implements FareService {

    private final FareRepository fareRepository;

    private final FareMapper fareMapper;

    private final UserService userService;

    private final DeptService deptService;

    @Value("${file.maxSize}")
    private long maxSize;

    public FareServiceImpl(FareRepository fareRepository, FareMapper fareMapper, UserService userService, DeptService deptService) {
        this.fareRepository = fareRepository;
        this.fareMapper = fareMapper;
        this.userService = userService;
        this.deptService = deptService;
    }

    @Override
    public Map<String,Object> queryAll(FareQueryCriteria criteria, Pageable pageable){
        Page<Fare> page = fareRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(fareMapper::toDto));
    }

    @Override
    public List<FareDto> queryAll(FareQueryCriteria criteria){
        return fareMapper.toDto(fareRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public FareDto findById(Long id) {
        Fare fare = fareRepository.findById(id).orElseGet(Fare::new);
        ValidationUtil.isNull(fare.getId(),"Fare","id",id);
        return fareMapper.toDto(fare);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public FareDto create(Fare resources) {
        UserDto userDto = userService.findByName(SecurityUtils.getUsername());
        resources.setCreateUserId(userDto.getId());
        resources.setUpdateUserId(userDto.getId());
        return fareMapper.toDto(fareRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(Long deptId, MultipartFile multipartFile) {
        FileUtil.checkSize(maxSize, multipartFile.getSize());
        ExcelReader reader = ExcelUtil.getReader(FileUtil.toFile(multipartFile));
        List<Map<String,Object>> readAll = reader.readAll();
        if(ObjectUtil.isNull(readAll)){
            throw new BadRequestException("Excel解析失敗");
        }
        try {
            UserDto userDto = userService.findByName(SecurityUtils.getUsername());
            DeptDto deptDto = deptService.findById(deptId);
            if(ObjectUtil.isNull(deptId)){
                deptId = userDto.getDept().getId();
            }
            for (Map<String,Object> row: readAll){
                Fare fare = new Fare();
                Dept dept = new Dept();
                dept.setId(deptId);
                fare.setWeight(new BigDecimal((row.get("重量").toString() )));
                fare.setPrice(new BigDecimal((row.get("価格").toString() )));
                fare.setDept(dept);
                fare.setIso((row.get("ISO").toString() ));
                fare.setUpdateUserId(userDto.getId());
                fare.setCreateUserId(userDto.getId());
                // check 检查 有没有重复
                if(fareRepository.findByDeptAndIsoAndPrice(fare.getDept(),fare.getIso(),fare.getPrice()) != null){
                    throw  new EntityExistException(Fare.class,"dept",fare.getDept().getName() + ",货币:" + fare.getIso() + ",价格:" + fare.getPrice());
                }
                this.create(fare);
            }
            return true;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    @CacheEvict(allEntries = true)
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
    @CacheEvict(allEntries = true)
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
            map.put("部门名称", fare.getDept().getName());
            map.put("重量", fare.getWeight());
            map.put("価格", fare.getPrice());
            map.put("ISO", fare.getIso());
            map.put("创建时间", fare.getCreateTime());
            map.put("更新时间", fare.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
