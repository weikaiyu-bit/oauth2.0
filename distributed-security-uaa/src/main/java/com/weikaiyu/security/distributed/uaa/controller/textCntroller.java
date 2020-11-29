package com.weikaiyu.security.distributed.uaa.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uaa")
public class textCntroller {

    @RequestMapping("/login-success")
    public String login() {
//        用户登录后通过SecurityContextHolder.getContext().getAuthentication();获取用户信息
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//        Object credentials = authentication.getCredentials();
//        Object details = authentication.getDetails();
//        System.out.println("principal"+principal);
//        System.out.println("credentials"+credentials);
//        System.out.println("details"+details);

        return getUserInfo()+"登录成功";
    }

    private String getUserInfo() {
        String username=null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if(principal instanceof org.springframework.security.core.userdetails.UserDetails ){

            UserDetails userDetails=(UserDetails)principal;
             username = userDetails.getUsername();
        }else{
            username=principal.toString();
        }
        return username;
    }


    @GetMapping("/r/r1")
    public String r1() {
        return getUserInfo()+"访问了r/r1";
    }
    @RequestMapping("/r/r2")
    public String r2() {
        return getUserInfo()+"访问了r/r2";
    }
}
