package me.smhc.modules.cts.service.mapper;

import me.smhc.base.BaseMapper;
import me.smhc.modules.cts.domain.Yunyu;
import me.smhc.modules.cts.service.dto.YunyuDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jhf
* @date 2020-04-23
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface YunyuMapper extends BaseMapper<YunyuDto, Yunyu> {

}
