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

    /**
     * 輸入者名/住所/法人番号/電話番号
     */
    @Query(blurry = "enAddressAll,jaAddressAll,enCompanyName,jaCompanyName,tel,corporateNumber",type = Query.Type.INNER_LIKE)
    private String queryAll;

    /**
     * 查询更新时间的区间
     */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
}
