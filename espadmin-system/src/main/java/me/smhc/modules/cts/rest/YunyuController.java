package me.smhc.modules.cts.rest;

import me.smhc.aop.log.Log;
import me.smhc.modules.cts.domain.Yunyu;
import me.smhc.modules.cts.service.YunyuService;
import me.smhc.modules.cts.service.dto.YunyuQueryCriteria;
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
* @author jhf
* @date 2020-04-23
*/
@Api(tags = "yunyu管理")
@RestController
@RequestMapping("/api/yunyu")
public class YunyuController {

    private final YunyuService yunyuService;

    public YunyuController(YunyuService yunyuService) {
        this.yunyuService = yunyuService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('yunyu:list')")
    public void download(HttpServletResponse response, YunyuQueryCriteria criteria) throws IOException {
        yunyuService.download(yunyuService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询yunyu")
    @ApiOperation("查询yunyu")
    @PreAuthorize("@el.check('yunyu:list')")
    public ResponseEntity<Object> getYunyus(YunyuQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(yunyuService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增yunyu")
    @ApiOperation("新增yunyu")
    @PreAuthorize("@el.check('yunyu:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Yunyu resources){
        return new ResponseEntity<>(yunyuService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改yunyu")
    @ApiOperation("修改yunyu")
    @PreAuthorize("@el.check('yunyu:edit')")
    public ResponseEntity<Object> update(@Validated(Yunyu.Update.class) @RequestBody Yunyu resources){
        yunyuService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除yunyu")
    @ApiOperation("删除yunyu")
    @PreAuthorize("@el.check('yunyu:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody String[] ids) {
        yunyuService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
