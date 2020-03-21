package me.smhc.modules.master.service.dto;

import lombok.Data;
import me.smhc.modules.system.domain.Dept;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author 布和
* @date 2020-03-20
*/
@Data
public class FareDto implements Serializable {

    /** ID */
    private Long id;

    /** 部门名称 */
    private Long deptId;

   private Dept dept;

    /** 重量 */
    private BigDecimal weight;

    /** 価格 */
    private BigDecimal price;

    /** ISO */
    private String iso;

    /** 创建时间 */
    private Timestamp createTime;

    /** 创建用户ID */
    private Long createUserId;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 更新用户ID */
    private Long updateUserId;

}
