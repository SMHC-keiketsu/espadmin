package me.smhc.modules.system.service.dto;

import lombok.Data;
import me.smhc.annotation.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
* @author Zheng Jie
* @date 2019-03-25
*/
@Data
public class DeptQueryCriteria{

    @Query(type = Query.Type.IN, propName="id")
    private Set<Long> ids;

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    @Query(type = Query.Type.EQUAL)
    private Integer type;

    @Query
    private Boolean enabled;

    @Query
    private Long pid;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
