package me.smhc.modules.cts.service.mapper;

import me.smhc.base.BaseMapper;
import me.smhc.modules.cts.domain.Permit;
import me.smhc.modules.cts.service.dto.PermitDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author 布和
 * @date 2020-03-24
 */
@Mapper(componentModel = "spring",uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermitMapper extends BaseMapper<PermitDto, Permit> {

}
