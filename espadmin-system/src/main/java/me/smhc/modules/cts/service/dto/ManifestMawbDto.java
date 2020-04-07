package me.smhc.modules.cts.service.dto;

import lombok.Data;
import me.smhc.modules.cts.domain.ManifestHawb;
import me.smhc.modules.system.domain.Dept;

import javax.persistence.Column;
import javax.persistence.Version;
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

    /** mawb类型 **/
    private String type;

    /** 状态 **/
    private String status;

    /** IDA amount **/
    private Long idaAmount;

    /** ida left amount **/
    private Long idaLeftAmount;

    /** mic amount **/
    private Long micAmount;
    /** mic left amount **/
    private Long micLeftAmount;

    /** hch amount **/
    private Long hchAmount;

    /** hch left amount **/
    private Long hchLeftAmount;

    /** 数据版本号 乐观锁*/
    private Long reserve1;

    /** 创建时间 */
    private Timestamp createTime;

    /** 创建用户ID 用户ID */
    private Long createUserId;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 更新用户ID 用户ID */
    private Long updateUserId;
}
