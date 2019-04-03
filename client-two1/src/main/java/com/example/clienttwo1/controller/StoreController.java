package com.example.clienttwo1.controller;

import com.alibaba.fescar.core.context.RootContext;
import com.example.clienttwo1.service.StorageTblService;
import com.example.common.model.clienttwo1.dto.StorageTbl;
import com.example.demobase.core.Result;
import com.example.demobase.core.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class StoreController {
    @Autowired
    private StorageTblService storageTblService;

    @PostMapping(value = "/store/update")
    public Result<String> echo(@RequestBody StorageTbl storageTbl) {
        log.info("Storage Service Begin ... xid: " + RootContext.getXID());
        storageTblService.update(storageTbl);
        storageTblService.updateStore(storageTbl);
        return ResultGenerator.genSuccessResult();
    }
}
