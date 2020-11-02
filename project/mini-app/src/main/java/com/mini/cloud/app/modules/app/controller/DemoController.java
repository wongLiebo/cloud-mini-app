package com.mini.cloud.app.modules.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/demo")
public class DemoController {


    @GetMapping("getDemoz")
    public String getDemo(ModelMap model){
        model.put("now", new Date());
        model.put("message", "Welcome to BeiJing!");
        return "login";
    }

    @GetMapping("getIndex")
    public String getIndex(ModelMap model){
        return "index";
    }
}
