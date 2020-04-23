package me.smhc.modules.cts.repository;

import me.smhc.modules.cts.service.dto.ManifestHawbImporterDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import me.smhc.modules.cts.domain.ManifestHawb;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
* @author jhf
* @date 2020-03-24
*/
public interface ManifestHawbRepository extends JpaRepository<ManifestHawb, Long>, JpaSpecificationExecutor<ManifestHawb> {

    /**
     * 根据IDs查找hawbList
     * @param ids id集合
     * @return
     */
    List<ManifestHawb> findByIdIn(List<Long> ids);

    /**
     * 除重查找输入者
     * @param ids hawb数据
     * @return
     */
    @Modifying
    @Query(value = "SELECT DISTINCT new me.smhc.modules.cts.service.dto.ManifestHawbImporterDto(m.imc,m.importerName,m.importerPosterCode,m.importerAddrAll,m.importerAddr1,m.importerAddr2,m.importerAddr3,m.importerAddr4,m.importerTel) FROM ManifestHawb as m WHERE m.id in (?1)")
    List<ManifestHawbImporterDto> findDistinctImporter(List<Long> ids);
}
