package me.smhc.modules.master.service;

import me.smhc.modules.master.domain.Agency;
import me.smhc.modules.master.service.dto.AgencyDto;
import me.smhc.modules.master.service.dto.AgencyQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author 布和
* @date 2020-03-24
*/
public interface AgencyService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(AgencyQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<AgencyDto>
    */
    List<AgencyDto> queryAll(AgencyQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return AgencyDto
     */
    AgencyDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return AgencyDto
    */
    AgencyDto create(Agency resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Agency resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<AgencyDto> all, HttpServletResponse response) throws IOException;
}
