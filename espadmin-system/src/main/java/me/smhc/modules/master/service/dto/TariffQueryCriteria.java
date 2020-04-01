package me.smhc.modules.master.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;
import me.smhc.annotation.Query;

/**
* @author 布和
* @date 2020-03-17
*/
@Data
public class TariffQueryCriteria{

    /**
     * 查询税関名/CIF(円)/重量(KG)
     * 模糊查询
     */
    @Query(blurry = "customs,cifValue,weightAmount",type = Query.Type.RIGHT_LIKE)
    private String queryAll;

    /** 精确 */
    @Query
    private BigDecimal weightAmount;
    /**
     * 查询更新时间的区间
     */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
}
