package me.smhc.modules.cts.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.smhc.aop.log.Log;
import me.smhc.modules.cts.domain.PermitMic;
import me.smhc.modules.cts.service.PermitMicService;
import me.smhc.modules.cts.service.dto.ManifestMawbQueryCriteria;
import me.smhc.modules.cts.service.dto.PermitMicQueryCriteria;
import me.smhc.utils.FileUtil;
import me.smhc.utils.ScanUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Api(tags = "permit管理")
@RestController
@RequestMapping("/api/permitMic")
public class PermitMicController {

    private final PermitMicService permitMicService;

    public PermitMicController(PermitMicService permitMicService) {
        this.permitMicService = permitMicService;
    }

    @GetMapping("scan")
    @Log("扫描permitMic")
    @ApiOperation("扫描permitMic")
    @PreAuthorize("@el.check('permitMic:list')")
    public ResponseEntity<Object> scan(PermitMicQueryCriteria criteria, Pageable pageable) throws Exception {
        List<File> list = FileUtil.loopFiles("F:\\1");
        for(File file :list){
            if (file.getName().contains("AAD2FG2")) {
                PermitMic permitMic = permitMicService.findByFileName(file.getName());
                if(permitMic == null) {
                    permitMicService.addPermitMic(file);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @Log("扫描permitMic")
    @ApiOperation("扫描permitMic")
    @PreAuthorize("@el.check('permitMic:list')")
    public ResponseEntity<Object> list(PermitMicQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(permitMicService.queryAll(criteria, pageable), HttpStatus.OK);
    }

}
