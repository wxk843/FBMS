/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import com.example.CommonUtils;
import com.example.entity.Group;
import com.example.entity.Rule;
import com.example.service.GroupService;
import com.example.service.RuleService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author deray.wang
 */
//@Api(value = "API - RoleController", description = "用户组管理")
@Controller
@RequestMapping(value = "/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private RuleService ruleService;

//    @ApiOperation(value = "查询用户组接口", notes = "查询用户组，返回所有的用户组", response = String.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
//            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
//            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
//            @ApiResponse(code = 404, message = "服务器找不到给定的资源"),
//            @ApiResponse(code = 500, message = "服务器不能完成请求")}
//    )
//    @ResponseBody
    @RequestMapping(value = {"/index"}, method = {RequestMethod.GET})
    public String index(Model model) {
        List datlist = groupService.getList();
        model.addAttribute("datlist", datlist);

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
        return "role/index";
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ModelMap list(Group role, @RequestParam(value = "pageNumber", required = true) Integer page) {
        ModelMap map = new ModelMap();
//        Integer curpage = page-1;
//        System.out.println("page:" );
        //List<Rule> Lists = ruleService.getList();
        Page<Group> Lists = groupService.getPageList(page-1,20);

//        for (Rule list : Lists) {
//            List<Role> rolelist = roleService.selectRoleListByAdminId(list.getUid());
//            list.setRoleList(rolelist);
//        }
        map.put("pageInfo", Lists);
        map.put("queryParam", role);
        System.out.println(map );
        return ReturnUtil.Success("加载成功", map, null);
    }
//    public String index(Model model) {
//        System.out.println(groupService.getList());
//        List<Group> groupList = groupService.getList();
////        Map<Long, Group> groupMap = new HashMap<>();
////        for (Group group : groupList) {
////            groupMap.put(group.getId(), group);
////        }
//        //Map<Long, Group> appleMap = groupList.stream().collect(Collectors.toMap(Group::getId, a -> a,(k1, k2)->k1));
//
//
////        for (Group group : groupList) {
////            //test.put(String.valueOf(group.getId()), group);
////            test.put("id",String.valueOf(group.getId()));
////            test.put("pid",String.valueOf(group.getPid()));
////        }
//        //System.out.println(groupList);
//        List<Map<String, Object>> list = new ArrayList();
//        int i=0;
//        for (Group group : groupList) {
//            Map<String, Object> test = new HashMap<>();
//            //test.put(String.valueOf(group.getId()), group);
//            test.put("id",String.valueOf(group.getId()));
//            test.put("pid",String.valueOf(group.getPid()));
//            test.put("data",group);
//            list.add(i,test);
//            i++;
//        }
//        //System.out.println(list);
//
//        List<Map<String, Object>> ddd = CommonUtils.getTree(list, String.valueOf(0), "id");
//        System.out.println(ddd);
//        //System.out.println(CommonUtils.getTree(list, null, "code"));
//        //System.out.println(JsonUtils.javaToJson(recursiveTree(1)));
//        model.addAttribute("datlist", ddd);
//
////        Group group = recursiveTree((long) 1);
////        System.out.println(group);
////
////        //1、使用JSONObject
////        JSONObject json = JSONObject.fromObject(group);
////        //2、使用JSONArray
////        JSONArray array=JSONArray.fromObject(group);
////
////        String strJson=json.toString();
////        String strArray=array.toString();
////
////        System.out.println("strJson:"+strJson);
////        System.out.println("strArray:"+strArray);
//        return "role/index";
//    }
    
    
    /**
    * 递归算法解析成树形结构
    *
    * @param id
    * @return
    * @author jiqinlin
    */
    public Group recursiveTree(Long id) {
        //根据cid获取节点对象(SELECT * FROM tb_tree t WHERE t.cid=?)
        Group node = groupService.getGroupById(id);

        //查询cid下的所有子节点(SELECT * FROM tb_tree t WHERE t.pid=?)
        List<Group> childTreeNodes = groupService.getGroupByPid(id);

        //遍历子节点
        for(Group child : childTreeNodes){
            Group n = recursiveTree(child.getId()); //递归
            //System.out.println(n);
            node.nodes.add(n);
        }

        return node;
    }

    @RequestMapping(value = {"/add"})
    public String add(Model model) {
        return "role/add";
    }

    @RequestMapping(value = {"/edit"})
    public String edit(Model model,Long id){
        if(id!=0){
            //Admin userInfo = adminService.findById(id);
            //model.addAttribute("userInfo", userInfo);
            List groupData = groupService.getList();
            model.addAttribute("groups",groupData);
            System.out.println(groupData);
        }

        return "role/edit";

    }
}
