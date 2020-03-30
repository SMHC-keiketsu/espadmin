package me.smhc.modules.master.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.smhc.aop.log.Log;
import me.smhc.modules.master.domain.Agency;
import me.smhc.modules.master.service.AgencyService;
import me.smhc.modules.master.service.dto.AgencyQueryCriteria;
import me.smhc.utils.ThrowableUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author 布和
* @date 2020-03-24
*/
@Api(tags = "agency管理")
@RestController
@RequestMapping("/api/agency")
public class AgencyController {

    private final AgencyService agencyService;

    public AgencyController(AgencyService agencyService) {
        this.agencyService = agencyService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('agency:list')")
    public void download(HttpServletResponse response, AgencyQueryCriteria criteria) throws IOException {
        agencyService.download(agencyService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询agency")
    @ApiOperation("查询agency")
    @PreAuthorize("@el.check('agency:list')")
    public ResponseEntity<Object> getAgencys(AgencyQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(agencyService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增agency")
    @ApiOperation("新增agency")
    @PreAuthorize("@el.check('agency:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Agency resources){
        return new ResponseEntity<>(agencyService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改agency")
    @ApiOperation("修改agency")
    @PreAuthorize("@el.check('agency:edit')")
    public ResponseEntity<Object> update(@Validated(Agency.Update.class) @RequestBody Agency resources){
        agencyService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除agency")
    @ApiOperation("删除agency")
    @PreAuthorize("@el.check('agency:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        try{
            agencyService.deleteAll(ids);
        }catch (Throwable e){
            ThrowableUtil.throwForeignKeyException(e, "所选代理店中存在部门关联，请取消关联后再试");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
