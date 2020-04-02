package me.smhc.modules.cts.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import me.smhc.modules.system.domain.Dept;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
