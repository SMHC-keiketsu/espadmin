package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author 布和
* @date 2020-03-19
*/
public interface KeywordRepository extends JpaRepository<Keyword, Long>, JpaSpecificationExecutor<Keyword> {
}