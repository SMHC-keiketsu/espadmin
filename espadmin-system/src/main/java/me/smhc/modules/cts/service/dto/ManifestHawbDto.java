package me.smhc.modules.cts.service.dto;

import lombok.Data;
import me.smhc.modules.cts.domain.ManifestMawb;
import me.smhc.modules.system.domain.Dept;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
* @author jhf
* @date 2020-03-24
*/
@Data
public class ManifestHawbDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8167577684265137045L;

    /** ID */
    private Long id;

    /** manifestMawb **/
    private Long manifestMawbId;

    /** HAWB番号 */
    private String hawbNo;

    /** 識別符号 */
    private String skb;

    /** 個数 */
    private Integer pcs;

    /** 重量 */
    private BigDecimal weight;

    /** 重量コード */
    private String weightCode;

    /** 品名 */
    private String productName;

    /** インボイス価格区分コード */
    private String invoiceClassificationCode;

    /** インボイス価格条件コード */
    private String invoiceConditionCode;

    /** INVOICE通貨 CURRENCY */
    private String invoiceIso;

    /** INVOICE金額 DECLARED VALUE OF CUSTOMS */
    private BigDecimal invoiceValue;

    /** 運賃区分コード */
    private String fareClassificationCode;

    /** 運賃通貨 */
    private String fareIso;

    /** 運賃金額 */
    private BigDecimal fareValue;

    /** 保険区分コード */
    private String insuranceClassificationCode;

    /** 保険通貨 */
    private String insuranceIso;

    /** 保険金額 */
    private BigDecimal insuranceValue;

    /** 輸入者コード　*/
    private String imc;

    /** 荷受人名 */
    private String importerName;

    /** 荷受人郵便番号 */
    private String importerPosterCode;

    /** 一括住所 */
    private String importerAddrAll;

    /** 住所１ */
    private String importerAddr1;

    /** 住所２ */
    private String importerAddr2;

    /** 住所３ */
    private String importerAddr3;

    /** 住所４ */
    private String importerAddr4;

    /** 荷受人TEL */
    private String importerTel;

    /** 荷送人コード */
    private String epc;

    /** 荷送人名 */
    private String shipperName;

    /** 荷送人郵便番号 */
    private String shipperPosterCode;

    /** 一括住所 */
    private String shipperAddrAll;

    /** 荷送人TEL */
    private String shipperTel;

    /** 原産国 */
    private String origin;

    /** 送り状No/問い合わせ番号 */
    private String trackingNo;

    /** 納品先郵便番号 */
    private String deliveryPosterCode;

    /** 一括住所 */
    private String deliveryAddrAll;

    /** 住所１ */
    private String deliveryAddr1;

    /** 住所２ */
    private String deliveryAddr2;

    /** 住所３ */
    private String deliveryAddr3;

    /** 住所４ */
    private String deliveryAddr4;

    /** 納品先名 */
    private String deliveryDestination;

    /** 納品先担当者/配送業者 */
    private String deliveryContact;

    /** 納品先TEL */
    private String deliveryTel;

    /** 請求方式 */
    private String billingMethod;

    /** 包装 */
    private String packaging;

    /** 着払い金額 */
    private BigDecimal cashOnDeliveryAmount;

    /** 課税価格 */
    private BigDecimal dpr;

    /** 記事 */
    private String nt1;

    /** 荷主セクションコード */
    private String nsc;

    /** 荷主リファレンスナンバー */
    private String nrn;

    /** 特殊貨物記号 */
    private String spc;

    /** 仕向地 */
    private String hchDst;

    /** 搬入保税蔵置場 */
    private String ihw;

    /** 社内整理用番号 */
    private String ref;

    /** ida/mic type **/
    private Integer idaMicType;

    /** ida/mic type status **/
    private Integer idaMicTypeStatus;

    /**hch type **/
    private Integer hchType;

    /** hch type status **/
    private Integer hchTypeStatus;

    /** 关键字包含 **/
    private  Integer inKeyword;

    /** 预留1 */
    private String reserve1;

    /** 预留2 */
    private String reserve2;

    /** 预留3 */
    private String reserve3;

    /** 预留4 */
    private String reserve4;

    /** 预留5 */
    private String reserve5;

    /** 预留6 */
    private String reserve6;

    /** 创建时间 */
    private Timestamp createTime;

    /** 创建用户ID 用户ID */
    private Long createUserId;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 更新用户ID 用户ID */
    private Long updateUserId;
}
