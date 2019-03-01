package com.example.clientone.controller;

import com.example.clientone.mapper.BankMapper;

import com.example.clientone.feginService.ServiceBFeginService;
import com.example.clientone.feginService.ServiceBNoFallbackFeginService;
import com.example.clientone.service.BankService;
import com.example.common.model.Bank;
import com.example.common.model.clientone.query.TestQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RefreshScope
public class TestController {

    @Autowired
    private ServiceBFeginService serviceBFeginService;
    @Autowired
    private BankService bankService;
    @Autowired
    private BankMapper bankMapper;
    @Autowired
    private ServiceBNoFallbackFeginService serviceBNoFallbackFeginService;

    @PostMapping("/rpc")
    public String rpc(TestQuery testRequestBo){
        String rpc = serviceBFeginService.rpc(testRequestBo);
        return rpc;
    }

    @PostMapping("/fallback")
    public String fallback(){
        return "test";
    }


    @PostMapping("/tk")
    public Bank tk(){
        List<Bank> banks = bankMapper.selectAll();
        Bank bank = bankMapper.selectByPrimaryKey(1);
        return null;
    }

}
