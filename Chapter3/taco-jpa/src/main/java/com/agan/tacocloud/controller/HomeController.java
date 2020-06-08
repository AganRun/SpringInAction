package com.agan.tacocloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller   //改用声明式视图控制器
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";  //返回视图名
    }
}
