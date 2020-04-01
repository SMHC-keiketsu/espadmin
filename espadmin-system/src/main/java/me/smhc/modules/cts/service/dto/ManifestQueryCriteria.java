package me.smhc.modules.cts.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

import me.smhc.annotation.Query;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* @author jhf
* @date 2020-03-24
*/
@Data
public class ManifestQueryCriteria{

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
    @Query(type = Query.Type.INNER_LIKE)
    private String hawbNo;

    /** 個数 */
    @Query(type = Query.Type.INNER_LIKE)
    private Integer pcs;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String productName;

    /** 重量 */
    @Query(type = Query.Type.INNER_LIKE)
    private BigDecimal weight;

    /** 重量コード */
    @Query(type = Query.Type.INNER_LIKE)
    private String weightCode;

    /** インボイス価格条件コード */
    @Query(type = Query.Type.INNER_LIKE)
    private String invoiceConditionCode;

    /** INVOICE通貨 CURRENCY */
    @Query(type = Query.Type.INNER_LIKE)
    private String invoiceIso;

    /** INVOICE金額 DECLARED VALUE OF CUSTOMS */
    @Query(type = Query.Type.INNER_LIKE)
    private BigDecimal invoiceValue;

    /** 運賃区分コード */
    @Query(type = Query.Type.INNER_LIKE)
    private String fareClassificationCode;

    /** 運賃通貨 */
    @Query(type = Query.Type.INNER_LIKE)
    private String fareIso;

    /** 運賃金額 */
    @Query(type = Query.Type.INNER_LIKE)
    private BigDecimal fareValue;

    /** 保険通貨 */
    @Query(type = Query.Type.INNER_LIKE)
    private String insuranceIso;

    /** 保険金額 */
    @Query(type = Query.Type.INNER_LIKE)
    private BigDecimal insuranceValue;

    /** 荷受人名 */
    @Query(type = Query.Type.INNER_LIKE)
    private String importerName;

    /** 荷受人郵便番号 */
    @Query(type = Query.Type.INNER_LIKE)
    private String importerPosterCode;

    /** 一括住所 */
    @Query(type = Query.Type.INNER_LIKE)
    private String importerAddrAll;

    /** 住所１ */
    @Query(type = Query.Type.INNER_LIKE)
    private String importerAddr1;

    /** 住所２ */
    @Query(type = Query.Type.INNER_LIKE)
    private String importerAddr2;

    /** 住所３ */
    @Query(type = Query.Type.INNER_LIKE)
    private String importerAddr3;

    /** 住所４ */
    @Query(type = Query.Type.INNER_LIKE)
    private String importerAddr4;

    /** 荷受人TEL */
    @Query(type = Query.Type.INNER_LIKE)
    private String importerTel;

    /** 荷送人名 */
    @Query(type = Query.Type.INNER_LIKE)
    private String shipperName;

    /** 荷送人郵便番号 */
    @Query(type = Query.Type.INNER_LIKE)
    private String shipperPosterCode;

    /** 一括住所 */
    @Query(type = Query.Type.INNER_LIKE)
    private String shipperAddrAll;

    /** 荷送人TEL */
    @Query(type = Query.Type.INNER_LIKE)
    private String shipperTel;

    /** 原産国 */
    @Query(type = Query.Type.INNER_LIKE)
    private String origin;

    /** 送り状No/問い合わせ番号 */
    @Query(type = Query.Type.INNER_LIKE)
    private String trackingNo;

    /** 納品先郵便番号 */
    @Query(type = Query.Type.INNER_LIKE)
    private String deliveryPosterCode;

    /** 一括住所 */
    @Query(type = Query.Type.INNER_LIKE)
    private String deliveryAddrAll;

    /** 住所１ */
    @Query(type = Query.Type.INNER_LIKE)
    private String deliveryAddr1;

    /** 住所２ */
    @Query(type = Query.Type.INNER_LIKE)
    private String deliveryAddr2;

    /** 住所３ */
    @Query(type = Query.Type.INNER_LIKE)
    private String deliveryAddr3;

    /** 住所４ */
    @Query(type = Query.Type.INNER_LIKE)
    private String deliveryAddr4;

    /** 納品先名 */
    @Query(type = Query.Type.INNER_LIKE)
    private String deliveryDestination;

    /** 納品先担当者/配送業者 */
    @Query(type = Query.Type.INNER_LIKE)
    private String deliveryContact;

    /** 納品先TEL */
    @Query(type = Query.Type.INNER_LIKE)
    private String deliveryTel;

    /** 請求方式 */
    @Query(type = Query.Type.INNER_LIKE)
    private String billingMethod;

    /** 包装 */
    @Query(type = Query.Type.INNER_LIKE)
    private String packaging;

    /** 着払い金額 */
    @Query(type = Query.Type.INNER_LIKE)
    private BigDecimal cashOnDeliveryAmount;

    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
}
