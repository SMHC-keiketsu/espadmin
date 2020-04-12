package me.smhc.modules.cts.repository;

import me.smhc.modules.cts.domain.ManifestMawb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author jhf
* @date 2020-03-24
*/
public interface ManifestMawbRepository extends JpaRepository<ManifestMawb, Long>, JpaSpecificationExecutor<ManifestMawb> {

    /**
     * 根据Mawbno查找
     * @param mawbNo 主单号
     * @return
     */
     ManifestMawb findByMawbNo(String mawbNo);
}
