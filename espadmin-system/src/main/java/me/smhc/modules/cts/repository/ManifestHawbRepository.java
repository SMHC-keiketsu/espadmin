package me.smhc.modules.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import me.smhc.modules.cts.domain.ManifestHawb;

/**
* @author jhf
* @date 2020-03-24
*/
public interface ManifestHawbRepository extends JpaRepository<ManifestHawb, Long>, JpaSpecificationExecutor<ManifestHawb> {

}