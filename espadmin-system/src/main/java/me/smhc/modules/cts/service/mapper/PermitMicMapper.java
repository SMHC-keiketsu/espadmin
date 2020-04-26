package me.smhc.modules.cts.service.mapper;

import me.smhc.base.BaseMapper;
import me.smhc.modules.cts.domain.PermitMic;
import me.smhc.modules.cts.service.dto.PermitMicDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author 布和
 * @date 2020-04-22
 */
@Mapper(componentModel = "spring",uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermitMicMapper extends BaseMapper<PermitMicDto, PermitMic> {

}
