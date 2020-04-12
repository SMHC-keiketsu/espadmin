package me.smhc.modules.cts.service.dto;

import lombok.Data;
import me.smhc.modules.cts.domain.ManifestHawb;
import me.smhc.modules.system.domain.Dept;

import javax.persistence.Column;
import javax.persistence.Version;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
* @author jhf
* @date 2020-03-24
*/
@Data
public class ManifestMawbDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8167577684265137045L;

    /** ID */
    private Long id;

    /** 代理商名 */
    private Dept dept;

    /** MAWB番号 */
    private String mawbNo;

    /** FlightNO */
    private String flightNo;

    /** FlightDate */
    private Date flightDate;

    /** manifestHawb  list **/
    List<ManifestHawb> manifestHawbList;

    /** mawb类型 **/
    private String type;

    /** 状态 **/
    private String status;

    /** IDA amount **/
    private Long idaAmount;

    /** ida left amount **/
    private Long idaLeftAmount;

    /** mic amount **/
    private Long micAmount;
    /** mic left amount **/
    private Long micLeftAmount;

    /** hch amount **/
    private Long hchAmount;

    /** hch left amount **/
    private Long hchLeftAmount;

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

    /** 申告等予定年月日 */
    private String icd;

    /** 積載船（機）名 */
    private String vsn;

    /** 入港年月日 */
    private String arr;

    /** 船（取）卸港コード */
    private String dst;

    /** 積出地コード */
    private String psc;

    /** 通関予定蔵置場コード */
    private String st;

    /** 孫混載表示 */
    private String mkh;

    /** 税関官署 */
    private String chc;

    /** 委託元混載業 */
    private String ibb;

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

    /** 仕向地 */
    private String hchDst;

    /** 搬入保税蔵置場 */
    private String ihw;

    /** 数据版本号 乐观锁*/
    private Long reserve1;

    /** 创建时间 */
    private Timestamp createTime;

    /** 创建用户ID 用户ID */
    private Long createUserId;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 更新用户ID 用户ID */
    private Long updateUserId;
}
