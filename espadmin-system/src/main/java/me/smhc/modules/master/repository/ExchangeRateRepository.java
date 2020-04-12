package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;

/**
* @author 布和
* @date 2020-03-19
*/
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long>, JpaSpecificationExecutor<ExchangeRate> {


    /**
     * 根据ISO和有效区间查找
     * @param iso 通貨
     * @param today　当日
     * @return rate
     */
    @Query(value="SELECT rate FROM exchange_rate WHERE iso = ?1 AND start_date <= ?2 AND ?2 <= end_date",nativeQuery = true)
    BigDecimal findRateByIsoAndToday(String iso, String today);

}
