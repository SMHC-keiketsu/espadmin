package me.smhc.modules.master.rest;

import me.smhc.aop.log.Log;
import me.smhc.modules.master.domain.ExchangeRate;
import me.smhc.modules.master.service.ExchangeRateService;
import me.smhc.modules.master.service.dto.ExchangeRateQueryCriteria;
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
@Api(tags = "rate管理")
@RestController
@RequestMapping("/api/exchangeRate")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('exchangeRate:list')")
    public void download(HttpServletResponse response, ExchangeRateQueryCriteria criteria) throws IOException {
        exchangeRateService.download(exchangeRateService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询rate")
    @ApiOperation("查询rate")
    @PreAuthorize("@el.check('exchangeRate:list')")
    public ResponseEntity<Object> getExchangeRates(ExchangeRateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(exchangeRateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增rate")
    @ApiOperation("新增rate")
    @PreAuthorize("@el.check('exchangeRate:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ExchangeRate resources){
        return new ResponseEntity<>(exchangeRateService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改rate")
    @ApiOperation("修改rate")
    @PreAuthorize("@el.check('exchangeRate:edit')")
    public ResponseEntity<Object> update(@Validated(ExchangeRate.Update.class) @RequestBody ExchangeRate resources){
        exchangeRateService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除rate")
    @ApiOperation("删除rate")
    @PreAuthorize("@el.check('exchangeRate:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        exchangeRateService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
