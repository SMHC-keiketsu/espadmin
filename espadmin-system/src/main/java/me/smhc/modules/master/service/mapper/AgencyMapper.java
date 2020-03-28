package me.smhc.modules.master.service.mapper;

import me.smhc.base.BaseMapper;
import me.smhc.modules.master.domain.Agency;
import me.smhc.modules.master.service.dto.AgencyDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author 布和
* @date 2020-03-24
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AgencyMapper extends BaseMapper<AgencyDto, Agency> {

}
