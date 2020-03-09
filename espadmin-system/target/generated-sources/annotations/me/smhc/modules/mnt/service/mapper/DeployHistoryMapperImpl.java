package me.smhc.modules.mnt.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.smhc.modules.mnt.domain.DeployHistory;
import me.smhc.modules.mnt.service.dto.DeployHistoryDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-03-09T15:36:43+0900",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 11.0.5 (JetBrains s.r.o)"
)
@Component
public class DeployHistoryMapperImpl implements DeployHistoryMapper {

    @Override
    public DeployHistory toEntity(DeployHistoryDto dto) {
        if ( dto == null ) {
            return null;
        }

        DeployHistory deployHistory = new DeployHistory();

        deployHistory.setId( dto.getId() );
        deployHistory.setAppName( dto.getAppName() );
        deployHistory.setIp( dto.getIp() );
        deployHistory.setDeployDate( dto.getDeployDate() );
        deployHistory.setDeployUser( dto.getDeployUser() );
        deployHistory.setDeployId( dto.getDeployId() );

        return deployHistory;
    }

    @Override
    public DeployHistoryDto toDto(DeployHistory entity) {
        if ( entity == null ) {
            return null;
        }

        DeployHistoryDto deployHistoryDto = new DeployHistoryDto();

        deployHistoryDto.setId( entity.getId() );
        deployHistoryDto.setAppName( entity.getAppName() );
        deployHistoryDto.setIp( entity.getIp() );
        deployHistoryDto.setDeployDate( entity.getDeployDate() );
        deployHistoryDto.setDeployUser( entity.getDeployUser() );
        deployHistoryDto.setDeployId( entity.getDeployId() );

        return deployHistoryDto;
    }

    @Override
    public List<DeployHistory> toEntity(List<DeployHistoryDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DeployHistory> list = new ArrayList<DeployHistory>( dtoList.size() );
        for ( DeployHistoryDto deployHistoryDto : dtoList ) {
            list.add( toEntity( deployHistoryDto ) );
        }

        return list;
    }

    @Override
    public List<DeployHistoryDto> toDto(List<DeployHistory> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DeployHistoryDto> list = new ArrayList<DeployHistoryDto>( entityList.size() );
        for ( DeployHistory deployHistory : entityList ) {
            list.add( toDto( deployHistory ) );
        }

        return list;
    }
}
