package me.smhc.modules.cts.repository;

import me.smhc.modules.cts.domain.Permit;
import me.smhc.modules.cts.domain.PermitMic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author 布和
 * @date 2020-04-16
 */
public interface PermitMicRepository extends JpaRepository<PermitMic, Long>, JpaSpecificationExecutor<PermitMic> {

}