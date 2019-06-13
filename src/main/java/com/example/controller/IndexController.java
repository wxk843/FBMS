package com.example.controller;

import com.example.CommonUtils;
import com.example.entity.Group;
import com.example.entity.Rule;
import com.example.service.GroupService;
import com.example.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @author deray.wang
 * @date 2017/12/28 15:35
 */
@Controller
public class IndexController {
    @Autowired
    private RuleService ruleService;

    @RequestMapping("/index")
    public String index(Model model) {
        //model.addAttribute("account", account);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext() .getAuthentication() .getPrincipal();
        // 登录名
        System.out.println("Username:" + userDetails.getUsername() );

        System.out.println(userDetails.getAuthorities());
        List<String> authorities = new ArrayList<String>();
        for(GrantedAuthority authority : userDetails.getAuthorities()) {
            authorities.add(authority.getAuthority());
        }

        List<Rule> ruleList = ruleService.getListMenu();
        List<Map<String, Object>> list = new ArrayList();
        int i=0;
        for (Rule rule : ruleList) {
                Map<String, Object> test = new HashMap<>();
                test.put("id", String.valueOf(rule.getId()));
                test.put("pid", String.valueOf(rule.getPid()));
                test.put("data", rule);
                list.add(i, test);
                i++;
        }
        List<Map<String, Object>> listMenu = CommonUtils.getTree(list, String.valueOf(0), "id");
        for (Map<String, Object> map : listMenu) {
            if (authorities.contains("*")) {
                System.out.println("ccc");
            } else {
                String ruleId = String.valueOf(map.get("id"));
                System.out.println(ruleId);
                if (authorities.contains(ruleId)) {
                    //List<Map<String, Object>> maptest = map.get("children");
                    System.out.println(map.get("children"));
//                    for (Map<String, Object> map : maptest) {
//
//                    }
                }else {
                    map.remove(ruleId);
                }
            }
        }
        System.out.println(listMenu);
        model.addAttribute("listMenu", listMenu);

        return "index/index";
    }
}
