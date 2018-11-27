package com.example.clientone.service.impl;

import com.example.clientone.orm.mapper.BankMapper;
import com.example.clientone.orm.model.Bank;
import com.example.clientone.service.BankService;
import com.example.clientone.core.AbstractService;
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
