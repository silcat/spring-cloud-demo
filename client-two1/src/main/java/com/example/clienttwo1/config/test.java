package com.example.clienttwo1.config;

import com.alibaba.fescar.spring.annotation.GlobalTransactionScanner;

import java.util.Optional;

public class test extends GlobalTransactionScanner {
    public test(String applicationId, String txServiceGroup) {
        super(applicationId, txServiceGroup);
    }
    @Override
    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        if (!Optional.ofNullable(bean).isPresent()){
            return null;
        }
        return super.wrapIfNecessary(bean,beanName,cacheKey);

    }

}
