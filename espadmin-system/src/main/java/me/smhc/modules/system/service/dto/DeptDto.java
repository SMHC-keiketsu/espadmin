package me.smhc.modules.system.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.smhc.modules.master.domain.Agency;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2019-03-25
 */
@Data
public class DeptDto implements Serializable {

    private Long id;

    private String name;

    @NotNull
    private Boolean enabled;

    private Long pid;

    private Integer type;

    private Agency agency;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DeptDto> children;

    private Timestamp createTime;

    public String getLabel() {
        return name;
    }
}
