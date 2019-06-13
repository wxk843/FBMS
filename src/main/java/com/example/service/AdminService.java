/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.service;

import com.example.entity.Admin;
import com.example.entity.Group;
import com.example.entity.MyUserDetails;
import com.example.repository.AdminRepository;
import java.util.List;

import com.example.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author deray.wang
 */
@Service
public class AdminService{
    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllUser() {  
        return adminRepository.findAll();  
    }

    public Admin findById(Long id){
        Admin admin = null;
        try{
            admin = adminRepository.findById(id);
        }catch (Exception e){
            throw new UsernameNotFoundException("用户不存在");
        }
        return admin;
    }

    public Page<Admin> getPageList(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
        //PageHelper.offsetPage(rule.getOffset(), rule.getLimit(), CamelCaseUtil.toUnderlineName(rule.getSort())+" "+rule.getOrder());
        return adminRepository.findAll(pageable);
    }
    
    /*
    * 创建用户
    */
    public Admin creatUser(Admin user){
        user.setCreatetime(TimeUtil.nowTimestamp());
        return adminRepository.save(user);
    } 

    /*
    * 删除用户
    */
    public void delete(Long id) {  
        adminRepository.delete(id);  
    }  
    
    /*
    * 修改用户
    */
    public void update(Admin admin) {  
        adminRepository.saveAndFlush(admin);  
    }

    public Admin findByUsername(String userName) {
        Admin user = null;
        try{
            user = adminRepository.findByUsername(userName);
        }catch (Exception e){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }
    
}
