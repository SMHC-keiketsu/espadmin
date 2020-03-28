package me.smhc.modules.cts.service.mapper;

import me.smhc.base.BaseMapper;
import me.smhc.modules.cts.domain.Manifest;
import me.smhc.modules.cts.service.dto.ManifestDto;
import me.smhc.modules.system.service.mapper.DeptMapper;
import me.smhc.modules.system.service.mapper.JobMapper;
import me.smhc.modules.system.service.mapper.RoleMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jhf
* @date 2020-03-24
*/
@Mapper(componentModel = "spring",uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ManifestMapper extends BaseMapper<ManifestDto, Manifest> {

}
