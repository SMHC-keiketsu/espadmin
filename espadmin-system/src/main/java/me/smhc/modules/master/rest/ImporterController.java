package me.smhc.modules.master.rest;

import me.smhc.aop.log.Log;
import me.smhc.modules.master.domain.Importer;
import me.smhc.modules.master.service.ImporterService;
import me.smhc.modules.master.service.dto.ImporterQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author
* @date 2020-03-25
*/
@Api(tags = "importer管理")
@RestController
@RequestMapping("/api/importer")
public class ImporterController {

    private final ImporterService importerService;

    public ImporterController(ImporterService importerService) {
        this.importerService = importerService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('importer:list')")
    public void download(HttpServletResponse response, ImporterQueryCriteria criteria) throws IOException {
        importerService.download(importerService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询importer")
    @ApiOperation("查询importer")
    @PreAuthorize("@el.check('importer:list')")
    public ResponseEntity<Object> getImporters(ImporterQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(importerService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增importer")
    @ApiOperation("新增importer")
    @PreAuthorize("@el.check('importer:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Importer resources){
        return new ResponseEntity<>(importerService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改importer")
    @ApiOperation("修改importer")
    @PreAuthorize("@el.check('importer:edit')")
    public ResponseEntity<Object> update(@Validated(Importer.Update.class) @RequestBody Importer resources){
        importerService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除importer")
    @ApiOperation("删除importer")
    @PreAuthorize("@el.check('importer:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        importerService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
