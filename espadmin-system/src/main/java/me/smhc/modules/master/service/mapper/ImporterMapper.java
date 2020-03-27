package me.smhc.modules.master.service.mapper;

import me.smhc.base.BaseMapper;
import me.smhc.modules.master.domain.Importer;
import me.smhc.modules.master.service.dto.ImporterDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author  
* @date 2020-03-25
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImporterMapper extends BaseMapper<ImporterDto, Importer> {

}
