package me.smhc.modules.master.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author 布和
* @date 2020-04-08
*/
@Data
public class PatternConfigDto implements Serializable {

    private Long id;

    private Long deptId;

    /** 同一部门内，名字不可重复 */
    private String name;
    /** 共同配置 */
    private String idaMicComConfig;

    /** IDA配置 */
    private String idaConfig;

    /** MIC配置 */
    private String micConfig;

    /** HCH配置 */
    private String hchConfig;

    private Timestamp createTime;

    private Long createUserId;

    private Timestamp updateTime;

    private Long updateUserId;
}
