package me.smhc.modules.master.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author  布和
* @date 2020-03-25
*/
@Data
public class ImporterDto implements Serializable {

    private Long id;

    /** 輸入者コード */
    private String imc;

    /** 法人番号 */
    private String corporateNumber;

    /** 電話番号 */
    private String tel;

    /** 郵便番号 */
    private String postalCode;

    /** 輸入者名（英文） */
    private String enCompanyName;

    /** 住所（英文） */
    private String enAddressAll;

    /** 住所_都道府県（英文） */
    private String enAddress1;

    /** 住所_市区町村（英文） */
    private String enAddress2;

    /** 住所_番地（英文） */
    private String enAddress3;

    /** 住所_建物（英文） */
    private String enAddress4;

    /** 輸入者名（和文） */
    private String jaCompanyName;

    /** 住所（和文） */
    private String jaAddressAll;

    private Timestamp createTime;

    private Long createUserId;

    private Timestamp updateTime;

    private Long updateUserId;
}
