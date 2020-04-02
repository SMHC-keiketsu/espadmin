package me.smhc.modules.cts.service.mapper;

import me.smhc.base.BaseMapper;
import me.smhc.modules.cts.domain.ManifestHawb;
import me.smhc.modules.cts.domain.ManifestMawb;
import me.smhc.modules.cts.service.dto.ManifestHawbDto;
import me.smhc.modules.cts.service.dto.ManifestMawbDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author jhf
* @date 2020-03-24
*/
@Mapper(componentModel = "spring",uses = {}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ManifestMawbMapper extends BaseMapper<ManifestMawbDto, ManifestMawb> {

}
