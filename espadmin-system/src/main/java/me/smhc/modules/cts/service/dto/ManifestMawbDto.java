package me.smhc.modules.cts.service.dto;

import lombok.Data;
import me.smhc.modules.cts.domain.ManifestHawb;
import me.smhc.modules.system.domain.Dept;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
* @author jhf
* @date 2020-03-24
*/
@Data
public class ManifestMawbDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8167577684265137045L;

    /** ID */
    private Long id;

    /** 代理商名 */
    private Dept dept;

    /** MAWB番号 */
    private String mawbNo;

    /** FlightNO */
    private String flightNo;

    /** FlightDate */
    private Date flightDate;

    /** manifestHawb  list **/
    List<ManifestHawb> manifestHawbList;

    /** 预留1 */
    private String reserve1;

    /** 预留2 */
    private String reserve2;

    /** 预留3 */
    private String reserve3;

    /** 预留4 */
    private String reserve4;

    /** 预留5 */
    private String reserve5;

    /** 预留6 */
    private String reserve6;

    /** 创建时间 */
    private Timestamp createTime;

    /** 创建用户ID 用户ID */
    private Long createUserId;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 更新用户ID 用户ID */
    private Long updateUserId;
}
