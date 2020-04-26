package me.smhc.modules.cts.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
* @author 布和
* @date 2020-04-16
*/
@Data
public class PermitDto implements Serializable {

    /** mawb番号 */
    private String mawbNo;

    /** hawb番号 */
    private String hawbNo;

    /** 電文ファイル名 */
    private String fileName;

    /** 类型(0:mic,1:idc) */
    private int type;

    /** 附表Id */
    private Long permitId;

    private Timestamp createTime;

    private Timestamp updateTime;

    private Long updateUserId;

    private Long createUserId;
}