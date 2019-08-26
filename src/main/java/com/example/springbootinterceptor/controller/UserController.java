package com.example.springbootinterceptor.controller;

import com.example.springbootinterceptor.pojo.JsonResult;
import com.example.springbootinterceptor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/all")
    @ResponseBody
    public JsonResult findAllUserInfo(){
        return userService.findAllUserInfo();
    }
}
