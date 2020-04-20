package me.smhc.modules.master.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.smhc.modules.system.domain.Dept;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;


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
    @NotNull(groups = Update.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dept_id", referencedColumnName = "id")
    private Dept dept;



    /** 同一部门内，名字不可重复 */
    @Column(name = "[name]",nullable = false)
    @NotBlank
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

    /** 申告条件 */
    @Column(name = "jyo",nullable = false)
    private String jyo;

    /** 申告先種別コード */
    @Column(name = "ic1",nullable = false)
    private String ic1;

    /** 申告等種別コード */
    @Column(name = "icb",nullable = false)
    private String icb;

    /** 大額・少額識別 */
    @Column(name = "ls",nullable = false)
    private String ls;

    /** あて先官署コード */
    @Column(name = "ch",nullable = false)
    private String ch;

    /** あて先部門コード */
    @Column(name = "chb",nullable = false)
    private String chb;

    /** 申告予定年月日 */
    @Column(name = "icd",nullable = false)
    private String icd;

    /** 積載機名 */
    @Column(name = "vsn",nullable = false)
    private String vsn;

    /** 入港年月日 */
    @Column(name = "arr",nullable = false)
    private String arr;

    /** 取卸港コード */
    @Column(name = "dst",nullable = false)
    private String dst;

    /** 積出地コード */
    @Column(name = "psc",nullable = false)
    private String psc;

    /** 通関予定蔵置場コード */
    @Column(name = "st",nullable = false)
    private String st;

    /** 課税価格 */
    @Column(name = "dpr",nullable = false)
    private String dpr;

    /** 記事 */
    @Column(name = "nt1",nullable = false)
    private String nt1;

    /** 荷主セクションコード */
    @Column(name = "nsc",nullable = false)
    private String nsc;

    /** 荷主リファレンスナンバー */
    @Column(name = "nrn",nullable = false)
    private String nrn;

    /** 孫混載表示 */
    @Column(name = "mkh",nullable = false)
    private String mkh;

    /** 到着便名１ */
    @Column(name = "fl1",nullable = false)
    private String fl1;

    /** 到着便名２ */
    @Column(name = "fl2",nullable = false)
    private String fl2;

    /** 到着空港 */
    @Column(name = "pot",nullable = false)
    private String pot;

    /** 仕出地 */
    @Column(name = "org",nullable = false)
    private String org;

    /** ジョイント混載 */
    @Column(name = "jnt",nullable = false)
    private String jnt;

    /** 特殊貨物記号 */
    @Column(name = "spc",nullable = false)
    private String spc;

    /** 搬入保税蔵置場 */
    @Column(name = "ihw",nullable = false)
    private String ihw;

    /** 仕向地 */
    @Column(name = "hch_dst",nullable = false)
    private String hchDst;

    /** 税関官署 */
    @Column(name = "chc",nullable = false)
    private String chc;

    /** 委託元混載業 */
    @Column(name = "ibb",nullable = false)
    private String ibb;

    /** インボイス価格区分コード */
    @Column(name = "ip1",nullable = false)
    private String ip1;

    /** インボイス価格条件コード */
    @Column(name = "ip2",nullable = false)
    private String ip2;

    /** インボイス通貨コード */
    @Column(name = "ip3",nullable = false)
    private String ip3;

    /** インボイス価格 */
    @Column(name = "ip4",nullable = false)
    private String ip4;

    /** 運賃区分コード */
    @Column(name = "fr1",nullable = false)
    private String fr1;

    /** 運賃通貨コード */
    @Column(name = "fr2",nullable = false)
    private String fr2;

    /** 運賃 */
    @Column(name = "fr3",nullable = false)
    private String fr3;

    /** 保険区分コード */
    @Column(name = "in1",nullable = false)
    private String in1;

    /** 保険通貨コード */
    @Column(name = "in2",nullable = false)
    private String in2;

    /** 保険金額 */
    @Column(name = "in3",nullable = false)
    private String in3;

    /** 原産地コード */
    @Column(name = "[or]",nullable = false)
    private String or;

    /** 申告貨物識別 */
    @Column(name ="ic2",nullable = false)
    private String ic2;

    /** 識別符号 */
    @Column(name ="skb",nullable = false)
    private String skb;

    /** 貿易形態別符号 */
    @Column(name ="bok",nullable = false)
    private String bok;

    /** 納付方法識別 */
    @Column(name ="nof",nullable = false)
    private String nof;

    /** 口座番号 */
    @Column(name ="pf",nullable = false)
    private String pf;

    /** 品目コード */
    @Column(name ="cmd",nullable = false)
    private String cmd;

    /** ＮＡＣＣＳ用コード */
    @Column(name ="cm2",nullable = false)
    private String cm2;

    /** 原産地証明書識別 */
    @Column(name ="ors",nullable = false)
    private String ors;

    /** 数量単位コード（１） */
    @Column(name ="qt1",nullable = false)
    private String qt1;

    /** 内国消費税等種別コード */
    @Column(name ="tx_",nullable = false)
    private String tx_;

    /** インボイス識別 */
    @Column(name ="iv1",nullable = false)
    private String iv1;

    /** 評価区分コード */
    @Column(name ="vd1",nullable = false)
    private String vd1;

    public @interface Update {}

    public void copy(PatternConfig source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
