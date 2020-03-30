package me.smhc.modules.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import me.smhc.modules.cts.domain.Manifest;

/**
* @author jhf
* @date 2020-03-24
*/
public interface ManifestRepository extends JpaRepository<Manifest, Long>, JpaSpecificationExecutor<Manifest> {

    /**
     * 根据mawbNo和hawbNo查询Manifest
     * @param mawbNo
     * @param hawbNo
     * @return
     */
    Manifest findByMawbNoAndHawbNo(String mawbNo,String hawbNo);

}
