package me.smhc.modules.cts.service.dto;

import lombok.Data;
import me.smhc.annotation.Query;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
* @author jhf
* @date 2020-03-24
*/
@Data
public class ManifestMawbQueryCriteria {

    @Query(type = Query.Type.IN, propName="dept")
    private Set<Long> deptIds;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "dept",propName = "name")
    private String deptName;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String mawbNo;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String flightNo;

    /** FlightDate */
    @Query(type = Query.Type.INNER_LIKE)
    private Date flightDate;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String hawbNo;

    /** 個数 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private Integer pcs;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String productName;

    /** 重量 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private BigDecimal weight;

    /** 重量コード */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String weightCode;

    /** インボイス価格条件コード */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String invoiceConditionCode;

    /** INVOICE通貨 CURRENCY */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String invoiceIso;

    /** INVOICE金額 DECLARED VALUE OF CUSTOMS */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private BigDecimal invoiceValue;

    /** 運賃区分コード */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String fareClassificationCode;

    /** 運賃通貨 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String fareIso;

    /** 運賃金額 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private BigDecimal fareValue;

    /** 保険通貨 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String insuranceIso;

    /** 保険金額 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private BigDecimal insuranceValue;

    /** 荷受人名 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String importerName;

    /** 荷受人郵便番号 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String importerPosterCode;

    /** 一括住所 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String importerAddrAll;

    /** 住所１ */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String importerAddr1;

    /** 住所２ */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String importerAddr2;

    /** 住所３ */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String importerAddr3;

    /** 住所４ */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String importerAddr4;

    /** 荷受人TEL */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String importerTel;

    /** 荷送人名 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String shipperName;

    /** 荷送人郵便番号 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String shipperPosterCode;

    /** 一括住所 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String shipperAddrAll;

    /** 荷送人TEL */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String shipperTel;

    /** 原産国 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String origin;

    /** 送り状No/問い合わせ番号 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String trackingNo;

    /** 納品先郵便番号 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String deliveryPosterCode;

    /** 一括住所 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String deliveryAddrAll;

    /** 住所１ */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String deliveryAddr1;

    /** 住所２ */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String deliveryAddr2;

    /** 住所３ */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String deliveryAddr3;

    /** 住所４ */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String deliveryAddr4;

    /** 納品先名 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String deliveryDestination;

    /** 納品先担当者/配送業者 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String deliveryContact;

    /** 納品先TEL */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String deliveryTel;

    /** 請求方式 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String billingMethod;

    /** 包装 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private String packaging;

    /** 着払い金額 */
    @Query(type = Query.Type.INNER_LIKE,joinName = "manifest_hawb")
    private BigDecimal cashOnDeliveryAmount;

    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
}
