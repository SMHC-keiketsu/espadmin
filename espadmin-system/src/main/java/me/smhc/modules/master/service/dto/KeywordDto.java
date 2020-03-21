package me.smhc.modules.master.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author 布和
* @date 2020-03-19
*/
@Data
public class KeywordDto implements Serializable {

    /** id */
    private Long id;

    /** キーワード */
    private String productName;

    /** 创建时间 */
    private Timestamp createTime;

    /** 创建用户ID */
    private Long createUserId;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 更新用户ID */
    private Long updateUserId;
}