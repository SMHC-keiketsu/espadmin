package me.smhc.modules.cts.service;

import me.smhc.modules.cts.domain.Yunyu;
import me.smhc.modules.cts.service.dto.YunyuDto;
import me.smhc.modules.cts.service.dto.YunyuQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author jhf
* @date 2020-04-23
*/
public interface YunyuService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(YunyuQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<YunyuDto>
    */
    List<YunyuDto> queryAll(YunyuQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param yunyuCd ID
     * @return YunyuDto
     */
    YunyuDto findById(String yunyuCd);

    /**
    * 创建
    * @param resources /
    * @return YunyuDto
    */
    YunyuDto create(Yunyu resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Yunyu resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(String[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<YunyuDto> all, HttpServletResponse response) throws IOException;

    /**
     * 根据检索用电话查找輸入者
     * @param kensakuTel　電話
     * @return  輸入者
     */
    List<YunyuDto> findByKensakuTel(String kensakuTel);


    /**
     * 根据輸入者コードOR法人番号查找輸入者
     * @param yunyuCd   輸入者コード
     * @param houjinNo　法人番号
     * @return  輸入者
     */
    YunyuDto findByYunyuCdOrHoujinNo(String yunyuCd, String houjinNo);
}
