package com.example.clienttwo1.service.impl;

import com.example.clienttwo1.maaper.StorageTblMapper;
import com.example.clienttwo1.service.StorageTblService;
import com.example.common.model.clienttwo1.dto.StorageTbl;
import com.example.democore.configuration.mybatis.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/03/28.
 */
@Service
public class StorageTblServiceImpl extends AbstractService<StorageTbl> implements StorageTblService {
    @Resource
    private StorageTblMapper storageTblMapper;

}
