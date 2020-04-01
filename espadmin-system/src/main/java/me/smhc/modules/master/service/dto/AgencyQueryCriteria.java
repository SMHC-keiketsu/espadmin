package me.smhc.modules.master.service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import me.smhc.annotation.Query;

/**
* @author 布和
* @date 2020-03-24
*/
@Data
public class AgencyQueryCriteria{

    /**
     * or然后再模糊查询
     * 查询电话号或担当着名
     */
    @Query(blurry = "tel,contact",type = Query.Type.INNER_LIKE)
    private String queryAll;

    /**
     * 查询更新时间的区间
     */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
}
