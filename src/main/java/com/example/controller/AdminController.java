/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import com.example.CommonUtils;
import com.example.Msg;
import com.example.annotation.MyLog;
import com.example.entity.Admin;
import com.example.entity.Group;
import com.example.entity.Rule;
import com.example.service.AdminService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.service.GroupService;
import com.example.service.RuleService;
import com.example.util.ReturnUtil;
import io.swagger.annotations.ApiParam;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @author deray.wang
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private GroupService groupService;

    @Autowired
    private RuleService ruleService;

    @RequestMapping(value = {"/index"})
    public String index(Model model) {
        List<Rule> ruleList = ruleService.getListMenu();
        List<Map<String, Object>> list = new ArrayList();
        int i=0;
        for (Rule rule : ruleList) {
            Map<String, Object> test = new HashMap<>();
            test.put("id",String.valueOf(rule.getId()));
            test.put("pid",String.valueOf(rule.getPid()));
            test.put("data",rule);
            list.add(i,test);
            i++;
        }
        List<Map<String, Object>> listMenu = CommonUtils.getTree(list, String.valueOf(0), "id");
        model.addAttribute("listMenu", listMenu);
        return "admin/index";
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap list(Admin user, @RequestParam(value = "pageNumber", required = true) Integer page) {
        ModelMap map = new ModelMap();
//        Integer curpage = page-1;
//        System.out.println("page:" );
        //List<Rule> Lists = ruleService.getList();
        Page<Admin> Lists = adminService.getPageList(page-1,20);

//        for (Rule list : Lists) {
//            List<Role> rolelist = roleService.selectRoleListByAdminId(list.getUid());
//            list.setRoleList(rolelist);
//        }
        map.put("pageInfo", Lists);
        map.put("queryParam", user);
        System.out.println(map );
        return ReturnUtil.Success("加载成功", map, null);
    }

    @MyLog(value = "添加管理员")
    @RequestMapping(value = {"/add"})
    public String add(Model model) {
        model.addAttribute("userInfo", new Admin());
        return "admin/add";
    }

    @MyLog(value = "修改管理员")
    @RequestMapping(value = {"/edit"})
    public String edit(Model model,Long id){
        if(id!=0){
            Admin userInfo = adminService.findById(id);
            model.addAttribute("userInfo", userInfo);
            List groupData = groupService.getList();
            model.addAttribute("groups",groupData);
            System.out.println(groupData);
        }
        return "admin/edit";
    }

    @MyLog(value = "查看管理员")
    @RequestMapping("/view/{id}")
    public Admin view(@PathVariable("id") Long id) {
        Admin user = adminService.findById(id);
        return user;
    }

    @MyLog(value = "添加管理员保存")
    @RequestMapping(value = {"/addsave"},method=RequestMethod.POST)//@RequestBody @Validated
    public String addsave(@Valid @ModelAttribute Admin user, BindingResult rs) {
        if(rs.hasErrors()){
            for (ObjectError error : rs.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            return "admin/add";
        }

        String password = user.getPassword();
        System.out.println("userName is:"+user.getUsername());
        System.out.println("password is:"+user.getPassword());
        System.out.println("email is:"+user.getEmail());
        BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
        String hashPass = encode.encode(password);
        user.setPassword(hashPass);
        String newpass = user.getPassword();
        user.setStatus("normal");
        System.out.println("password is:"+newpass);
        adminService.creatUser(user);
        return "admin/index";
    }

    @RequestMapping(value = {"/remote"},method=RequestMethod.GET)
    @ResponseBody
    public Map remote(@RequestParam(value = "username", required = false) String username) {
        System.out.println(username);
        Admin admin = adminService.findByUsername(username);
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        if (admin!=null){
            map.put("valid",false);
        }else {
            map.put("valid",true);
        }
        return map;
    }
}
