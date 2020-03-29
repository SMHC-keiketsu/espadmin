package me.smhc.modules.master.service.mapper;

import me.smhc.base.BaseMapper;
import me.smhc.modules.master.domain.ExcelConfig;
import me.smhc.modules.master.service.dto.ExcelConfigDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author 布和
* @date 2020-03-26
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExcelConfigMapper extends BaseMapper<ExcelConfigDto, ExcelConfig> {

}
