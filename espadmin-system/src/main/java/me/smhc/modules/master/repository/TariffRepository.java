package me.smhc.modules.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import me.smhc.modules.master.domain.Tariff;

/**
* @author 布和
* @date 2020-03-17
*/
public interface TariffRepository extends JpaRepository<Tariff, Long>, JpaSpecificationExecutor<Tariff> {
    /**
     * 根据官署コードより税関
     * @param governmentCode　官署コード
     * @return  税関
     */
    Tariff findByGovernmentCode(String governmentCode);
}
