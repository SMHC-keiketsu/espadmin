package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.ExcelConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author  
* @date 2020-03-26
*/
public interface ExcelConfigRepository extends JpaRepository<ExcelConfig, Long>, JpaSpecificationExecutor<ExcelConfig> {
}