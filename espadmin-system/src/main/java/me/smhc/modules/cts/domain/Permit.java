package me.smhc.modules.cts.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
 * @author 布和
 * @date 2020-04-16
 */
@Entity
@Data
@Table(name="permit")
public class Permit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** mawb番号 */
    @Column(name = "mawb_no")
    private String mawbNo;

    /** hawb番号 */
    @Column(name = "hawb_no")
    private String hawbNo;

    /** 電文ファイル名 */
    @Column(name = "file_name")
    private String fileName;

    /** 类型(0:mic,1:idc) */
    @Column(name = "type")
    private int type;

    /** 附表Id */
    @Column(name = "permit_id")
    private Long permitId;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "update_user_id")
    private Long updateUserId;

    @Column(name = "create_user_id")
    private Long createUserId;

    public void copy(Permit source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
