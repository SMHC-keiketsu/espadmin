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
* @date 2020-03-19
*/
@Entity
@Data
@Table(name="exchange_rate")
public class ExchangeRate implements Serializable {

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    @Column(name = "id")
    private Long id;

    /** 开始日 */
    @Column(name = "start_date",nullable = false)
    @NotNull
    private Timestamp startDate;

    /** 用户ID */
    @Column(name = "update_user_id")
    private Long updateUserId;

    /** レート */
    @Column(name = "rate",nullable = false)
    @NotNull
    private BigDecimal rate;

    /** 创建时间 */
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    /** 用户ID */
    @Column(name = "create_user_id")
    private Long createUserId;

    /** 更新时间 */
    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    /** 终了日 */
    @Column(name = "end_date",nullable = false)
    @NotNull
    private Timestamp endDate;

    /** ISO */
    @Column(name = "iso",nullable = false)
    @NotBlank
    private String iso;

    public @interface Update {}

    public void copy(ExchangeRate source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
