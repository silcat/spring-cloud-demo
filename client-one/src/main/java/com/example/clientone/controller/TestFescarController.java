package com.example.clientone.controller;

import com.alibaba.fescar.spring.annotation.GlobalTransactional;
import com.example.clientone.feginService.ServiceBFeginService;
import com.example.clientone.service.AccountService;
import com.example.common.model.clientone.dto.AccountTbl;

import com.example.common.model.clienttwo1.dto.StorageTbl;
import com.example.demobase.core.Result;
import com.example.demobase.core.ResultCode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/tx")
@RefreshScope
public class TestFescarController {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";
    private static final String USER_ID = "U100001";
    private static final String COMMODITY_CODE = "C00321";
    private static final int ORDER_COUNT = 2;

    @Autowired
    private ServiceBFeginService serviceBFeginService;
    @Autowired
    private AccountService accountTblService;

    @GlobalTransactional(timeoutMills = 300000, name = "spring-cloud-demo-tx")
    @GetMapping(value = "/fescar/feign")
    public String feign() {
        AccountTbl accountTbl = new AccountTbl();
        accountTbl.setMoney(10);
        accountTbl.setUserId("1");
        accountTblService.save(accountTbl);
        StorageTbl storageTbl = new StorageTbl();
        storageTbl.setCommodityCode(COMMODITY_CODE);
        storageTbl.setCount(ORDER_COUNT);
        Result<String> echo = serviceBFeginService.echo(storageTbl);
        if (echo.getCode()!= ResultCode.SUCCESS.code) {
            throw new RuntimeException();
        }
        return SUCCESS;

    }

}
