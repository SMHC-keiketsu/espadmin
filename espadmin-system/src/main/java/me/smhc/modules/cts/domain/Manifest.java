package me.smhc.modules.cts.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import me.smhc.modules.system.domain.Dept;
import org.hibernate.annotations.*;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
* @author jhf
* @date 2020-03-24
*/
@Entity
@Data
@Table(name="manifest")
public class Manifest implements Serializable {

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(groups = Update.class)
    private Long id;

    /** 代理商名 */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "dept_id", referencedColumnName = "id")
    private Dept dept;

    /** MAWB番号 */
    @Column(name = "mawb_no",nullable = false)
    @NotBlank
    private String mawbNo;

    /** FlightNO */
    @Column(name = "flight_no",nullable = false)
    @NotBlank
    private String flightNo;

    /** FlightDate */
    @Column(name = "flight_date")
    @NotNull
    private Date flightDate;

    /** HAWB番号 */
    @Column(name = "hawb_no")
    @NotBlank
    private String hawbNo;

    /** 個数 */
    @Column(name = "pcs")
    @NotNull
    private Integer pcs;

    /** 重量 */
    @Column(name = "weight")
    private BigDecimal weight;

    /** 重量コード */
    @Column(name = "weight_code")
    private String weightCode;

    /** 品名 */
    @Column(name = "product_name")
    @NotBlank
    private String productName;

    /** インボイス価格条件コード */
    @Column(name = "invoice_condition_code")
    private String invoiceConditionCode;

    /** INVOICE通貨 CURRENCY */
    @Column(name = "invoice_iso")
    private String invoiceIso;

    /** INVOICE金額 DECLARED VALUE OF CUSTOMS */
    @Column(name = "invoice_value")
    private BigDecimal invoiceValue;

    /** 運賃区分コード */
    @Column(name = "fare_classification_code")
    private String fareClassificationCode;

    /** 運賃通貨 */
    @Column(name = "fare_iso")
    private String fareIso;

    /** 運賃金額 */
    @Column(name = "fare_value")
    private BigDecimal fareValue;

    /** 保険通貨 */
    @Column(name = "insurance_iso")
    private String insuranceIso;

    /** 保険金額 */
    @Column(name = "insurance_value")
    private BigDecimal insuranceValue;

    /** 荷受人名 */
    @Column(name = "importer_name")
    private String importerName;

    /** 荷受人郵便番号 */
    @Column(name = "importer_poster_code")
    private String importerPosterCode;

    /** 一括住所 */
    @Column(name = "importer_addr_all")
    private String importerAddrAll;

    /** 住所１ */
    @Column(name = "importer_addr_1")
    private String importerAddr1;

    /** 住所２ */
    @Column(name = "importer_addr_2")
    private String importerAddr2;

    /** 住所３ */
    @Column(name = "importer_addr_3")
    private String importerAddr3;

    /** 住所４ */
    @Column(name = "importer_addr_4")
    private String importerAddr4;

    /** 荷受人TEL */
    @Column(name = "importer_tel")
    private String importerTel;

    /** 荷送人名 */
    @Column(name = "shipper_name")
    private String shipperName;

    /** 荷送人郵便番号 */
    @Column(name = "shipper_poster_code")
    private String shipperPosterCode;

    /** 一括住所 */
    @Column(name = "shipper_addr_all")
    private String shipperAddrAll;

    /** 荷送人TEL */
    @Column(name = "shipper_tel")
    private String shipperTel;

    /** 原産国 */
    @Column(name = "origin")
    private String origin;

    /** 送り状No/問い合わせ番号 */
    @Column(name = "tracking_no")
    private String trackingNo;

    /** 納品先郵便番号 */
    @Column(name = "delivery_poster_code")
    private String deliveryPosterCode;

    /** 一括住所 */
    @Column(name = "delivery_addr_all")
    private String deliveryAddrAll;

    /** 住所１ */
    @Column(name = "delivery_addr_1")
    private String deliveryAddr1;

    /** 住所２ */
    @Column(name = "delivery_addr_2")
    private String deliveryAddr2;

    /** 住所３ */
    @Column(name = "delivery_addr_3")
    private String deliveryAddr3;

    /** 住所４ */
    @Column(name = "delivery_addr_4")
    private String deliveryAddr4;

    /** 納品先名 */
    @Column(name = "delivery_destination")
    private String deliveryDestination;

    /** 納品先担当者/配送業者 */
    @Column(name = "delivery_contact")
    private String deliveryContact;

    /** 納品先TEL */
    @Column(name = "delivery_tel")
    private String deliveryTel;

    /** 請求方式 */
    @Column(name = "billing_method")
    private String billingMethod;

    /** 包装 */
    @Column(name = "packaging")
    private String packaging;

    /** 着払い金額 */
    @Column(name = "cash_on_delivery_amount")
    private BigDecimal cashOnDeliveryAmount;

    /** 预留1 */
    @Column(name = "reserve_1")
    private String reserve1;

    /** 预留2 */
    @Column(name = "reserve_2")
    private String reserve2;

    /** 预留3 */
    @Column(name = "reserve_3")
    private String reserve3;

    /** 预留4 */
    @Column(name = "reserve_4")
    private String reserve4;

    /** 预留5 */
    @Column(name = "reserve_5")
    private String reserve5;

    /** 预留6 */
    @Column(name = "reserve_6")
    private String reserve6;

    /** 预留7 */
    @Column(name = "reserve_7")
    private String reserve7;

    /** 预留8 */
    @Column(name = "reserve_8")
    private String reserve8;

    /** 预留9 */
    @Column(name = "reserve_9")
    private String reserve9;

    /** 预留10 */
    @Column(name = "reserve_10")
    private String reserve10;

    /** 预留11 */
    @Column(name = "reserve_11")
    private String reserve11;

    /** 预留12 */
    @Column(name = "reserve_12")
    private String reserve12;

    /** 预留13 */
    @Column(name = "reserve_13")
    private String reserve13;

    /** 预留14 */
    @Column(name = "reserve_14")
    private String reserve14;

    /** 预留15 */
    @Column(name = "reserve_15")
    private String reserve15;

    /** 预留16 */
    @Column(name = "reserve_16")
    private String reserve16;

    /** 创建时间 */
    @Column(name = "create_time",nullable = false)
    @CreationTimestamp
    private Timestamp createTime;

    /** 创建用户ID 用户ID */
    @Column(name = "create_user_id")
    private Long createUserId;

    /** 更新时间 */
    @Column(name = "update_time",nullable = false)
    @UpdateTimestamp
    private Timestamp updateTime;

    /** 更新用户ID 用户ID */
    @Column(name = "update_user_id")
    private Long updateUserId;

    public @interface Update {}

    public void copy(Manifest source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}