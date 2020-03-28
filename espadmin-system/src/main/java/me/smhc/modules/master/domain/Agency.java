package me.smhc.modules.master.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author 布和
* @date 2020-03-24
*/
@Entity
@Data
@Table(name="agency")
public class Agency implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    /** 代理店名 */
    @Column(name = "company_name",nullable = false)
    @NotBlank
    private String companyName;

    /** 担当者名 */
    @Column(name = "contact",nullable = false)
    @NotBlank
    private String contact;

    /** メールアドレス */
    @Column(name = "email",nullable = false)
    @NotBlank
    private String email;

    /** 電話番号 */
    @Column(name = "tel",nullable = false)
    @NotBlank
    private String tel;

    /** 郵便番号 */
    @Column(name = "postal_code",nullable = false)
    @NotBlank
    private String postalCode;

    /** 住所 */
    @Column(name = "address_all",nullable = false)
    @NotBlank
    private String addressAll;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    @Column(name = "update_user_id")
    private Long updateUserId;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="excel_config_id",referencedColumnName = "id")
    private ExcelConfig excelConfig;

    public @interface Update {}

    public void copy(Agency source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
