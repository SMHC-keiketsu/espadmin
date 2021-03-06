package me.smhc.modules.master.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;
import me.smhc.annotation.Query;

/**
* @author 布和
* @date 2020-03-19
*/
@Data
public class KeywordQueryCriteria{

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String productName;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
}
