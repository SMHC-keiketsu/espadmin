package me.smhc.modules.cts.repository;

import me.smhc.modules.cts.domain.Permit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 布和
 * @date 2020-04-16
 */
public interface PermitRepository extends JpaRepository<Permit, Long>, JpaSpecificationExecutor<Permit> {
    /**
     * 查询文件名称
     * @param fileName
     * @return PermitMic
     */
    Permit findByFileName(String fileName);
}