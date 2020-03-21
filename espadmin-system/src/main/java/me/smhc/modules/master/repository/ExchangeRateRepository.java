package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author 布和
* @date 2020-03-19
*/
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long>, JpaSpecificationExecutor<ExchangeRate> {
}