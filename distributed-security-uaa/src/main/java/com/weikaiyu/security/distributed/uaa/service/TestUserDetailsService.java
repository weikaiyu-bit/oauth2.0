package com.weikaiyu.security.distributed.uaa.service;

import com.weikaiyu.security.distributed.uaa.dao.PermissionRepository;
import com.weikaiyu.security.distributed.uaa.dao.UserRepository;
import com.weikaiyu.security.distributed.uaa.pojo.permission;
import com.weikaiyu.security.distributed.uaa.pojo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 UserDetailsService，
 * 1、这个类会拿到前端输入的用户名
 * 2、拿到用户名后，开发者拿到用户名去查询数据库，返回账号密码、
 * 3、将账号密码丢进 User.withUsername(byName.getName()).password(byName.getPassword()).authorities("p1").build();
 * withUsername接收参数为数据库查出的账号 password参数为数据库查出密码  p1为权限
 *
 */
@Service
public class TestUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionRepository permissionRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //这里不应该使用name，如果使用name查询，将会的到一个List集合，不符合逻辑，所以学校采用用户的唯一标识登录（学号）
        user byName = userRepository.findByName(s);
        List<user> all = userRepository.findAll();

        if(byName==null){//这里如果查不到数据，直接就返回null，因为spring security帮我们做了异常抛出
            return null;
        }

//      用户验证通过后 用户信息已经查询出来，得到用户的唯一（自增）id，将自增id拿去做in语句查询，得到用户的角色，拿到角色查出用户的权限
//      拥有什么权限，就能访问什么资源
        List<permission> permission = permissionRepository.findPermission(byName.getId());
        System.out.println("permission"+permission);
        List<String> list=new ArrayList<>();
        for(permission p:permission){
            list.add(p.getName());
            }
            String[] str=new String[list.size()];
        list.toArray(str);
        System.out.println("拥有访问资源"+str);
        UserDetails build = User.withUsername(byName.getName()).password(byName.getPassword()).authorities(str).build();
        return build;
    }
}
