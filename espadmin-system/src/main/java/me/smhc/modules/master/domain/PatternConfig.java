package me.smhc.modules.master.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author 布和
* @date 2020-04-08
*/
@Entity
@Data
@Table(name="pattern_config")
public class PatternConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dept_id")
    private Long deptId;

    /** 同一部门内，名字不可重复 */
    @Column(name = "name")
    private String name;

    /** IDA配置 */
    @Column(name = "ida_config")
    private String idaConfig;

    /** MIC配置 */
    @Column(name = "mic_config")
    private String micConfig;

    /** HCH配置 */
    @Column(name = "hch_config")
    private String hchConfig;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "create_user_id")
    private Long createUserId;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    @Column(name = "update_user_id")
    private Long updateUserId;

    public void copy(PatternConfig source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
