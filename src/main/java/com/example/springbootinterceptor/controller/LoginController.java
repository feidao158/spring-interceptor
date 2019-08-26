package com.example.springbootinterceptor.controller;

import com.example.springbootinterceptor.pojo.JsonResult;
import com.example.springbootinterceptor.pojo.User;
import com.example.springbootinterceptor.service.UserService;
import com.example.springbootinterceptor.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public JsonResult login(User user) {

        return userService.login(user);
    }
}
