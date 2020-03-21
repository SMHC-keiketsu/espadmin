package me.smhc.modules.master.service.mapper;

import me.smhc.base.BaseMapper;
import me.smhc.modules.master.domain.Tariff;
import me.smhc.modules.master.service.dto.TariffDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author 布和
* @date 2020-03-17
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TariffMapper extends BaseMapper<TariffDto, Tariff> {

}
