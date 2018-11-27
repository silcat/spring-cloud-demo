package com.example.client-one.service.impl;

import com.example.client-one.orm.mapper.BankMapper;
import com.example.client-one.orm.model.Bank;
import com.example.client-one.service.BankService;
import com.example.client-one.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/11/26.
 */
@Service
@Transactional
public class BankServiceImpl extends AbstractService<Bank> implements BankService {
    @Resource
    private BankMapper bankMapper;

}
