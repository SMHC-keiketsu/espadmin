package me.smhc.modules.cts.rest;

import me.smhc.aop.log.Log;
import me.smhc.config.DataScope;
import me.smhc.modules.cts.domain.Manifest;
import me.smhc.modules.cts.service.ManifestService;
import me.smhc.modules.cts.service.dto.ManifestQueryCriteria;
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
@RequestMapping("/api/manifest")
public class ManifestController {

    private final ManifestService manifestService;
    private final DataScope dataScope;

    public ManifestController(ManifestService manifestService, DataScope dataScope) {
        this.manifestService = manifestService;
        this.dataScope = dataScope;
    }

    @ApiOperation("导入Excel数据")
    @PostMapping(value = "/uploadExcel")
    @PreAuthorize("@el.check('manifest:add')")
    public ResponseEntity<Object> create(@RequestParam Long deptId, @RequestParam("file") MultipartFile file){
        return new ResponseEntity<>(manifestService.create(deptId, file),HttpStatus.CREATED);
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('manifest:list')")
    public void download(HttpServletResponse response, ManifestQueryCriteria criteria) throws IOException {
        criteria.setDeptIds(dataScope.getDeptIds());
        manifestService.download(manifestService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询manifest")
    @ApiOperation("查询manifest")
    @PreAuthorize("@el.check('manifest:list')")
    public ResponseEntity<Object> getManifests(ManifestQueryCriteria criteria, Pageable pageable){
        criteria.setDeptIds(dataScope.getDeptIds());
        return new ResponseEntity<>(manifestService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增manifest")
    @ApiOperation("新增manifest")
    @PreAuthorize("@el.check('manifest:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Manifest resources){
        return new ResponseEntity<>(manifestService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改manifest")
    @ApiOperation("修改manifest")
    @PreAuthorize("@el.check('manifest:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Manifest resources){
        manifestService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除manifest")
    @ApiOperation("删除manifest")
    @PreAuthorize("@el.check('manifest:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        manifestService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
