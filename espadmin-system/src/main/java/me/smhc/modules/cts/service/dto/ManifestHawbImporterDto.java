package me.smhc.modules.cts.service.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * @author jhf
 * @date 2020-03-24
 */
@Data
@Value
public class ManifestHawbImporterDto implements Serializable {

    /** 輸入者コード/cnc　*/
    private String imc;

    /** 荷受人名 */
    private String importerName;

    /** 荷受人郵便番号 */
    private String importerPosterCode;

    /** 一括住所 */
    private String importerAddrAll;

    /** 住所１ */
    private String importerAddr1;

    /** 住所２ */
    private String importerAddr2;

    /** 住所３ */
    private String importerAddr3;

    /** 住所４ */
    private String importerAddr4;

    /** 荷受人TEL */
    private String importerTel;
}
