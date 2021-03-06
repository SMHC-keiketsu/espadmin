package me.smhc.modules.system.service.dto;

import lombok.Data;
import me.smhc.annotation.Query;

/**
 * @author Zheng Jie
 * 公共查询类
 */
@Data
public class DictQueryCriteria {

    @Query(blurry = "name,remark")
    private String blurry;
}
