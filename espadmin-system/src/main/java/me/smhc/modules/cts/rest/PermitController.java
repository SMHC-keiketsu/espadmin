package me.smhc.modules.cts.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.smhc.aop.log.Log;
import me.smhc.modules.cts.domain.Permit;
import me.smhc.modules.cts.domain.PermitMic;
import me.smhc.modules.cts.service.PermitMicService;
import me.smhc.modules.cts.service.PermitService;
import me.smhc.modules.cts.service.dto.PermitDto;
import me.smhc.modules.cts.service.dto.PermitMicDto;
import me.smhc.modules.cts.service.dto.PermitQueryCriteria;
import me.smhc.utils.FileUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@Api(tags = "permit管理")
@RestController
@RequestMapping("/api/permit")
public class PermitController {

    private final PermitService permitService;
    private final PermitMicService permitMicService;
    //mic 的类型
    private final int mic = 0;
    //ida 的类型
    private final int ida = 1;

    public PermitController(PermitService permitService, PermitMicService permitMicService) {
        this.permitService = permitService;
        this.permitMicService = permitMicService;
    }



    @PostMapping()
    @Log("扫描permit")
    @ApiOperation("扫描permit")
    @PreAuthorize("@el.check('permit:add')")
    public ResponseEntity<Object> add(PermitQueryCriteria criteria, Pageable pageable) throws Exception {
        List<File> list = FileUtil.loopFiles("F:\\1");
        for(File file :list){
            if (file.getName().contains("AAD2FG2")) {
                Permit result = permitService.findByFileName(file.getName());
                if(result == null) {
                    PermitMicDto permitMicDto =  permitMicService.addPermitMic(file);
                    Permit permit = new Permit();
                    permit.setPermitId(permitMicDto.getId());
                    permit.setFileName(file.getName());
                    permit.setMawbNo(permitMicDto.getMab());
                    permit.setHawbNo(permitMicDto.getHab());
                    permit.setType(this.mic);
                    permitService.create(permit);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @Log("扫描permit")
    @ApiOperation("扫描permit")
    @PreAuthorize("@el.check('permit:list')")
    public ResponseEntity<Object> list(PermitQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(permitService.queryAll(criteria, pageable), HttpStatus.OK);
    }

}
