package me.smhc.modules.master.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author 布和
* @date 2020-03-26
*/
@Entity
@Data
@Table(name="excel_config")
public class ExcelConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(groups = Update.class)
    @Column(name = "id")
    private Long id;

    @Column(name = "mainfest_excel",nullable = false)
    private String mainFestExcel;

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

    public @interface Update {}

    public void copy(ExcelConfig source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

}
