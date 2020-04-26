package me.smhc.modules.cts.service;

import me.smhc.modules.cts.domain.Permit;
import me.smhc.modules.cts.domain.PermitMic;
import me.smhc.modules.cts.service.dto.PermitMicDto;
import org.springframework.http.ResponseEntity;

import java.io.File;

/**
 * @author 布和
 * @date 2020-04-22
 */
public interface PermitMicService {

    /**
     * 添加到数据库
     * @param file
     */
    PermitMicDto addPermitMic(File file);

    /**
     * 创建
     * @param resources /
     * @return PermitMicDto
     */
    PermitMicDto create(PermitMic resources);
}
