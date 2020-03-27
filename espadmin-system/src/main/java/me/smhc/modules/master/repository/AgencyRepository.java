package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author  
* @date 2020-03-24
*/
public interface AgencyRepository extends JpaRepository<Agency, Long>, JpaSpecificationExecutor<Agency> {
}