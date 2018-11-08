package com.example.servicea.service;
import com.example.common.model.Bank;
import com.example.servicea.mapper.BankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankServiceImpl implements BankService {

    @Autowired
     private BankMapper bankMapper;

     @Override
     public Bank test() {
         bankMapper.selectByPrimaryKey(1);
         List<Bank> banks = bankMapper.selectAll();

         return bankMapper.selectByPrimaryKey(1);
     }
}
