/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import com.example.service.CustomUserService;
import com.example.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author deray.wang
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 登录session key
     */
    public final static String SESSION_KEY = "user";

//    @Autowired
//    private MyAuthenticationProvider provider;//自定义验证
    
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    UserDetailsService customUserService() {
        return new MyUserDetailsService();
    }

    @Override  
    public void configure(WebSecurity web) throws Exception {  
        // 设置不拦截规则  
        web.ignoring().antMatchers(
                "/static/**", 
                "/**/*.jsp",
                "index"
//                "/",
//                "/*.html",
//                "/favicon.ico",
//                "/**/*.html",
//                "/**/*.css",
//                "/**/*.js"
        );  
    }
    @Autowired
    private MyUserDetailsService myUserDetailsService;

   @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(customUserService()).passwordEncoder(new BCryptPasswordEncoder());
        //指定密码加密所使用的加密器为passwordEncoder()
        //需要将密码加密后写入数据库
       auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
       auth.eraseCredentials(false);
   }



    /** 
     * 配置 特殊权限-特殊路径 
     * 配置 任意权限-剩余路径 
     * 配置 登陆页-用户名、密码-登陆失败页-不需要权限 
     */  
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable() //HTTP with Disable CSRF
                .authorizeRequests()//Authorize Request Configuration
                .anyRequest().authenticated()

                // 允许对于网站静态资源的无授权访问
                .antMatchers( "/login",
                        "/api/**",
                        "/**/heapdump",
                        "/**/loggers",
                        //"/**/liquibase",
                        //"/**/logfile",
                        //"/**/flyway",
                        "/swagger-ui.html",
                        //"/**/auditevents",
                        "/**/jolokia").permitAll() //放开"/api/**"：为了给被监控端免登录注册并解决Log与Logger冲突
//                .and()
//                .authorizeRequests()
//                .antMatchers("/**").hasRole("USER")
//                .antMatchers("/**").authenticated()
                .and() //Login Form configuration for all others
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login").permitAll()
                //设置默认登录成功跳转页面
                .defaultSuccessUrl("/home")
                .successHandler(loginSuccessHandler()) //登录成功后可使用loginSuccessHandler()存储用户信息，可选。  
                .failureUrl("/login?error=true").permitAll()
                .and() //Logout Form configuration
//                //默认注销行为为logout，可以通过下面的方式来修改
//                .logoutUrl("/custom-logout")
//                //设置注销成功后跳转页面，默认是跳转到登录页面
                .logout()
                .deleteCookies("remove")
                .logoutSuccessUrl("/login").permitAll()
                .invalidateHttpSession(true)  
                .and()
                //开启cookie保存用户数据
                .rememberMe()
                //设置cookie有效期
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                //设置cookie的私钥
 //               .key("")
                .and()
                .httpBasic();


    }
    @Bean  
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }


}
