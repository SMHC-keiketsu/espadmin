package me.smhc.modules.cts.service;

import me.smhc.modules.cts.domain.Manifest;
import me.smhc.modules.cts.service.dto.ManifestDto;
import me.smhc.modules.cts.service.dto.ManifestQueryCriteria;
import me.smhc.service.dto.LocalStorageDto;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author jhf
* @date 2020-03-24
*/
public interface ManifestService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(ManifestQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<ManifestDto>
    */
    List<ManifestDto> queryAll(ManifestQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return ManifestDto
     */
    ManifestDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return ManifestDto
    */
    ManifestDto create(Manifest resources);

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
    void update(Manifest resources);

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
    void download(List<ManifestDto> all, HttpServletResponse response) throws IOException;
}
