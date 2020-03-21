package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author 布和
* @date 2020-03-20
*/
public interface FareRepository extends JpaRepository<Fare, Long>, JpaSpecificationExecutor<Fare> {
}
