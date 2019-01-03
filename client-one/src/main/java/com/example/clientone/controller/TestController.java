package com.example.clientone.controller;

import com.example.clientone.configuration.DynamicConfiguration;
import com.example.clientone.mapper.BankMapper;
import com.example.common.bo.TestRequestBo;

import com.example.clientone.feginService.ServiceBFeginService;
import com.example.clientone.feginService.ServiceBNoFallbackFeginService;
import com.example.clientone.service.BankService;
import com.example.common.model.clientone.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RefreshScope
public class TestController {

    @Autowired
    private DynamicConfiguration dynamicConfiguration;
    @Autowired
    private ServiceBFeginService serviceBFeginService;
    @Autowired
    private BankService bankService;
    @Autowired
    private BankMapper bankMapper;
    @Autowired
    private ServiceBNoFallbackFeginService serviceBNoFallbackFeginService;

    @PostMapping("/rpc")
    public String rpc(TestRequestBo testRequestBo){
        String rpc = serviceBFeginService.rpc(testRequestBo);
        return rpc;
    }

    @PostMapping("/fallback")
    public String fallback(){
        return "";
    }

    @PostMapping("/config")
    public String config(){
        return dynamicConfiguration.getTest();
    }

    @PostMapping("/tk")
    public Bank tk(){
        List<Bank> banks = bankMapper.selectAll();
        Bank bank = bankMapper.selectByPrimaryKey(1);
        return null;
    }

}
