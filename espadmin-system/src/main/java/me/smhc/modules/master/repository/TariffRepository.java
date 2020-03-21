package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.Tariff;
import me.smhc.modules.master.domain.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author 布和
* @date 2020-03-17
*/
public interface TariffRepository extends JpaRepository<Tariff, Long>, JpaSpecificationExecutor<Tariff> {
}
