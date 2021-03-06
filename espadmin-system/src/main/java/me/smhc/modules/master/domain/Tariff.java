package me.smhc.modules.master.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

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
    @Size(max = 100)
    private String customs;

    @Column(name = "customs_code",nullable = false)
    @NotBlank
    @Size(max = 6)
    private String customsCode;

    @Column(name = "government_code",nullable = false)
    @NotBlank
    @Size(max = 6)
    private String governmentCode;

    @Column(name = "government",nullable = false)
    @NotBlank
    @Size(max = 100)
    private String government;

    @Column(name = "customs_sign",nullable = false)
    @NotBlank
    @Size(max = 6)
    private String customsSign;

    @Column(name = "rome_goverment",nullable = false)
    @NotBlank
    @Size(max = 100)
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
    @Size(max = 3)
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
