package me.smhc.modules.master.service.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
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

    private String customsCode;

    private String governmentCode;

    private String government;

    private String customsSign;

    private String romeGoverment;

    /** JPY、USD、CNY、EUR,KRW */
    private String cifIso;

    /** CIF（円） */
    private BigDecimal cifValue;

    /** CIF条件 */
    private Boolean cifLogic;

    /** 重量（KG） */
    private BigDecimal weightAmount;

    /** 重量单位 */
    private String weightUnit;

    /** 重量条件（KG） */
    private Boolean weightLogic;

    private Timestamp createTime;

    /** 用户ID */
    private Long createUserId;

    /** 更新日時 */
    private Timestamp updateTime;

    /** 用户ID */
    private Long updateUserId;
}
