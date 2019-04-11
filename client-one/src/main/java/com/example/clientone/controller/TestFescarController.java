package com.example.clientone.controller;

import com.alibaba.fescar.spring.annotation.GlobalTransactional;
import com.example.clientone.feginService.ServiceBFeginService;
import com.example.clientone.mapper.BankMapper;
import com.example.clientone.service.AccountService;
import com.example.clientone.service.BankService;
import com.example.common.model.Bank;
import com.example.common.model.clientone.dto.AccountTbl;
import com.example.common.model.clienttwo1.dto.StorageTbl;
import com.example.demobase.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private BankMapper bankService;

    @GlobalTransactional(timeoutMills = 300000, name = "spring-cloud-demo-tx")
    @GetMapping(value = "/fescar/feign")
    public String feign(@RequestParam String uid) {
        StorageTbl storageTbl = new StorageTbl();
        storageTbl.setCommodityCode(COMMODITY_CODE);
        storageTbl.setCount(ORDER_COUNT);
        storageTbl.setId(1);
        Result<String> echo = serviceBFeginService.echo(storageTbl);
        AccountTbl accountTbl = new AccountTbl();
        accountTbl.setMoney(10);
        accountTbl.setUserId(uid);
        accountTblService.save(accountTbl);

        return SUCCESS;

    }

}
