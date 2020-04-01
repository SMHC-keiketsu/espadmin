package me.smhc.modules.master.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.util.List;
import me.smhc.annotation.Query;

/**
* @author 布和
* @date 2020-03-19
*/
@Data
public class ExchangeRateQueryCriteria{

    /**
     * 查询货币类型，汇率
     * or模糊查询
     */
    @Query(blurry = "rate,iso",type = Query.Type.INNER_LIKE)
    private String queryAll;

    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
}
