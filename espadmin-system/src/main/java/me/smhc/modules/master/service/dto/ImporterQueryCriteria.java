package me.smhc.modules.master.service.dto;

import lombok.Data;
import me.smhc.annotation.Query;

import java.sql.Timestamp;
import java.util.List;

/**
* @author 布和
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

    /** or */
    @Query(blurry = "enAddressAll,jaAddressAll")
    private String addressAll;
    /** or */
    @Query(blurry = "enCompanyName,jaCompanyName")
    private String companyName;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
}
