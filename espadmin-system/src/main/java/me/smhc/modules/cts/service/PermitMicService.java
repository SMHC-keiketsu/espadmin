package me.smhc.modules.cts.service;

import me.smhc.modules.cts.domain.PermitMic;
import me.smhc.modules.cts.service.dto.PermitMicDto;
import me.smhc.modules.cts.service.dto.PermitMicQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author 布和
* @date 2020-04-16
*/
public interface PermitMicService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(PermitMicQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<PermitMicDto>
    */
    List<PermitMicDto> queryAll(PermitMicQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return PermitMicDto
     */
    PermitMicDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return PermitMicDto
    */
    PermitMicDto create(PermitMic resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(PermitMic resources);

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
    void download(List<PermitMicDto> all, HttpServletResponse response) throws IOException;

    PermitMic findByFileName(String fileName);

    void addPermitMic(File file);
}