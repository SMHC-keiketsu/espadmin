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
* @date 2020-03-20
*/
@Entity
@Data
@Table(name="fare")
public class Fare implements Serializable {

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 部门名称 */
    @Column(name = "dept_id",nullable = false)
    @NotNull
    private Long deptId;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "dept_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Dept dept;

    /** 重量 */
    @Column(name = "weight",nullable = false)
    @NotNull
    private BigDecimal weight;

    /** 価格 */
    @Column(name = "price",nullable = false)
    @NotNull
    private BigDecimal price;

    /** ISO */
    @Column(name = "currency",nullable = false)
    @NotBlank
    private String currency;

    /** 创建时间 */
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    /** 创建用户ID */
    @Column(name = "create_user_id")
    private Long createUserId;

    /** 更新时间 */
    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    /** 更新用户ID */
    @Column(name = "update_user_id")
    private Long updateUserId;

    public void copy(Fare source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
