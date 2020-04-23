package me.smhc.modules.cts.service.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author 布和
 * @date 2020-04-22
 */
@Data
public class PermitMicDto {
    private Long id;

    private String mawbNo;

    private String hawbNo;

    private String fileName;

    private String type;

    private Long permitId;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Long updateUserId;

    private Long createUserId;
}

