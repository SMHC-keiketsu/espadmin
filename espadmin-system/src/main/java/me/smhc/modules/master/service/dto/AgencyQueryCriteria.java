package me.smhc.modules.master.service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import me.smhc.annotation.Query;

/**
* @author
* @date 2020-03-24
*/
@Data
public class AgencyQueryCriteria{

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String contact;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String tel;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
}
