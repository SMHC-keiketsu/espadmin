package me.smhc.modules.cts.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.smhc.modules.system.domain.Dept;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import springfox.documentation.service.ApiListing;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
* @author jhf
* @date 2020-03-24
*/
@Entity
@Data
@Table(name="manifest_mawb")
public class ManifestMawb implements Serializable {

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

    /** manifest_hawb **/
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "manifest_mawb_id")
    List<ManifestHawb> manifestHawbList;

    /** mawb类型 **/
    @Column(name = "type")
    private String type;

    /** 状态 **/
    @Column(name = "status")
    private String status;

    /** IDA amount **/
    @Column(name = "ida_amount")
    private Long idaAmount;

    /** ida left amount **/
    @Column(name = "ida_left_amount")
    private Long idaLeftAmount;

    /** mic amount **/
    @Column(name="mic_amount")
    private Long micAmount;

    /** mic left amount **/
    @Column(name = "mic_left_amount")
    private Long micLeftAmount;

    /** hch amount **/
    @Column(name="hch_amount")
    private Long hchAmount;

    /** hch left amount **/
    @Column(name = "hch_left_amount")
    private Long hchLeftAmount;

    /** 申告条件 */
    @Column(name = "jyo")
    private String jyo;

    /** 申告先種別コード */
    @Column(name = "ic1")
    private String ic1;

    /** 申告等種別コード */
    @Column(name = "icb")
    private String icb;

    /** 大額・少額識別 */
    @Column(name = "ls")
    private String ls;

    /** あて先官署コード */
    @Column(name = "ch")
    private String ch;

    /** あて先部門コード */
    @Column(name = "chb")
    private String chb;

    /** 申告等予定年月日 */
    @Column(name = "icd")
    private String icd;

    /** 積載船（機）名 */
    @Column(name = "vsn")
    private String vsn;

    /** 入港年月日 */
    @Column(name = "arr")
    private String arr;

    /** 船（取）卸港コード */
    @Column(name = "dst")
    private String dst;

    /** 積出地コード */
    @Column(name = "psc")
    private String psc;

    /** 通関予定蔵置場コード */
    @Column(name = "st")
    private String st;

    /** 孫混載表示 */
    @Column(name = "mkh")
    private String mkh;

    /** 税関官署 */
    @Column(name = "chc")
    private String chc;

    /** 委託元混載業 */
    @Column(name = "ibb")
    private String ibb;

    /** 到着便名１ */
    @Column(name = "fl1")
    private String fl1;

    /** 到着便名２ */
    @Column(name = "fl2")
    private String fl2;

    /** 到着空港 */
    @Column(name = "pot")
    private String pot;

    /** 仕出地 */
    @Column(name = "org")
    private String org;

    /** ジョイント混載 */
    @Column(name = "jnt")
    private String jnt;

    /** 数据版本号 乐观锁*/
    @Version
    @Column(name = "reserve_1")
    private Long reserve1;

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

    public void copy(ManifestMawb source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
