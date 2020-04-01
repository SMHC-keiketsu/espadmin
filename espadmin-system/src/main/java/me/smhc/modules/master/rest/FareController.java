package me.smhc.modules.master.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.smhc.aop.log.Log;
import me.smhc.modules.master.domain.Fare;
import me.smhc.modules.master.service.FareService;
import me.smhc.modules.master.service.dto.FareQueryCriteria;
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
* @author 布和
* @date 2020-03-20
*/
@Api(tags = "fare管理")
@RestController
@RequestMapping("/api/fare")
public class FareController {

    private final FareService fareService;

    public FareController(FareService fareService) {
        this.fareService = fareService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('fare:list')")
    public void download(HttpServletResponse response, FareQueryCriteria criteria) throws IOException {
        fareService.download(fareService.queryAll(criteria), response);
    }

    @ApiOperation("导入Excel数据")
    @PostMapping(value = "/uploadExcel")
    @PreAuthorize("@el.check('fare:add')")
    public ResponseEntity<Object> create(@RequestParam Long deptId, @RequestParam("file") MultipartFile file){
        return new ResponseEntity<>(fareService.create(deptId,file),HttpStatus.CREATED);
    }

    @GetMapping
    @Log("查询fare")
    @ApiOperation("查询fare")
    @PreAuthorize("@el.check('fare:list')")
    public ResponseEntity<Object> getFares(FareQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(fareService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增fare")
    @ApiOperation("新增fare")
    @PreAuthorize("@el.check('fare:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Fare resources){
        return new ResponseEntity<>(fareService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改fare")
    @ApiOperation("修改fare")
    @PreAuthorize("@el.check('fare:edit')")
    public ResponseEntity<Object> update(@Validated(Fare.Update.class) @RequestBody Fare resources){
        fareService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除fare")
    @ApiOperation("删除fare")
    @PreAuthorize("@el.check('fare:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        fareService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
