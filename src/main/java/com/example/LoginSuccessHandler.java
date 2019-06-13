/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

/**
 *
 * @author deray.wang
 */
import com.example.entity.Admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Set;
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import com.example.util.IpAdrressUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;  
  
  
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {  
    @Override    
    public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response, Authentication authentication) throws IOException, ServletException {    
        //获得授权后可得到用户信息   可使用SUserService进行数据库操作  
        Admin userDetails = (Admin)authentication.getPrincipal();    
       /* Set<SysRole> roles = userDetails.getSysRoles();*/  
        //输出登录提示信息    
        System.out.println("管理员 " + userDetails.getUsername() + " 登录");    
          
        System.out.println("IP :"+ IpAdrressUtil.getIpAdrress(request));
                
        super.onAuthenticationSuccess(request, response, authentication);    
    }

}  
