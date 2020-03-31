package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.Fare;
import me.smhc.modules.system.domain.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;

/**
* @author 布和
* @date 2020-03-20
*/
public interface FareRepository extends JpaRepository<Fare, Long>, JpaSpecificationExecutor<Fare> {

    /**
     * 用部门id，重量，价格查询
     * @param dept
     * @param iso
     * @param price
     * @return Fare 返回Fare类型的数据
     */
    Fare findByDeptAndIsoAndPrice(Dept dept, String iso,BigDecimal price);
}
