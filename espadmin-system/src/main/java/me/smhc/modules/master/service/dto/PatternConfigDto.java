package me.smhc.modules.master.service.dto;

import lombok.Data;
import me.smhc.modules.system.domain.Dept;

import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author 布和
* @date 2020-04-09
*/
@Data
public class PatternConfigDto implements Serializable {

    /** 搬入保税蔵置場 */
    private Long id;

    private Dept dept;

    /** 同一部门内，名字不可重复 */
    private String name;

    private Timestamp createTime;

    private Long createUserId;

    private Timestamp updateTime;

    private Long updateUserId;

    /** 申告条件 */
    private String jyo;

    /** 申告先種別コード */
    private String ic1;

    /** 申告等種別コード */
    private String icb;

    /** あて先官署コード */
    private String ch;

    /** あて先部門コード */
    private String chb;

    /** 申告予定年月日 */
    private String icd;

    /** 積載機名 */
    private String vsn;

    /** 入港年月日 */
    private String arr;

    /** 取卸港コード */
    private String dst;

    /** 積出地コード */
    private String psc;

    /** 通関予定蔵置場コード */
    private String st;

    /** 課税価格 */
    private String dpr;

    /** 記事 */
    private String nt2;

    /** 荷主セクションコード */
    private String nsc;

    /** 荷主リファレンスナンバー */
    private String nrn;

    /** 孫混載表示 */
    private String mkh;

    /** 到着便名１ */
    private String fl1;

    /** 到着便名２ */
    private String fl2;

    /** 到着空港 */
    private String pot;

    /** 仕出地 */
    private String org;

    /** ジョイント混載 */
    private String jnt;

    /** 特殊貨物記号 */
    private String spc;

    /** 搬入保税蔵置場 */
    private String ihw;

    /** 仕向地 */
    private String hchDst;

    /** 税関官署 */
    private String chc;

    /** 委託元混載業 */
    private String ibb;

    /** インボイス価格区分コード */
    private String ip1;

    /** インボイス価格条件コード */
    private String ip2;

    /** インボイス通貨コード */
    private String ip3;

    /** インボイス価格 */
    private String ip4;

    /** 運賃区分コード */
    private String fr1;

    /** 運賃通貨コード */
    private String fr2;

    /** 運賃 */
    private String fr3;

    /** 保険区分コード */
    private String in1;

    /** 保険通貨コード */
    private String in2;

    /** 保険金額 */
    private String in3;

    /** 原産地コード */
    private String or;

    /** 申告貨物識別 */
    private String ic2;

    /** 識別符号 */
    private String skb;

    /** 貿易形態別符号 */
    private String bok;

    /** 納付方法識別 */
    private String nof;

    /** 口座番号 */
    private String pf;

    /** 品目コード */
    private String cmd;

    /** ＮＡＣＣＳ用コード */
    private String cm2;

    /** 原産地証明書識別 */
    private String ors;

    /** 数量単位コード（１） */
    private String qt1;

    /** 内国消費税等種別コード */
    private String tx_;

    /** インボイス識別 */
    private String iv1;

    /** 評価区分コード */
    private String vd1;
}
