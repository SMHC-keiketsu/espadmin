package me.smhc.modules.cts.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author 布和
* @date 2020-04-16
*/
@Entity
@Data
@Table(name="permit_mic")
public class Permit implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    /** ((予約エリア)) */
    @Column(name = "ra1")
    private String ra1;

    /** (業務コード) */
    @Column(name = "bc")
    private String bc;

    /** (出力情報コード) */
    @Column(name = "cic")
    private String cic;

    /** (電文受信日時) */
    @Column(name = "md")
    private String md;

    /** (利用者コード) */
    @Column(name = "uc")
    private String uc;

    /** ((予約エリア)) */
    @Column(name = "ra2")
    private String ra2;

    /** (論理端末名) */
    @Column(name = "ltn")
    private String ltn;

    /** ((予約エリア)) */
    @Column(name = "ra3")
    private String ra3;

    /** (Subject) */
    @Column(name = "subject")
    private String subject;

    /** ((予約エリア)) */
    @Column(name = "ra4")
    private String ra4;

    /** (電文引継情報) */
    @Column(name = "mti")
    private String mti;

    /** (電文制御情報-分割通番) */
    @Column(name = "mci_dsn")
    private String mciDsn;

    /** (電文制御情報-最終表示) */
    @Column(name = "mci_fd")
    private String mciFd;

    /** (電文制御情報-電文種別) */
    @Column(name = "mci_mt")
    private String mciMt;

    /** (電文制御情報-(予約エリア)) */
    @Column(name = "mci_ra")
    private String mciRa;

    /** (入力情報特定番号) */
    @Column(name = "iicn")
    private String iicn;

    /** (索引引継情報) */
    @Column(name = "iti")
    private String iti;

    /** (宛管形式) */
    @Column(name = "af")
    private String af;

    /** ((予約エリア)) */
    @Column(name = "ra5")
    private String ra5;

    /** (電文長) */
    @Column(name = "tc")
    private String tc;

    /** (申告先種別コード) */
    @Column(name = "ic1")
    private String ic1;

    /** (識別符号) */
    @Column(name = "skb")
    private String skb;

    /** (審査検査区分識別) */
    @Column(name = "rii")
    private String rii;

    /** (あて先税関) */
    @Column(name = "chz")
    private String chz;

    /** (あて先部門コード) */
    @Column(name = "chb")
    private String chb;

    /** (申告年月日) */
    @Column(name = "ict")
    private String ict;

    /** (申告番号) */
    @Column(name = "icn")
    private String icn;

    /** (申告条件コード) */
    @Column(name = "jyo")
    private String jyo;

    /** (申告予定年月日) */
    @Column(name = "icd")
    private String icd;

    /** (本申告表示) */
    @Column(name = "ice")
    private String ice;

    /** (輸入者コード) */
    @Column(name = "imc")
    private String imc;

    /** (輸入者名) */
    @Column(name = "imn")
    private String imn;

    /** (郵便番号) */
    @Column(name = "imy")
    private String imy;

    /** (住所１（都道府県）) */
    @Column(name = "ima")
    private String ima;

    /** (住所２（市区町村) */
    @Column(name = "ima2")
    private String ima2;

    /** (住所３（町域名・番) */
    @Column(name = "ima3")
    private String ima3;

    /** (住所４（ビル名ほ) */
    @Column(name = "ima4")
    private String ima4;

    /** (輸入者住所) */
    @Column(name = "iad")
    private String iad;

    /** (輸入者電話番号) */
    @Column(name = "imt")
    private String imt;

    /** (税関事務管理人コー) */
    @Column(name = "zjy")
    private String zjy;

    /** (税関事務管理人受理) */
    @Column(name = "zjj")
    private String zjj;

    /** (税関事務管理人名) */
    @Column(name = "zjn")
    private String zjn;

    /** (仕出人コード) */
    @Column(name = "epc")
    private String epc;

    /** (仕出人名) */
    @Column(name = "epn")
    private String epn;

    /** (郵便番号（Postcode) */
    @Column(name = "epy")
    private String epy;

    /** (住所１（Street) */
    @Column(name = "epa")
    private String epa;

    /** (住所２（Street) */
    @Column(name = "ep2")
    private String ep2;

    /** (住所３（City) */
    @Column(name = "ep3")
    private String ep3;

    /** (住所４（Country) */
    @Column(name = "ep4")
    private String ep4;

    /** (国名コード) */
    @Column(name = "epo")
    private String epo;

    /** (仕出人住所) */
    @Column(name = "ead")
    private String ead;

    /** (代理人コード) */
    @Column(name = "agc")
    private String agc;

    /** (代理人名) */
    @Column(name = "agn")
    private String agn;

    /** (通関士コード) */
    @Column(name = "sn")
    private String sn;

    /** (検査立会者) */
    @Column(name = "ttc")
    private String ttc;

    /** (ＨＡＷＢ番号) */
    @Column(name = "hab")
    private String hab;

    /** (ＭＡＷＢ番号) */
    @Column(name = "mab")
    private String mab;

    /** (取卸港コード) */
    @Column(name = "dst")
    private String dst;

    /** (取卸港名) */
    @Column(name = "pn")
    private String pn;

    /** (積出地コード) */
    @Column(name = "psc")
    private String psc;

    /** (積出地名) */
    @Column(name = "psn")
    private String psn;

    /** (積載機名) */
    @Column(name = "vsn")
    private String vsn;

    /** (入港年月日) */
    @Column(name = "arr")
    private String arr;

    /** (蔵置税関) */
    @Column(name = "sct")
    private String sct;

    /** (蔵置税関部門) */
    @Column(name = "wcd")
    private String wcd;

    /** (貨物個数) */
    @Column(name = "no")
    private String no;

    /** (貨物重量（グロス）) */
    @Column(name = "gw")
    private String gw;

    /** (通関蔵置場コード) */
    @Column(name = "nocc")
    private String nocc;

    /** (通関蔵置場名) */
    @Column(name = "nocn")
    private String nocn;

    /** (通貨換算レート通貨コード) */
    @Column(name = "cerc")
    private String cerc;

    /** (通貨換算レート) */
    @Column(name = "ccr")
    private String ccr;

    /** (通貨換算レート通貨コード1) */
    @Column(name = "cerc1")
    private String cerc1;

    /** (通貨換算レート1) */
    @Column(name = "ccr1")
    private String ccr1;

    /** (通貨換算レート通貨コード2) */
    @Column(name = "cerc2")
    private String cerc2;

    /** (通貨換算レート2) */
    @Column(name = "ccr2")
    private String ccr2;

    /** (インボイス価格区分) */
    @Column(name = "ip1")
    private String ip1;

    /** (インボイス価格条件) */
    @Column(name = "ip2")
    private String ip2;

    /** (インボイス通貨コー) */
    @Column(name = "ip3")
    private String ip3;

    /** (インボイス価格) */
    @Column(name = "ip4")
    private String ip4;

    /** (運賃区分コード) */
    @Column(name = "fr1")
    private String fr1;

    /** (運賃通貨コード) */
    @Column(name = "fr2")
    private String fr2;

    /** (運賃) */
    @Column(name = "fr3")
    private String fr3;

    /** (保険区分コード) */
    @Column(name = "in1")
    private String in1;

    /** (保険通貨コード) */
    @Column(name = "in2")
    private String in2;

    /** (保険金額) */
    @Column(name = "in3")
    private String in3;

    /** (品名) */
    @Column(name = "cmn")
    private String cmn;

    /** (課税価格) */
    @Column(name = "dpr")
    private String dpr;

    /** (課税価格入力識別) */
    @Column(name = "tpi")
    private String tpi;

    /** (原産地コード) */
    @Column(name = "[or]")
    private String or;

    /** (原産地名) */
    @Column(name = "po")
    private String po;

    /** (記事) */
    @Column(name = "nt1")
    private String nt1;

    /** (輸入者（入力）) */
    @Column(name = "imi")
    private String imi;

    /** (社内整理用番号) */
    @Column(name = "ref")
    private String ref;

    /** (荷主セクションコー) */
    @Column(name = "nsc")
    private String nsc;

    /** (荷主リファレンスナ) */
    @Column(name = "nrn")
    private String nrn;

    /** (税関官署長名) */
    @Column(name = "noc")
    private String noc;

    /** (輸入許可年月日) */
    @Column(name = "iid")
    private String iid;

    /** (審査終了年月日) */
    @Column(name = "eor")
    private String eor;

    /** (事後審査識別) */
    @Column(name = "fui")
    private String fui;

    /** 電文ファイル名 */
    @Column(name = "file_name")
    private String fileName;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "update_user_id")
    private Long updateUserId;

    @Column(name = "create_user_id")
    private Long createUserId;

    public void copy(Permit source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
