package com.weikaiyu.security.distributed.order.controller;

import com.weikaiyu.security.distributed.order.pojo.sysUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
@RequestMapping(value = "order/r")
public class OrderController {

    @GetMapping(value = "/r1")
    @PreAuthorize("hasAuthority('p1')")//拥有p1权限方可访问此url
    public String r1(){
        //获取用户身份信息
        sysUser userDTO = (sysUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDTO.getLogin()+"访问资源1";
    }
    @GetMapping(value = "/r6")
    @PreAuthorize("hasAuthority('p6')")//拥有p1权限方可访问此url
    public String r6(){
        //获取用户身份信息
        return "访问资源r1";
    }

    @GetMapping(value = "/test")
    public String order(){
        //获取用户身份信息
        return "test gateway";
    }
}