package me.smhc.modules.master.service.dto;

import lombok.Data;
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

    private Long deptId;

    /** 同一部门内，名字不可重复 */
    private String name;

    private Timestamp createTime;

    private Long createUserId;

    private Timestamp updateTime;

    private Long updateUserId;

    /** ＭＡＷＢ番号 */
    private String mawbNo;

    /** 申告条件 */
    private String jyo;

    /** 申告先種別コード */
    private String ic1;

    /** 申告等種別コード */
    private String icb;

    /** 大額・少額識別 */
    private String ls;

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
    private String nt1;

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
}
