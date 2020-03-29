package me.smhc.modules.master.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author 布和
* @date 2020-03-19
*/
@Data
public class ExchangeRateDto implements Serializable {

    /** ID */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** 开始日 */
    private Timestamp startDate;

    /** 用户ID */
    private Long updateUserId;

    /** レート */
    private BigDecimal rate;

    /** 创建时间 */
    private Timestamp createTime;

    /** 用户ID */
    private Long createUserId;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 终了日 */
    private Timestamp endDate;

    /** ISO */
    private String iso;
}
