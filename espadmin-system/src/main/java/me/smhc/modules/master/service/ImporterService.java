package me.smhc.modules.master.service;

import me.smhc.modules.cts.domain.ManifestHawb;
import me.smhc.modules.cts.service.dto.ManifestHawbImporterDto;
import me.smhc.modules.master.domain.Importer;
import me.smhc.modules.master.service.dto.ImporterDto;
import me.smhc.modules.master.service.dto.ImporterQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author 布和
* @date 2020-03-25
*/
public interface ImporterService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(ImporterQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<ImporterDto>
    */
    List<ImporterDto> queryAll(ImporterQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return ImporterDto
     */
    ImporterDto findById(Long id);


    /**
     * 根据手机号码查询
     * @param tel 电话
     * @return 輸入者
     */
    List<ImporterDto> findByTel(String tel);

    /**
    * 创建
    * @param resources /
    * @return ImporterDto
    */
    ImporterDto create(Importer resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Importer resources);

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
    void download(List<ImporterDto> all, HttpServletResponse response) throws IOException;

    /**
     * 保存manifest的输入者
     * @param manifestHawbImporterDtos manifestHawbImporterDtos
     */
     void saveImporter(List<ManifestHawbImporterDto> manifestHawbImporterDtos);
}
