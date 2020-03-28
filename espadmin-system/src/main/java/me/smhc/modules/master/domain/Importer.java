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
* @author
* @date 2020-03-25
*/
@Entity
@Data
@Table(name="importer")
public class Importer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    @Column(name = "id")
    private Long id;

    /** 輸入者符号 */
    @Column(name = "jastpro_code",nullable = false)
    @NotBlank
    private String jastproCode;

    /** 法人番号 */
    @Column(name = "corporate_number",nullable = false)
    @NotBlank
    private String corporateNumber;

    /** 電話番号 */
    @Column(name = "tel",nullable = false)
    @NotBlank
    private String tel;

    /** 郵便番号 */
    @Column(name = "postal_code",nullable = false)
    @NotBlank
    private String postalCode;

    /** 輸入者名（英文） */
    @Column(name = "en_company_name",nullable = false)
    @NotBlank
    private String enCompanyName;

    /** 住所（英文） */
    @Column(name = "en_address_all",nullable = false)
    @NotBlank
    private String enAddressAll;

    /** 住所_都道府県（英文） */
    @Column(name = "en_address_1",nullable = false)
    @NotBlank
    private String enAddress1;

    /** 住所_市区町村（英文） */
    @Column(name = "en_address_2",nullable = false)
    @NotBlank
    private String enAddress2;

    /** 住所_番地（英文） */
    @Column(name = "en_address_3",nullable = false)
    @NotBlank
    private String enAddress3;

    /** 住所_建物（英文） */
    @Column(name = "en_address_4",nullable = false)
    @NotBlank
    private String enAddress4;

    /** 輸入者名（和文） */
    @Column(name = "ja_company_name",nullable = false)
    @NotBlank
    private String jaCompanyName;

    /** 住所（和文） */
    @Column(name = "ja_address_all",nullable = false)
    @NotBlank
    private String jaAddressAll;

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

    public @interface Update {}

    public void copy(Importer source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
