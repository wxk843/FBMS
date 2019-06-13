/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.service;

import com.example.entity.Admin;
import com.example.entity.Group;
import com.example.entity.MyUserDetails;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.example.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author deray.wang
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminService adminService;

    //@Resource(name = "GroupServiceImpl")
    private GroupService groupService;
    @Autowired
    AdminRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Admin user;
        try {
            user = adminService.findByUsername(userName);
        } catch (Exception e) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        if(user == null){
            throw new BadCredentialsException("用户名不存在");
        } else {
            try {
                List<Group> roles = user.getGroup();//GrantedAuthority是security提供的权限类，
                System.out.println(roles);
//                return new MyUserDetails(user, roles);

                //List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();//GrantedAuthority是security提供的权限类，
//                getRoles(user,list);//获取角色，放到list里面
//
//                org.springframework.security.core.userdetails.User auth_user = new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),list);//返回包括权限角色的User给security
//                System.out.printf("s:%s%n", user);
                return user;
            } catch (Exception e) {
                throw new UsernameNotFoundException("user role select fail");
            }
        }
    }
}
