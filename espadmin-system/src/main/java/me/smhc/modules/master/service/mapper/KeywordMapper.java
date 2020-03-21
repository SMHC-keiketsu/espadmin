package me.smhc.modules.master.service.mapper;

import me.smhc.base.BaseMapper;
import me.smhc.modules.master.domain.Keyword;
import me.smhc.modules.master.service.dto.KeywordDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author 布和
* @date 2020-03-19
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KeywordMapper extends BaseMapper<KeywordDto, Keyword> {

}
