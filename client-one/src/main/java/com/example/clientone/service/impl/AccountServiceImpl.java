package com.example.clientone.service.impl;

import com.example.clientone.mapper.BankMapper;
import com.example.clientone.service.AccountService;
import com.example.clientone.service.BankService;
import com.example.common.model.Bank;
import com.example.common.model.clientone.dto.AccountTbl;
import com.example.democore.configuration.mybatis.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/11/28.
 */
@Service
public class AccountServiceImpl extends AbstractService<AccountTbl> implements AccountService {
    @Resource
    private BankMapper bankMapper;

}
