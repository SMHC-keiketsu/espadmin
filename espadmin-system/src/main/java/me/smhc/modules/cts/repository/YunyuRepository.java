package me.smhc.modules.cts.repository;

import me.smhc.modules.cts.domain.Yunyu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author jhf
* @date 2020-04-23
*/
public interface YunyuRepository extends JpaRepository<Yunyu, String>, JpaSpecificationExecutor<Yunyu> {

    /**
     * 根据检索用电话查找輸入者
     * @param kensakuTel　電話
     * @return  輸入者
     */
    List<Yunyu> findByKensakuTel(String kensakuTel);

    /**
     * 根据輸入者コードOR法人番号查找輸入者
     * @param yunyuCd   輸入者コード
     * @param houjinNo　法人番号
     * @return  輸入者
     */
    Yunyu findByYunyuCdOrHoujinNo(String yunyuCd, String houjinNo);
}
