package me.smhc.modules.master.service.mapper;

import me.smhc.base.BaseMapper;
import me.smhc.modules.master.domain.PatternConfig;
import me.smhc.modules.master.service.dto.PatternConfigDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author 布和
* @date 2020-04-08
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatternConfigMapper extends BaseMapper<PatternConfigDto, PatternConfig> {

}
