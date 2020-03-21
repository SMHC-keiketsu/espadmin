package me.smhc.modules.master.service.mapper;

import me.smhc.base.BaseMapper;
import me.smhc.modules.master.domain.Fare;
import me.smhc.modules.master.service.dto.FareDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author 布和
* @date 2020-03-20
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FareMapper extends BaseMapper<FareDto, Fare> {

}
