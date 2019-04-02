package com.example.clienttwo1.service.impl;


import com.example.clienttwo1.maaper.OrderTblMapper;
import com.example.clienttwo1.service.OrderTblService;
import com.example.common.model.clienttwo1.dto.OrderTbl;
import com.example.democore.configuration.mybatis.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/03/28.
 */
@Service
public class OrderTblServiceImpl extends AbstractService<OrderTbl> implements OrderTblService {
    @Resource
    private OrderTblMapper orderTblMapper;

}
