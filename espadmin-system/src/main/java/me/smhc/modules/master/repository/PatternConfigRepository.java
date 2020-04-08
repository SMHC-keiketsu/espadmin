package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.PatternConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author 布和
* @date 2020-04-08
*/
public interface PatternConfigRepository extends JpaRepository<PatternConfig, Long>, JpaSpecificationExecutor<PatternConfig> {
}