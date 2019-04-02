package com.example.clienttwo1.controller;

import com.example.clienttwo1.service.StorageTblService;
import com.example.common.model.clienttwo1.dto.StorageTbl;
import com.example.demobase.core.Result;
import com.example.demobase.core.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StorageTblService storageTblService;

    @PostMapping(value = "/update")
    public Result<String> echo(StorageTbl storageTbl) {
        storageTblService.update(storageTbl);
        return ResultGenerator.genSuccessResult();
    }
}
