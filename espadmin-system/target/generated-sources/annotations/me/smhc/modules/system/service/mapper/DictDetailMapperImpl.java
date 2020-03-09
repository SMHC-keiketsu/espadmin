package me.smhc.modules.system.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.smhc.modules.system.domain.DictDetail;
import me.smhc.modules.system.service.dto.DictDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-03-09T15:36:44+0900",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 11.0.5 (JetBrains s.r.o)"
)
@Component
public class DictDetailMapperImpl implements DictDetailMapper {

    @Autowired
    private DictSmallMapper dictSmallMapper;

    @Override
    public DictDetail toEntity(DictDetailDto dto) {
        if ( dto == null ) {
            return null;
        }

        DictDetail dictDetail = new DictDetail();

        dictDetail.setId( dto.getId() );
        dictDetail.setLabel( dto.getLabel() );
        dictDetail.setValue( dto.getValue() );
        dictDetail.setSort( dto.getSort() );
        dictDetail.setDict( dictSmallMapper.toEntity( dto.getDict() ) );
        dictDetail.setCreateTime( dto.getCreateTime() );

        return dictDetail;
    }

    @Override
    public DictDetailDto toDto(DictDetail entity) {
        if ( entity == null ) {
            return null;
        }

        DictDetailDto dictDetailDto = new DictDetailDto();

        dictDetailDto.setId( entity.getId() );
        dictDetailDto.setLabel( entity.getLabel() );
        dictDetailDto.setValue( entity.getValue() );
        dictDetailDto.setSort( entity.getSort() );
        dictDetailDto.setDict( dictSmallMapper.toDto( entity.getDict() ) );
        dictDetailDto.setCreateTime( entity.getCreateTime() );

        return dictDetailDto;
    }

    @Override
    public List<DictDetail> toEntity(List<DictDetailDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DictDetail> list = new ArrayList<DictDetail>( dtoList.size() );
        for ( DictDetailDto dictDetailDto : dtoList ) {
            list.add( toEntity( dictDetailDto ) );
        }

        return list;
    }

    @Override
    public List<DictDetailDto> toDto(List<DictDetail> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DictDetailDto> list = new ArrayList<DictDetailDto>( entityList.size() );
        for ( DictDetail dictDetail : entityList ) {
            list.add( toDto( dictDetail ) );
        }

        return list;
    }
}
