package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.Fare;
import me.smhc.modules.system.domain.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

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


    /**
     * 運賃マスタ算出
     * @param deptId　部門Id
     * @param iso　通貨
     * @param weight　重量
     * @return 運賃
     */
    @Query(value="SELECT price FROM fare WHERE dept_id = ?1 AND iso = ?2 AND weight >= ?3 ORDER BY Weight ASC ",nativeQuery = true)
    List<BigDecimal>  findPriceByDeptAndIsoAndWeight(Long deptId, String iso, BigDecimal weight);

    /**
     * 最高価格運賃取得
     * @param deptId　部門Id
     * @param iso　通貨
     * @return 運賃
     */
    @Query(value="SELECT price FROM fare WHERE dept_id = ?1 AND iso = ?2 ORDER BY Weight DESC ",nativeQuery = true)
    List<BigDecimal>  findHeightestPrice(Long deptId,String iso);
}
