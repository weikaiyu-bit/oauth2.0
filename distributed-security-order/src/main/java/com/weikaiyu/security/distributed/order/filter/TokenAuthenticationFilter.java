package com.weikaiyu.security.distributed.order.filter;

import com.alibaba.fastjson.JSON;
import com.weikaiyu.security.distributed.order.pojo.sysUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 **/
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //解析出头中的token
        if(authentication instanceof OAuth2Authentication){
            OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
            Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
            //取出用户身份信息
            sysUser sysUser = JSON.parseObject(userAuthentication.getName(), sysUser.class);
            //取出用户权限
            List<String> authoritieList = new ArrayList<>();
            //从userAuthentication取出权限，放在authoritieList
            userAuthentication.getAuthorities().stream().forEach(c->authoritieList.add(((GrantedAuthority) c).getAuthority()));
            /*转换为数组*/
            String[] authorities =new String[authoritieList.size()];
            authoritieList.toArray(authorities);
            //将用户信息和权限填充 到用户身份token对象中
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(sysUser,null, AuthorityUtils.createAuthorityList(authorities));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            //将authenticationToken填充到安全上下文
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }

}
