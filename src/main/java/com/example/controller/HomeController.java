/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import com.example.Msg;
import com.example.WebSecurityConfig;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;

import java.util.Arrays;
import java.util.List;

import com.example.annotation.MyLog;
import com.example.entity.Menu;
import com.example.service.MenuService;
import com.example.util.MenuTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import com.example.entity.Admin;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author deray.wang
 */
@Controller
public class HomeController {
    @Autowired
    private MenuService menuService;
    //@RequestMapping("/")
    @RequestMapping("/home")
    public String index(Model model) {
        //model.addAttribute("account", account);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext() .getAuthentication() .getPrincipal();  
        // 登录名
        System.out.println("Username:" + userDetails.getUsername() );

        System.out.println(userDetails.getAuthorities());

        System.out.println(menuService.getMenuList());
        List arr = menuService.getMenuList();

        arr.stream().forEach(s -> System.out.println(s));
        return "index";
    }
    /**
     * 登录页面
     * @param
     * @return
     */
    @MyLog(value = "用户登录")  //这里添加了AOP的自定义注解
    @RequestMapping(value = "/")
    public String login(Model model, Admin user, @RequestParam(value = "error",required = false) boolean error , @RequestParam(value = "logout",required = false) boolean logout, HttpServletRequest request){
        model.addAttribute(user);
        //如果已经登陆跳转到个人首页
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null&&!authentication.getPrincipal().equals("anonymousUser")&&authentication.isAuthenticated())
            return "index";
        if(error==true)
            model.addAttribute("error",error);
        if(logout==true)
            model.addAttribute("logout",logout);
        return "login";
    }
    @RequestMapping("/zeroException")
    public void zeroException(){
        int i = 1024/0;
    }

    private List<Menu> getMenu(Admin admin) {

//        if(admin.getIsSystem() == 1){
//            menuLists = menuService.selectAllMenu();
//        }else{
//            menuLists = menuService.selectMenuByAdminId(admin.getUid());
//        }
        List menuLists = menuService.getMenuList();
        MenuTreeUtil menuTreeUtil = new MenuTreeUtil(menuLists);
        return menuTreeUtil.buildTreeGrid();
    }
}
