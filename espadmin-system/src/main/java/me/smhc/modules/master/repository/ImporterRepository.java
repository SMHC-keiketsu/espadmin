package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.Importer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author  
* @date 2020-03-25
*/
public interface ImporterRepository extends JpaRepository<Importer, Long>, JpaSpecificationExecutor<Importer> {
}