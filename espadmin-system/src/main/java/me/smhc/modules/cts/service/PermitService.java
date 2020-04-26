package me.smhc.modules.cts.service;

import me.smhc.modules.cts.domain.Permit;
import me.smhc.modules.cts.service.dto.PermitDto;
import me.smhc.modules.cts.service.dto.PermitQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @author 布和
* @date 2020-04-16
*/
public interface PermitService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(PermitQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<PermitMicDto>
    */
    List<PermitDto> queryAll(PermitQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return PermitMicDto
     */
    PermitDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return PermitMicDto
    */
    PermitDto create(Permit resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Permit resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
     * 查询名称
     * @param fileName
     * @return Permit
     */
    Permit findByFileName(String fileName);
}
