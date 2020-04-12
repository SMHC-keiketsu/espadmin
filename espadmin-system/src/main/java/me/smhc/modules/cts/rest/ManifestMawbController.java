package me.smhc.modules.cts.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.smhc.aop.log.Log;
import me.smhc.config.DataScope;
import me.smhc.modules.cts.domain.ManifestMawb;
import me.smhc.modules.cts.service.ManifestMawbService;
import me.smhc.modules.cts.service.dto.ManifestMawbQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author jhf
* @date 2020-03-24
*/
@Api(tags = "manifestMawb管理")
@RestController
@RequestMapping("/api/manifestMawb")
public class ManifestMawbController {

    private final ManifestMawbService manifestMawbService;
    private final DataScope dataScope;

    public ManifestMawbController(ManifestMawbService manifestMawbService, DataScope dataScope) {
        this.manifestMawbService = manifestMawbService;
        this.dataScope = dataScope;
    }

    @ApiOperation("导入Excel数据")
    @PostMapping(value = "/uploadExcel")
    @PreAuthorize("@el.check('manifest:add')")
    public ResponseEntity<Object> create(@RequestParam Long deptId,@RequestParam Long patternId, @RequestParam("file") MultipartFile file){
        return new ResponseEntity<>(manifestMawbService.create(deptId, patternId, file),HttpStatus.CREATED);
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('manifest:list')")
    public void download(HttpServletResponse response, ManifestMawbQueryCriteria criteria) throws IOException {
        criteria.setDeptIds(dataScope.getDeptIds());
        manifestMawbService.download(manifestMawbService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询manifestMawb")
    @ApiOperation("查询manifestMawb")
    @PreAuthorize("@el.check('manifest:list')")
    public ResponseEntity<Object> getManifests(ManifestMawbQueryCriteria criteria, Pageable pageable){
        criteria.setDeptIds(dataScope.getDeptIds());
        return new ResponseEntity<>(manifestMawbService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增manifestMawb")
    @ApiOperation("新增manifestMawb")
    @PreAuthorize("@el.check('manifest:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ManifestMawb resources){
        return new ResponseEntity<>(manifestMawbService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改manifestMawb")
    @ApiOperation("修改manifestMawb")
    @PreAuthorize("@el.check('manifest:edit')")
    public ResponseEntity<Object> update(@Validated(ManifestMawb.Update.class) @RequestBody ManifestMawb resources){
        manifestMawbService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除manifestMawb")
    @ApiOperation("删除manifestMawb")
    @PreAuthorize("@el.check('manifest:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        manifestMawbService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/calculateCif")
    @Log("CIF計算")
    @ApiOperation("CIF計算")
    @PreAuthorize("@el.check('manifest:edit')")
    public ResponseEntity<Object> calculateCif(@RequestBody Long id){
        manifestMawbService.calculateCIF(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
