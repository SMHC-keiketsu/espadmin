package me.smhc.modules.master.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
* @author 布和
* @date 2020-03-26
*/
@Data
public class ExcelConfigDto implements Serializable {

    private Long id;

    private String manifestExcel;

    private Timestamp createTime;

    private Long createUserId;

    private Timestamp updateTime;

    private Long updateUserId;
}
