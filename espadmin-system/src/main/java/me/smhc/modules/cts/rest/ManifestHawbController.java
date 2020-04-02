package me.smhc.modules.cts.rest;

import me.smhc.aop.log.Log;
import me.smhc.config.DataScope;
import me.smhc.modules.cts.domain.ManifestHawb;
import me.smhc.modules.cts.service.ManifestHawbService;
import me.smhc.modules.cts.service.dto.ManifestHawbQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author jhf
* @date 2020-03-24
*/
@Api(tags = "manifest管理")
@RestController
@RequestMapping("/api/manifestHawb")
public class ManifestHawbController {

    private final ManifestHawbService manifestHawbService;

    public ManifestHawbController(ManifestHawbService manifestHawbService) {
        this.manifestHawbService = manifestHawbService;
    }

    @GetMapping
    @Log("查询manifestHawb")
    @ApiOperation("查询manifestHawb")
    @PreAuthorize("@el.check('manifest:list')")
    public ResponseEntity<Object> getManifests(ManifestHawbQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(manifestHawbService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增manifestHawb")
    @ApiOperation("新增manifestHawb")
    @PreAuthorize("@el.check('manifest:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ManifestHawb resources){
        return new ResponseEntity<>(manifestHawbService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改manifestHawb")
    @ApiOperation("修改manifestHawb")
    @PreAuthorize("@el.check('manifest:edit')")
    public ResponseEntity<Object> update(@Validated(ManifestHawb.Update.class) @RequestBody ManifestHawb resources){
        manifestHawbService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除manifestHawb")
    @ApiOperation("删除manifestHawb")
    @PreAuthorize("@el.check('manifest:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        manifestHawbService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
