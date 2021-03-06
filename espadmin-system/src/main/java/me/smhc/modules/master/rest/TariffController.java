package me.smhc.modules.master.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import me.smhc.modules.master.service.dto.TariffDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.smhc.aop.log.Log;
import me.smhc.modules.master.domain.Tariff;
import me.smhc.modules.master.service.TariffService;
import me.smhc.modules.master.service.dto.TariffQueryCriteria;

/**
* @author 布和
* @date 2020-03-17
*/
@Api(tags = "tariff管理")
@RestController
@RequestMapping("/api/tariff")
public class TariffController {

    private final TariffService tariffService;

    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tariff:list')")
    public void download(HttpServletResponse response, TariffQueryCriteria criteria) throws IOException {
        tariffService.download(tariffService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询tariff")
    @ApiOperation("查询tariff")
    @PreAuthorize("@el.check('tariff:list')")
    public ResponseEntity<Object> getTariffs(TariffQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(tariffService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tariff")
    @ApiOperation("新增tariff")
    @PreAuthorize("@el.check('tariff:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Tariff resources){
        return new ResponseEntity<>(tariffService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tariff")
    @ApiOperation("修改tariff")
    @PreAuthorize("@el.check('tariff:edit')")
    public ResponseEntity<Object> update(@Validated(Tariff.Update.class) @RequestBody Tariff resources){
        tariffService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除tariff")
    @ApiOperation("删除tariff")
    @PreAuthorize("@el.check('tariff:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        tariffService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/checkGovernmentCode")
    @Log("查询governmentCode")
    @ApiOperation("查询governmentCode")
    @PreAuthorize("@el.check('tariff:list')")
    public ResponseEntity<Object> checkGovernmentCode(String governmentCode, Long id) {
        boolean result = tariffService.findByGovernmentCodeAndId(governmentCode, id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
