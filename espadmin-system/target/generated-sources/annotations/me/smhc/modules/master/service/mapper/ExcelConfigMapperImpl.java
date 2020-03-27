package me.smhc.modules.master.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.smhc.modules.master.domain.ExcelConfig;
import me.smhc.modules.master.service.dto.ExcelConfigDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-03-27T17:37:47+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_212 (Oracle Corporation)"
)
@Component
public class ExcelConfigMapperImpl implements ExcelConfigMapper {

    @Override
    public ExcelConfig toEntity(ExcelConfigDto dto) {
        if ( dto == null ) {
            return null;
        }

        ExcelConfig excelConfig = new ExcelConfig();

        excelConfig.setId( dto.getId() );
        excelConfig.setCreateTime( dto.getCreateTime() );
        excelConfig.setCreateUserId( dto.getCreateUserId() );
        excelConfig.setUpdateTime( dto.getUpdateTime() );
        excelConfig.setUpdateUserId( dto.getUpdateUserId() );

        return excelConfig;
    }

    @Override
    public ExcelConfigDto toDto(ExcelConfig entity) {
        if ( entity == null ) {
            return null;
        }

        ExcelConfigDto excelConfigDto = new ExcelConfigDto();

        excelConfigDto.setId( entity.getId() );
        excelConfigDto.setCreateTime( entity.getCreateTime() );
        excelConfigDto.setCreateUserId( entity.getCreateUserId() );
        excelConfigDto.setUpdateTime( entity.getUpdateTime() );
        excelConfigDto.setUpdateUserId( entity.getUpdateUserId() );

        return excelConfigDto;
    }

    @Override
    public List<ExcelConfig> toEntity(List<ExcelConfigDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ExcelConfig> list = new ArrayList<ExcelConfig>( dtoList.size() );
        for ( ExcelConfigDto excelConfigDto : dtoList ) {
            list.add( toEntity( excelConfigDto ) );
        }

        return list;
    }

    @Override
    public List<ExcelConfigDto> toDto(List<ExcelConfig> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ExcelConfigDto> list = new ArrayList<ExcelConfigDto>( entityList.size() );
        for ( ExcelConfig excelConfig : entityList ) {
            list.add( toDto( excelConfig ) );
        }

        return list;
    }
}
