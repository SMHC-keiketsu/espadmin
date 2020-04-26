package me.smhc.modules.cts.service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import me.smhc.annotation.Query;

/**
* @author 布和
* @date 2020-04-16
*/
@Data
public class PermitQueryCriteria {
    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String hawbNo;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String mawbNo;

    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
}
