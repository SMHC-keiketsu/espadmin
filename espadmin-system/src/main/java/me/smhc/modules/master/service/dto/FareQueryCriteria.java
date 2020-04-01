package me.smhc.modules.master.service.dto;

import lombok.Data;
import me.smhc.annotation.Query;
import me.smhc.modules.system.domain.Dept;

import java.sql.Timestamp;
import java.util.List;

/**
* @author 布和
* @date 2020-03-20
*/
@Data
public class FareQueryCriteria{

    @Query
    private Dept dept;

    /**
     * or 模糊查询
     * 查询重量，价格，货币
     */
    @Query(blurry = "weight,price,iso",type = Query.Type.INNER_LIKE)
    private String queryAll;
    /**
     * 查询更新时间的区间
     */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
}
