package me.smhc.modules.master.rest;

import me.smhc.aop.log.Log;
import me.smhc.modules.master.domain.PatternConfig;
import me.smhc.modules.master.service.PatternConfigService;
import me.smhc.modules.master.service.dto.PatternConfigQueryCriteria;
import me.smhc.modules.system.domain.Dept;
import me.smhc.utils.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

/**
* @author 布和
* @date 2020-04-08
*/
@Api(tags = "pattern管理")
@RestController
@RequestMapping("/api/patternConfig")
public class PatternConfigController {

    private final PatternConfigService patternConfigService;

    public PatternConfigController(PatternConfigService patternConfigService) {
        this.patternConfigService = patternConfigService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('patternConfig:list')")
    public void download(HttpServletResponse response, PatternConfigQueryCriteria criteria) throws IOException {
        patternConfigService.download(patternConfigService.queryAll(criteria), response);
    }

    @GetMapping(value = "/checkName")
    @Log("查询pattern的name")
    @ApiOperation("查询pattern的name")
    @PreAuthorize("@el.check('patternConfig:list')")
    public ResponseEntity<Object> checkName(String name, Dept dept){
        Boolean flag = patternConfigService.findName(name,dept);
        if(StringUtils.isBlank(name)){
            return new ResponseEntity<>("名称不能为空",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(dept.getId() == null){
            return new ResponseEntity<>("代理店不能为空",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(!flag) {
            return new ResponseEntity<>(flag,HttpStatus.OK);
        }
        return new ResponseEntity<>(flag,HttpStatus.OK);
    }

    @GetMapping(value = "/checkUpdateName")
    @Log("查询pattern的name")
    @ApiOperation("查询pattern的name")
    @PreAuthorize("@el.check('patternConfig:list')")
    public ResponseEntity<Object> checkUpdateName(Long id,String name, Dept dept){
        Boolean flag = patternConfigService.queryUpdateNaem(id,name,dept);
        if(StringUtils.isBlank(name)){
            return new ResponseEntity<>("名称不能为空",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(dept.getId() == null){
            return new ResponseEntity<>("代理店不能为空",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(!flag) {
            return new ResponseEntity<>(flag,HttpStatus.OK);
        }
        return new ResponseEntity<>(flag,HttpStatus.OK);
    }

    @GetMapping
    @Log("查询pattern")
    @ApiOperation("查询pattern")
    @PreAuthorize("@el.check('patternConfig:list')")
    public ResponseEntity<Object> getPatternConfigs(PatternConfigQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(patternConfigService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增pattern")
    @ApiOperation("新增pattern")
    @PreAuthorize("@el.check('patternConfig:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody PatternConfig resources){
        boolean flag = patternConfigService.findName(resources.getName(),resources.getDept());
        if(!flag){
            return new ResponseEntity<>("名称不能重复",HttpStatus.OK);
        }
        return new ResponseEntity<>(patternConfigService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改pattern")
    @ApiOperation("修改pattern")
    @PreAuthorize("@el.check('patternConfig:edit')")
    public ResponseEntity<Object> update(@Validated(PatternConfig.Update.class) @RequestBody PatternConfig resources){
        patternConfigService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除pattern")
    @ApiOperation("删除pattern")
    @PreAuthorize("@el.check('patternConfig:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        patternConfigService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
