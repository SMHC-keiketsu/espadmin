package me.smhc.modules.monitor.service.mapper;

import me.smhc.base.BaseMapper;
import me.smhc.modules.monitor.domain.Server;
import me.smhc.modules.monitor.service.dto.ServerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author Zhang houying
* @date 2019-11-03
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServerMapper extends BaseMapper<ServerDTO, Server> {

}
