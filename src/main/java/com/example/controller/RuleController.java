/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import com.example.CommonUtils;
import com.example.entity.Rule;
import com.example.service.RuleService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author deray.wang
 */
@Controller
@RequestMapping(value = "/rule")
public class RuleController {
    @Autowired
    private RuleService ruleService;
//    @RequestMapping(value = {"/index"})
//    public String index(Model model) {
//        List datlist = ruleService.getList();
//        model.addAttribute("datlist", datlist);
//        return "rule/index";
//    }

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

        return "rule/index";
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap list(Rule rule, @RequestParam(value = "pageNumber", required = true) Integer page) {
        ModelMap map = new ModelMap();
//        Integer curpage = page-1;
//        System.out.println("page:" );
        //List<Rule> Lists = ruleService.getList();
        Page<Rule> Lists = ruleService.getPageList(page-1,20);

//        for (Rule list : Lists) {
//            List<Role> rolelist = roleService.selectRoleListByAdminId(list.getUid());
//            list.setRoleList(rolelist);
//        }
        map.put("pageInfo", Lists);
        map.put("queryParam", rule);
        return ReturnUtil.Success("加载成功", map, null);
    }

    @RequestMapping(value = {"/edit"})
    public String edit(Model model) {
        List datlist = ruleService.getList();
        model.addAttribute("datlist", datlist);
        return "rule/edit";
    }
}
