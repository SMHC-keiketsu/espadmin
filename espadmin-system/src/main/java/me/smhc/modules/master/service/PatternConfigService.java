package me.smhc.modules.master.service;

import me.smhc.modules.master.domain.PatternConfig;
import me.smhc.modules.master.service.dto.PatternConfigDto;
import me.smhc.modules.master.service.dto.PatternConfigQueryCriteria;
import me.smhc.modules.system.domain.Dept;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author 布和
* @date 2020-04-08
*/
public interface PatternConfigService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(PatternConfigQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<PatternConfigDto>
    */
    List<PatternConfigDto> queryAll(PatternConfigQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return PatternConfigDto
     */
    PatternConfigDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return PatternConfigDto
    */
    PatternConfigDto create(PatternConfig resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(PatternConfig resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据findName
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<PatternConfigDto> all, HttpServletResponse response) throws IOException;

    /**
     * 查询名称
     * @param name
     * @param dept
     * @return
     */
    Boolean checkName(String name, Dept dept, Long id);

    /**
     * 查询名称是否是唯一
     * @param name
     * @param dept
     * @return
     */
    Boolean findByName(String name, Dept dept);
}
