package me.smhc.modules.master.rest;

import me.smhc.aop.log.Log;
import me.smhc.modules.master.domain.Keyword;
import me.smhc.modules.master.service.KeywordService;
import me.smhc.modules.master.service.dto.KeywordQueryCriteria;
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
* @author 布和
* @date 2020-03-19
*/
@Api(tags = "keyword管理")
@RestController
@RequestMapping("/api/keyword")
public class KeywordController {

    private final KeywordService keywordService;

    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('keyword:list')")
    public void download(HttpServletResponse response, KeywordQueryCriteria criteria) throws IOException {
        keywordService.download(keywordService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询keyword")
    @ApiOperation("查询keyword")
    @PreAuthorize("@el.check('keyword:list')")
    public ResponseEntity<Object> getKeywords(KeywordQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(keywordService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增keyword")
    @ApiOperation("新增keyword")
    @PreAuthorize("@el.check('keyword:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Keyword resources){
        return new ResponseEntity<>(keywordService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改keyword")
    @ApiOperation("修改keyword")
    @PreAuthorize("@el.check('keyword:edit')")
    public ResponseEntity<Object> update(@Validated(Keyword.Update.class) @RequestBody Keyword resources){
        keywordService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除keyword")
    @ApiOperation("删除keyword")
    @PreAuthorize("@el.check('keyword:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        keywordService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
