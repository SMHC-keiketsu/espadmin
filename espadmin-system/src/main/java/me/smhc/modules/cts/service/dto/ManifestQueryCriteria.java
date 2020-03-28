package me.smhc.modules.cts.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import me.smhc.annotation.Query;

/**
* @author jhf
* @date 2020-03-24
*/
@Data
public class ManifestQueryCriteria{

    @Query(type = Query.Type.IN, propName="dept")
    private Set<Long> deptIds;

    /** 精确 */
    @Query(type = Query.Type.EQUAL,propName = "dept")
    private Long deptId;

    /** 精确 */
    @Query
    private String mawbNo;

    /** 精确 */
    @Query
    private String flightNo;

    /** 精确 */
    @Query
    private String hawbNo;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String productName;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
}
