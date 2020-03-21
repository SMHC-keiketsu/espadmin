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

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String customs;

    /** 精确 */
    @Query
    private BigDecimal cifValue;

    /** 精确 */
    @Query
    private BigDecimal weightAmount;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
}
