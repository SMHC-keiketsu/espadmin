package me.smhc.modules.master.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

/**
* @author 布和
* @date 2020-03-19
*/
@Entity
@Data
@Table(name="keyword")
public class Keyword implements Serializable {

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    @Column(name = "id")
    private Long id;

    /** キーワード */
    @Column(name = "product_name",nullable = false)
    @NotBlank
    private String productName;

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

    public @interface Update {}

    public void copy(Keyword source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
