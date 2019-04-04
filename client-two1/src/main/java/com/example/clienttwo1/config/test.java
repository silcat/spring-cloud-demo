package com.example.clienttwo1.config;

import com.alibaba.fescar.spring.annotation.GlobalTransactionScanner;

public class test extends GlobalTransactionScanner {
    public test(String applicationId, String txServiceGroup) {
        super(applicationId, txServiceGroup);
    }
    @Override
    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        if (beanName.equals("org.springframework.cloud.netflix.hystrix.stream.HystrixStreamClient")){
            return bean;
        }

        return super.wrapIfNecessary(bean,beanName,cacheKey);
    }

}
