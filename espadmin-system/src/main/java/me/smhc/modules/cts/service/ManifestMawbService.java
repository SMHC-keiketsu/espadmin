package me.smhc.modules.cts.service;

import me.smhc.modules.cts.domain.ManifestMawb;
import me.smhc.modules.cts.service.dto.ManifestMawbDto;
import me.smhc.modules.cts.service.dto.ManifestMawbQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @author jhf
* @date 2020-03-24
*/
public interface ManifestMawbService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(ManifestMawbQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<ManifestMawbDto>
    */
    List<ManifestMawbDto> queryAll(ManifestMawbQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return ManifestDto
     */
    ManifestMawbDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return ManifestMawbDto
    */
    ManifestMawbDto create(ManifestMawb resources);

    /**
     * 上传
     * @param deptId 部门名称
     * @param file 文件
     * @return /
     */
    Boolean create(Long deptId, MultipartFile file);
    /**
    * 编辑
    * @param resources /
    */
    void update(ManifestMawb resources);

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
    void download(List<ManifestMawbDto> all, HttpServletResponse response) throws IOException;
}
