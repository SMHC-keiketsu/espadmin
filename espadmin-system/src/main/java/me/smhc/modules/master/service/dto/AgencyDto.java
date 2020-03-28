package me.smhc.modules.master.service.dto;

import lombok.Data;
import me.smhc.modules.master.domain.ExcelConfig;

import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author 布和
* @date 2020-03-24
*/
@Data
public class AgencyDto implements Serializable {

    private Long id;

    /** 代理店名 */
    private String companyName;

    /** 担当者名 */
    private String contact;

    /** メールアドレス */
    private String email;

    /** 電話番号 */
    private String tel;

    /** 郵便番号 */
    private String postalCode;

    /** 住所 */
    private String addressAll;

    private Timestamp createTime;

    private Long createUserId;

    private Timestamp updateTime;

    private Long updateUserId;

    private Long excelConfigId;
    /** Excel Config **/
    private ExcelConfig excelConfig;
}
