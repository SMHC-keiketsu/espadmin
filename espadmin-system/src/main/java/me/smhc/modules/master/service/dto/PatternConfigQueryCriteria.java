package me.smhc.modules.master.service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import me.smhc.annotation.Query;
import me.smhc.modules.system.domain.Dept;

import javax.persistence.Column;

/**
* @author 布和
* @date 2020-04-08
*/
@Data
public class PatternConfigQueryCriteria{

    @Query(blurry = "name")
    private String queryAll;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;

    @Query(propName = "id",joinName = "dept")
    private Long deptId;
}
