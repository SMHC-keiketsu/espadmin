package me.smhc.modules.master.rest;

<<<<<<< HEAD
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
=======
>>>>>>> 1e8f48c88ff24da2ee7db876674476e77c04e730
import me.smhc.aop.log.Log;
import me.smhc.modules.master.domain.Agency;
import me.smhc.modules.master.domain.ExcelConfig;
import me.smhc.modules.master.service.ExcelConfigService;
import me.smhc.modules.master.service.dto.ExcelConfigQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author
* @date 2020-03-26
*/
@Api(tags = "excelConfig管理")
@RestController
@RequestMapping("/api/excelConfig")
public class ExcelConfigController {

    private final ExcelConfigService excelConfigService;

    public ExcelConfigController(ExcelConfigService excelConfigService) {
        this.excelConfigService = excelConfigService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('excelConfig:list')")
    public void download(HttpServletResponse response, ExcelConfigQueryCriteria criteria) throws IOException {
        excelConfigService.download(excelConfigService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询excelConfig")
    @ApiOperation("查询excelConfig")
    @PreAuthorize("@el.check('excelConfig:list')")
    public ResponseEntity<Object> getExcelConfigs(ExcelConfigQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(excelConfigService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增excelConfig")
    @ApiOperation("新增excelConfig")
    @PreAuthorize("@el.check('excelConfig:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ExcelConfig resources){
        return new ResponseEntity<>(excelConfigService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改excelConfig")
    @ApiOperation("修改excelConfig")
    @PreAuthorize("@el.check('excelConfig:edit')")
    public ResponseEntity<Object> update(@Validated(ExcelConfig.Update.class) @RequestBody ExcelConfig resources){
        excelConfigService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除excelConfig")
    @ApiOperation("删除excelConfig")
    @PreAuthorize("@el.check('excelConfig:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        excelConfigService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
