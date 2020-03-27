package me.smhc.modules.master.service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import me.smhc.annotation.Query;

/**
* @author
* @date 2020-03-25
*/
@Data
public class ImporterQueryCriteria{

    /** 精确 */
    @Query
    private String corporateNumber;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String tel;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String enAddressAll;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String jaAddressAll;

    @Query(type = Query.Type.INNER_LIKE)
    private String enCompanyName;

    @Query(type = Query.Type.INNER_LIKE)
    private String jaCompanyName;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
}
