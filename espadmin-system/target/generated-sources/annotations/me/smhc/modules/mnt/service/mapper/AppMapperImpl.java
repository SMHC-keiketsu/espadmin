package me.smhc.modules.mnt.service.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import me.smhc.modules.mnt.domain.App;
import me.smhc.modules.mnt.service.dto.AppDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-03-09T15:36:44+0900",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 11.0.5 (JetBrains s.r.o)"
)
@Component
public class AppMapperImpl implements AppMapper {

    @Override
    public App toEntity(AppDto dto) {
        if ( dto == null ) {
            return null;
        }

        App app = new App();

        app.setId( dto.getId() );
        app.setName( dto.getName() );
        if ( dto.getPort() != null ) {
            app.setPort( dto.getPort() );
        }
        app.setUploadPath( dto.getUploadPath() );
        app.setDeployPath( dto.getDeployPath() );
        app.setBackupPath( dto.getBackupPath() );
        app.setStartScript( dto.getStartScript() );
        app.setDeployScript( dto.getDeployScript() );
        app.setCreateTime( dto.getCreateTime() );

        return app;
    }

    @Override
    public AppDto toDto(App entity) {
        if ( entity == null ) {
            return null;
        }

        AppDto appDto = new AppDto();

        appDto.setId( entity.getId() );
        appDto.setName( entity.getName() );
        appDto.setPort( entity.getPort() );
        appDto.setUploadPath( entity.getUploadPath() );
        appDto.setDeployPath( entity.getDeployPath() );
        appDto.setBackupPath( entity.getBackupPath() );
        appDto.setStartScript( entity.getStartScript() );
        appDto.setDeployScript( entity.getDeployScript() );
        appDto.setCreateTime( entity.getCreateTime() );

        return appDto;
    }

    @Override
    public List<App> toEntity(List<AppDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<App> list = new ArrayList<App>( dtoList.size() );
        for ( AppDto appDto : dtoList ) {
            list.add( toEntity( appDto ) );
        }

        return list;
    }

    @Override
    public List<AppDto> toDto(List<App> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AppDto> list = new ArrayList<AppDto>( entityList.size() );
        for ( App app : entityList ) {
            list.add( toDto( app ) );
        }

        return list;
    }
}
