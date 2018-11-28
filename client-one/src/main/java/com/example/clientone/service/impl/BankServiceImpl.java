package com.example.clientone.service.impl;

import com.example.clientone.mapper.BankMapper;
import com.example.common.model.clientone.Bank;
import com.example.clientone.service.BankService;
import com.example.common.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/11/28.
 */
@Service
@Transactional
public class BankServiceImpl extends AbstractService<Bank> implements BankService {
    @Resource
    private BankMapper bankMapper;

}
