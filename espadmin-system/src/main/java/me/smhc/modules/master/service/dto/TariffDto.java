package me.smhc.modules.master.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author 布和
* @date 2020-03-17
*/
@Data
public class TariffDto implements Serializable {

    /** id */
    private Long id;

    /** 関税名 */
    private String customs;

    /** JPY、USD、CNY、EUR,KRW */
    private String cifIso;

    /** CIF（円） */
    private BigDecimal cifValue;

    /** CIF条件 */
    private Integer cifLogic;

    /** 重量（KG） */
    private BigDecimal weightAmount;

    /** 重量单位 */
    private String weightUnit;

    /** 重量条件（KG） */
    private Integer weightLogic;

    private Timestamp createTime;

    /** 用户ID */
    private Long createUserId;

    /** 更新日時 */
    private Timestamp updateTime;

    /** 用户ID */
    private Long updateUserId;
}
