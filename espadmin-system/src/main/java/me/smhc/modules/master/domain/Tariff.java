package me.smhc.modules.master.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import me.smhc.modules.system.domain.Dept;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author 布和
* @date 2020-03-17
*/
@Entity
@Data
@Table(name="tariff")
public class Tariff implements Serializable {

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    @Column(name = "id")
    private Long id;

    /** 関税名 */
    @Column(name = "customs",nullable = false)
    @NotBlank
    private String customs;

    @Column(name = "customs_code",nullable = false)
    @NotBlank
    private String customsCode;

    @Column(name = "government_code",nullable = false)
    @NotBlank
    private String governmentCode;

    @Column(name = "government",nullable = false)
    @NotBlank
    private String government;

    @Column(name = "customs_sign",nullable = false)
    @NotBlank
    private String customsSign;

    @Column(name = "rome_goverment",nullable = false)
    @NotBlank
    private String romeGoverment;

    /** JPY、USD、CNY、EUR,KRW */
    @Column(name = "cif_iso")
    private String cifIso;

    /** CIF（円） */
    @Column(name = "cif_value",nullable = false)
    @NotNull
    private BigDecimal cifValue;

    /** CIF条件 */
    @Column(name = "cif_logic",nullable = false)
    @NotNull
    private Boolean cifLogic;

    /** 重量（KG） */
    @Column(name = "weight_amount",nullable = false)
    @NotNull
    private BigDecimal weightAmount;

    /** 重量单位 */
    @Column(name = "weight_unit")
    private String weightUnit;

    /** 重量条件（KG） */
    @Column(name = "weight_logic",nullable = false)
    @NotNull
    private Boolean weightLogic;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    /** 用户ID */
    @Column(name = "create_user_id")
    private Long createUserId;

    /** 更新日時 */
    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    /** 用户ID */
    @Column(name = "update_user_id")
    private Long updateUserId;

    public @interface Update {}

    public void copy(Tariff source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
