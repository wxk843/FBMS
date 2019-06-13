/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import com.example.entity.MyUserDetails;
import com.example.service.MyUserDetailsService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author deray.wang
 */
@Component
public class MyAuthenticationProvider  implements AuthenticationProvider {
    @Autowired
    private MyUserDetailsService userService;

    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        MyUserDetails user = (MyUserDetails) userService.loadUserByUsername(username);
        if(user == null){
            throw new BadCredentialsException("Username not found.");
        }

//         //这里我们还要判断密码是否正确，实际应用中，我们的密码一般都会加密，以Md5加密为例
//         Md5PasswordEncoder md5PasswordEncoder=new Md5PasswordEncoder();
//         //这里第个参数，是salt就是加点盐的意思，这样的好处就是用户的密码如果都是123456，由于盐的不同，密码也是不一样的，就不用怕相同密码泄漏之后，不会批量被破解。
//         String encodePwd=md5PasswordEncoder.encodePassword(password, username);
//         //这里判断密码正确与否
//         if(!user.getPassword().equals(encodePwd))
//         {
//            throw new BadCredentialsException("密码不正确");
//         }
         //这里还可以加一些其他信息的判断，比如用户账号已停用等判断，这里为了方便我接下去的判断，我就不用加密了。


//        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
//        String hashPass = encode.encode(password);
//        if (!password.equals(user.getPassword())) {
//            throw new BadCredentialsException("Wrong password.");
//        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
