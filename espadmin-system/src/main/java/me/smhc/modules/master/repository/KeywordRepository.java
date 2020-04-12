package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author 布和
* @date 2020-03-19
*/
public interface KeywordRepository extends JpaRepository<Keyword, Long>, JpaSpecificationExecutor<Keyword> {

    /**
     * 全部のキーワード取得
     * @return  キーワードリスト
     */
    @Query(value="SELECT product_name FROM keyword",nativeQuery = true)
    List<String> findAllPorductName();
}
