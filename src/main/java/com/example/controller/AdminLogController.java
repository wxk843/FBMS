/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import com.example.CommonUtils;
import com.example.annotation.MyLog;
import com.example.entity.Admin;
import com.example.entity.Rule;
import com.example.entity.SysLog;
import com.example.service.AdminService;
import com.example.service.GroupService;
import com.example.service.RuleService;
import com.example.service.SysLogService;
import com.example.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author deray.wang
 */
@Controller
@RequestMapping(value = "/adminlog")
public class AdminLogController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private GroupService groupService;

    @Autowired
    private RuleService ruleService;
    @Autowired
    private SysLogService sysLogService;

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
        return "adminlog/index";
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap list(SysLog sysLog, @RequestParam(value = "pageNumber", required = true) Integer page) {
        ModelMap map = new ModelMap();
//        Integer curpage = page-1;
//        System.out.println("page:" );
        //List<Rule> Lists = ruleService.getList();
        Page<SysLog> Lists = sysLogService.getPageList(page-1,20);

//        for (Rule list : Lists) {
//            List<Role> rolelist = roleService.selectRoleListByAdminId(list.getUid());
//            list.setRoleList(rolelist);
//        }
        map.put("pageInfo", Lists);
        map.put("queryParam", sysLog);
        System.out.println(map );
        return ReturnUtil.Success("加载成功", map, null);
    }

    @RequestMapping("/view/{id}")
    public Admin view(@PathVariable("id") Long id) {
        Admin user = adminService.findById(id);
        return user;
    }

    @MyLog(value = "删除管理员日志")
    @RequestMapping("/{id}")
    @ResponseBody
    public void del(@PathVariable("id") Long id) {
        sysLogService.delete(id);
    }

}
