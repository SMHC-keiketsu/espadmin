package me.smhc.modules.master.service;

import me.smhc.modules.master.domain.Keyword;
import me.smhc.modules.master.service.dto.KeywordDto;
import me.smhc.modules.master.service.dto.KeywordQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author 布和
* @date 2020-03-19
*/
public interface KeywordService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(KeywordQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<KeywordDto>
    */
    List<KeywordDto> queryAll(KeywordQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return KeywordDto
     */
    KeywordDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return KeywordDto
    */
    KeywordDto create(Keyword resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Keyword resources);

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
    void download(List<KeywordDto> all, HttpServletResponse response) throws IOException;

    /**
     * 全部のキーワード取得
     * @return  キーワードリスト
     */
    List<String> findAllPorductName();
}
