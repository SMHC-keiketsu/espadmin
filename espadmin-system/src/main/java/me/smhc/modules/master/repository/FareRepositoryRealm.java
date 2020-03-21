package me.smhc.modules.master.repository;

import me.smhc.modules.master.domain.Fare;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FareRepositoryRealm {

    @Query(value = "SELECT *FROM fare f INNER JOIN dept d on f.dept_id = d.id WHERE d.`name` = ?1", nativeQuery = true)
    public List<Fare> queryFareByName(String name);
}
