package me.smhc.modules.master.service.dto;

import lombok.Data;
import me.smhc.annotation.Query;

/**
* @author 布和
* @date 2020-03-20
*/
@Data
public class FareQueryCriteria{

    @Query
    private Long deptId;
}
