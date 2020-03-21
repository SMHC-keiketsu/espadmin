package me.smhc.modules.master.service;

import me.smhc.modules.master.domain.Fare;
import me.smhc.modules.master.service.dto.FareDto;
import me.smhc.modules.master.service.dto.FareQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author 布和
* @date 2020-03-20
*/
public interface FareService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(FareQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<FareDto>
    */
    List<FareDto> queryAll(FareQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return FareDto
     */
    FareDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return FareDto
    */
    FareDto create(Fare resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Fare resources);

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
    void download(List<FareDto> all, HttpServletResponse response) throws IOException;
}