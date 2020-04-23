package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.Importer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author 布和
* @date 2020-03-25
*/
public interface ImporterRepository extends JpaRepository<Importer, Long>, JpaSpecificationExecutor<Importer> {

    /**
     * 根据手机号码查询
     * @param tel 电话
     * @return 輸入者
     */
    List<Importer> findByTel(String tel);
}
