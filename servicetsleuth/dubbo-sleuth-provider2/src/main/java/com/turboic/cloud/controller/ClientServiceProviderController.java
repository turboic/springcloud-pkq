package com.turboic.cloud.controller;

import com.turboic.cloud.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liebe
 */
@RestController
@RequestMapping("/provider")
public class ClientServiceProviderController {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceProviderController.class);
    private final ManagerService managerService;

    public ClientServiceProviderController(ManagerService managerService) {
        this.managerService = managerService;
    }
    @RequestMapping("/selectById/{id}")
    public String selectById(@PathVariable(value="id") String id){
        Integer ids = Integer.parseInt(id);
        managerService.executeTest(ids);
        return "call request";
    }
}
