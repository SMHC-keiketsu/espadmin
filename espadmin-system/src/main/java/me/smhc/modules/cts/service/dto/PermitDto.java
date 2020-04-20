package me.smhc.modules.cts.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
* @author 布和
* @date 2020-04-16
*/
@Data
public class PermitDto implements Serializable {

    /** 防止精度丢失 */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    /** ((予約エリア)) */
    private String ra1;

    /** (業務コード) */
    private String bc;

    /** (出力情報コード) */
    private String cic;

    /** (電文受信日時) */
    private String md;

    /** (利用者コード) */
    private String uc;

    /** ((予約エリア)) */
    private String ra2;

    /** (論理端末名) */
    private String ltn;

    /** ((予約エリア)) */
    private String ra3;

    /** (Subject) */
    private String subject;

    /** ((予約エリア)) */
    private String ra4;

    /** (電文引継情報) */
    private String mti;

    /** (電文制御情報-分割通番) */
    private String mciDsn;

    /** (電文制御情報-最終表示) */
    private String mciFd;

    /** (電文制御情報-電文種別) */
    private String mciMt;

    /** (電文制御情報-(予約エリア)) */
    private String mciRa;

    /** (入力情報特定番号) */
    private String iicn;

    /** (索引引継情報) */
    private String iti;

    /** (宛管形式) */
    private String af;

    /** ((予約エリア)) */
    private String ra5;

    /** (電文長) */
    private String tc;

    /** (申告先種別コード) */
    private String ic1;

    /** (識別符号) */
    private String skb;

    /** (審査検査区分識別) */
    private String rii;

    /** (あて先税関) */
    private String chz;

    /** (あて先部門コード) */
    private String chb;

    /** (申告年月日) */
    private String ict;

    /** (申告番号) */
    private String icn;

    /** (申告条件コード) */
    private String jyo;

    /** (申告予定年月日) */
    private String icd;

    /** (本申告表示) */
    private String ice;

    /** (輸入者コード) */
    private String imc;

    /** (輸入者名) */
    private String imn;

    /** (郵便番号) */
    private String imy;

    /** (住所１（都道府県）) */
    private String ima;

    /** (住所２（市区町村) */
    private String ima2;

    /** (住所３（町域名・番) */
    private String ima3;

    /** (住所４（ビル名ほ) */
    private String ima4;

    /** (輸入者住所) */
    private String iad;

    /** (輸入者電話番号) */
    private String imt;

    /** (税関事務管理人コー) */
    private String zjy;

    /** (税関事務管理人受理) */
    private String zjj;

    /** (税関事務管理人名) */
    private String zjn;

    /** (仕出人コード) */
    private String epc;

    /** (仕出人名) */
    private String epn;

    /** (郵便番号（Postcode) */
    private String epy;

    /** (住所１（Street) */
    private String epa;

    /** (住所２（Street) */
    private String ep2;

    /** (住所３（City) */
    private String ep3;

    /** (住所４（Country) */
    private String ep4;

    /** (国名コード) */
    private String epo;

    /** (仕出人住所) */
    private String ead;

    /** (代理人コード) */
    private String agc;

    /** (代理人名) */
    private String agn;

    /** (通関士コード) */
    private String sn;

    /** (検査立会者) */
    private String ttc;

    /** (ＨＡＷＢ番号) */
    private String hab;

    /** (ＭＡＷＢ番号) */
    private String mab;

    /** (取卸港コード) */
    private String dst;

    /** (取卸港名) */
    private String pn;

    /** (積出地コード) */
    private String psc;

    /** (積出地名) */
    private String psn;

    /** (積載機名) */
    private String vsn;

    /** (入港年月日) */
    private String arr;

    /** (蔵置税関) */
    private String sct;

    /** (蔵置税関部門) */
    private String wcd;

    /** (貨物個数) */
    private String no;

    /** (貨物重量（グロス）) */
    private String gw;

    /** (通関蔵置場コード) */
    private String nocc;

    /** (通関蔵置場名) */
    private String nocn;

    /** (通貨換算レート通貨コード) */
    private String cerc;

    /** (通貨換算レート) */
    private String ccr;

    /** (通貨換算レート通貨コード1) */
    private String cerc1;

    /** (通貨換算レート1) */
    private String ccr1;

    /** (通貨換算レート通貨コード2) */
    private String cerc2;

    /** (通貨換算レート2) */
    private String ccr2;

    /** (インボイス価格区分) */
    private String ip1;

    /** (インボイス価格条件) */
    private String ip2;

    /** (インボイス通貨コー) */
    private String ip3;

    /** (インボイス価格) */
    private String ip4;

    /** (運賃区分コード) */
    private String fr1;

    /** (運賃通貨コード) */
    private String fr2;

    /** (運賃) */
    private String fr3;

    /** (保険区分コード) */
    private String in1;

    /** (保険通貨コード) */
    private String in2;

    /** (保険金額) */
    private String in3;

    /** (品名) */
    private String cmn;

    /** (課税価格) */
    private String dpr;

    /** (課税価格入力識別) */
    private String tpi;

    /** (原産地コード) */
    private String or;

    /** (原産地名) */
    private String po;

    /** (記事) */
    private String nt1;

    /** (輸入者（入力）) */
    private String imi;

    /** (社内整理用番号) */
    private String ref;

    /** (荷主セクションコー) */
    private String nsc;

    /** (荷主リファレンスナ) */
    private String nrn;

    /** (税関官署長名) */
    private String noc;

    /** (輸入許可年月日) */
    private String iid;

    /** (審査終了年月日) */
    private String eor;

    /** (事後審査識別) */
    private String fui;

    /** 電文ファイル名 */
    private String fileName;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Long updateUserId;

    private Long createUserId;
}