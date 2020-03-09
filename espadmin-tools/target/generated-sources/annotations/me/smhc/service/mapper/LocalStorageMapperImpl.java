package me.smhc.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.smhc.domain.LocalStorage;
import me.smhc.service.dto.LocalStorageDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-03-09T15:36:39+0900",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 11.0.5 (JetBrains s.r.o)"
)
@Component
public class LocalStorageMapperImpl implements LocalStorageMapper {

    @Override
    public LocalStorage toEntity(LocalStorageDto dto) {
        if ( dto == null ) {
            return null;
        }

        LocalStorage localStorage = new LocalStorage();

        localStorage.setId( dto.getId() );
        localStorage.setRealName( dto.getRealName() );
        localStorage.setName( dto.getName() );
        localStorage.setSuffix( dto.getSuffix() );
        localStorage.setType( dto.getType() );
        localStorage.setSize( dto.getSize() );
        localStorage.setOperate( dto.getOperate() );
        localStorage.setCreateTime( dto.getCreateTime() );

        return localStorage;
    }

    @Override
    public LocalStorageDto toDto(LocalStorage entity) {
        if ( entity == null ) {
            return null;
        }

        LocalStorageDto localStorageDto = new LocalStorageDto();

        localStorageDto.setId( entity.getId() );
        localStorageDto.setRealName( entity.getRealName() );
        localStorageDto.setName( entity.getName() );
        localStorageDto.setSuffix( entity.getSuffix() );
        localStorageDto.setType( entity.getType() );
        localStorageDto.setSize( entity.getSize() );
        localStorageDto.setOperate( entity.getOperate() );
        localStorageDto.setCreateTime( entity.getCreateTime() );

        return localStorageDto;
    }

    @Override
    public List<LocalStorage> toEntity(List<LocalStorageDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<LocalStorage> list = new ArrayList<LocalStorage>( dtoList.size() );
        for ( LocalStorageDto localStorageDto : dtoList ) {
            list.add( toEntity( localStorageDto ) );
        }

        return list;
    }

    @Override
    public List<LocalStorageDto> toDto(List<LocalStorage> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<LocalStorageDto> list = new ArrayList<LocalStorageDto>( entityList.size() );
        for ( LocalStorage localStorage : entityList ) {
            list.add( toDto( localStorage ) );
        }

        return list;
    }
}
