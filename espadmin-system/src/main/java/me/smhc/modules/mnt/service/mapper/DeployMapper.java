package me.smhc.modules.mnt.service.mapper;

import me.smhc.base.BaseMapper;
import me.smhc.modules.mnt.domain.Deploy;
import me.smhc.modules.mnt.service.dto.DeployDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author zhanghouying
* @date 2019-08-24
*/
@Mapper(componentModel = "spring",uses = {AppMapper.class, ServerDeployMapper.class},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeployMapper extends BaseMapper<DeployDto, Deploy> {

}
