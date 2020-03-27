package me.smhc.modules.master.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.smhc.modules.master.domain.ExchangeRate;
import me.smhc.modules.master.service.dto.ExchangeRateDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-03-27T17:37:47+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_212 (Oracle Corporation)"
)
@Component
public class ExchangeRateMapperImpl implements ExchangeRateMapper {

    @Override
    public ExchangeRate toEntity(ExchangeRateDto dto) {
        if ( dto == null ) {
            return null;
        }

        ExchangeRate exchangeRate = new ExchangeRate();

        exchangeRate.setId( dto.getId() );
        exchangeRate.setStartDate( dto.getStartDate() );
        exchangeRate.setUpdateUserId( dto.getUpdateUserId() );
        exchangeRate.setRate( dto.getRate() );
        exchangeRate.setCreateTime( dto.getCreateTime() );
        exchangeRate.setCreateUserId( dto.getCreateUserId() );
        exchangeRate.setUpdateTime( dto.getUpdateTime() );
        exchangeRate.setEndDate( dto.getEndDate() );
        exchangeRate.setIso( dto.getIso() );

        return exchangeRate;
    }

    @Override
    public ExchangeRateDto toDto(ExchangeRate entity) {
        if ( entity == null ) {
            return null;
        }

        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();

        exchangeRateDto.setId( entity.getId() );
        exchangeRateDto.setStartDate( entity.getStartDate() );
        exchangeRateDto.setUpdateUserId( entity.getUpdateUserId() );
        exchangeRateDto.setRate( entity.getRate() );
        exchangeRateDto.setCreateTime( entity.getCreateTime() );
        exchangeRateDto.setCreateUserId( entity.getCreateUserId() );
        exchangeRateDto.setUpdateTime( entity.getUpdateTime() );
        exchangeRateDto.setEndDate( entity.getEndDate() );
        exchangeRateDto.setIso( entity.getIso() );

        return exchangeRateDto;
    }

    @Override
    public List<ExchangeRate> toEntity(List<ExchangeRateDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ExchangeRate> list = new ArrayList<ExchangeRate>( dtoList.size() );
        for ( ExchangeRateDto exchangeRateDto : dtoList ) {
            list.add( toEntity( exchangeRateDto ) );
        }

        return list;
    }

    @Override
    public List<ExchangeRateDto> toDto(List<ExchangeRate> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ExchangeRateDto> list = new ArrayList<ExchangeRateDto>( entityList.size() );
        for ( ExchangeRate exchangeRate : entityList ) {
            list.add( toDto( exchangeRate ) );
        }

        return list;
    }
}
