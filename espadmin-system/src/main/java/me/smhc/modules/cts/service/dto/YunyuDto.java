package me.smhc.modules.cts.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @author jhf
* @date 2020-04-23
*/
@Data
public class YunyuDto implements Serializable {

    private String yunyuCd;

    private String houjinNo;

    private String yunyuNmE;

    private String yunyuYuubinNo;

    private String yunyuAddE1;

    private String yunyuAddE2;

    private String yunyuAddE3;

    private String yunyuAddE4;

    private String yunyuNmW;

    private String yunyuAddW;

    private String yunyuTel;

    private String yunyuFax;

    private String kensakuTel;

    private String noukigenEncyoCd;

    private String noufuHouhou;

    private String kouzaNo;

    private String tanpoNo;
}