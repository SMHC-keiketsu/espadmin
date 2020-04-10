package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.PatternConfig;
import me.smhc.modules.system.domain.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author 布和
* @date 2020-04-08
*/
public interface PatternConfigRepository extends JpaRepository<PatternConfig, Long>, JpaSpecificationExecutor<PatternConfig> {

    /**
     * 查询同一个部门是否有多个name
     * @param name
     * @param dept
     * @return PatternConfig
     */
    PatternConfig findByNameAndDept(String name, Dept dept);
    /**
     * 查看更新的时候是否有名称
     * @param id
     * @param name
     * @param dept
     * @return PatternConfig
     */
    PatternConfig findByIdAndNameAndDept(Long id, String name,Dept dept);
}
