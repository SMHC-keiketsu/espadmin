package me.smhc.modules.master.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author 布和
* @date 2020-04-09
*/
@Entity
@Data
@Table(name="pattern_config")
public class PatternConfig implements Serializable {

    /** 搬入保税蔵置場 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = Update.class)
    @Column(name = "id")
    private Long id;

    @Column(name = "dept_id")
    private Long deptId;

    /** 同一部门内，名字不可重复 */
    @Column(name = "name")
    private String name;

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

    /** ＭＡＷＢ番号 */
    @Column(name = "mawb_no",nullable = false)
    @NotBlank
    private String mawbNo;

    /** 申告条件 */
    @Column(name = "jyo",nullable = false)
    @NotBlank
    private String jyo;

    /** 申告先種別コード */
    @Column(name = "ic1",nullable = false)
    @NotBlank
    private String ic1;

    /** 申告等種別コード */
    @Column(name = "icb",nullable = false)
    @NotBlank
    private String icb;

    /** 大額・少額識別 */
    @Column(name = "ls",nullable = false)
    @NotBlank
    private String ls;

    /** あて先官署コード */
    @Column(name = "ch",nullable = false)
    @NotBlank
    private String ch;

    /** あて先部門コード */
    @Column(name = "chb",nullable = false)
    @NotBlank
    private String chb;

    /** 申告予定年月日 */
    @Column(name = "icd",nullable = false)
    @NotBlank
    private String icd;

    /** 積載機名 */
    @Column(name = "vsn",nullable = false)
    @NotBlank
    private String vsn;

    /** 入港年月日 */
    @Column(name = "arr",nullable = false)
    @NotBlank
    private String arr;

    /** 取卸港コード */
    @Column(name = "dst",nullable = false)
    @NotBlank
    private String dst;

    /** 積出地コード */
    @Column(name = "psc",nullable = false)
    @NotBlank
    private String psc;

    /** 通関予定蔵置場コード */
    @Column(name = "st",nullable = false)
    @NotBlank
    private String st;

    /** 課税価格 */
    @Column(name = "dpr",nullable = false)
    @NotBlank
    private String dpr;

    /** 記事 */
    @Column(name = "nt1",nullable = false)
    @NotBlank
    private String nt1;

    /** 荷主セクションコード */
    @Column(name = "nsc",nullable = false)
    @NotBlank
    private String nsc;

    /** 荷主リファレンスナンバー */
    @Column(name = "nrn",nullable = false)
    @NotBlank
    private String nrn;

    /** 孫混載表示 */
    @Column(name = "mkh",nullable = false)
    @NotBlank
    private String mkh;

    /** 到着便名１ */
    @Column(name = "fl1",nullable = false)
    @NotBlank
    private String fl1;

    /** 到着便名２ */
    @Column(name = "fl2",nullable = false)
    @NotBlank
    private String fl2;

    /** 到着空港 */
    @Column(name = "pot",nullable = false)
    @NotBlank
    private String pot;

    /** 仕出地 */
    @Column(name = "org",nullable = false)
    @NotBlank
    private String org;

    /** ジョイント混載 */
    @Column(name = "jnt",nullable = false)
    @NotBlank
    private String jnt;

    /** 特殊貨物記号 */
    @Column(name = "spc",nullable = false)
    @NotBlank
    private String spc;

    /** 搬入保税蔵置場 */
    @Column(name = "ihw",nullable = false)
    @NotBlank
    private String ihw;

    /** 仕向地 */
    @Column(name = "hch_dst",nullable = false)
    @NotBlank
    private String hchDst;

    public @interface Update {}

    public void copy(PatternConfig source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
