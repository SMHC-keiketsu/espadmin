package me.smhc.modules.cts.service;

import me.smhc.modules.cts.domain.ManifestHawb;
import me.smhc.modules.cts.service.dto.ManifestHawbDto;
import me.smhc.modules.cts.service.dto.ManifestHawbImporterDto;
import me.smhc.modules.cts.service.dto.ManifestHawbQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
* @author jhf
* @date 2020-03-24
*/
public interface ManifestHawbService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(ManifestHawbQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<ManifestDto>
    */
    List<ManifestHawbDto> queryAll(ManifestHawbQueryCriteria criteria);

    /**
     * 根据IDs查找Hawb
     * @param ids ID数组
     * @return
     */
    List<ManifestHawb> findByIds(List<Long> ids);

    /**
     * 根据ID查询
     * @param id ID
     * @return ManifestHawbDto
     */
    ManifestHawbDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return ManifestHawbDto
    */
    ManifestHawbDto create(ManifestHawb resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(ManifestHawb resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
     * 除重查找输入者
     * @param ids hawb数据
     * @return
     */
    List<ManifestHawbImporterDto> findDistinctImporter(List<Long> ids);
}
