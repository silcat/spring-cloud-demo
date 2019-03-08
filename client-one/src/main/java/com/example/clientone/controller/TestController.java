package com.example.clientone.controller;

import com.example.clientone.mapper.BankMapper;

import com.example.clientone.feginService.ServiceBFeginService;
import com.example.clientone.feginService.ServiceBNoFallbackFeginService;
import com.example.clientone.service.BankService;
import com.example.common.model.Bank;
import com.example.common.model.clientone.query.TestQuery;
import com.example.common.model.clientone.valid.TestQueryValid;
import com.example.demobase.core.Result;
import com.example.demobase.core.ResultCode;
import com.example.demobase.core.ResultGenerator;
import com.example.demobase.exception.DemoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * 请求参数日志及参数校验测试
     * @param testRequestBo
     * @return
     */
    @GetMapping("/get")
    public Result<String> get(@Validated TestQuery testRequestBo){
        Result<String> get = ResultGenerator.genSuccessResult("get");
        return ResultGenerator.genSuccessResult("get");
    }
    @PostMapping("/post")
    public Result<String> post(@Validated(TestQueryValid.Post.class)TestQuery testRequestBo){
        return ResultGenerator.genSuccessResult("post");
    }

    /**
     * 统一异常日志测试
     * @param testRequestBo
     * @return
     */
    @GetMapping("/get/error")
    public Result<String> getError(TestQuery testRequestBo){
        throw new DemoException(ResultCode.SERVER_UNKONW_ERROR);
    }
    @PostMapping("/post/error")
    public Result<String> postError(TestQuery testRequestBo){
        throw new DemoException(ResultCode.SERVER_UNKONW_ERROR);
    }

    /**
     * fegin异常日志测试
     * @param testRequestBo
     * @return
     */
    @PostMapping("/rpc")
    public String rpc(TestQuery testRequestBo){
        String rpc = serviceBFeginService.rpc(testRequestBo);
        return rpc;
    }

    /**
     * 通用mapper测试
     * @param
     * @return
     */
    @PostMapping("/tk")
    public Result<Bank> tk(TestQuery testRequestBo){
        List<Bank> banks = bankMapper.selectAll();
        Bank bank = bankMapper.selectByPrimaryKey(1);
        return ResultGenerator.genSuccessResult(bank);
    }

}
