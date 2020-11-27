package com.weikaiyu.security.distributed.uaa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/admin/register")
public class RegisterController {

    @GetMapping("/phoneRegister")
    public String loginredirect(){
        return "手机号注册成功";
    }

}
