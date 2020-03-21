package me.smhc.modules.master.service.dto;

import lombok.Data;
import java.util.List;
import me.smhc.annotation.Query;
import me.smhc.modules.system.domain.Dept;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
* @author 布和
* @date 2020-03-20
*/
@Data
public class FareQueryCriteria{

    private String name;
}
