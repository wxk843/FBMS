/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.service;

import com.example.entity.Admin;
import com.example.entity.Group;
import com.example.repository.GroupRepositoty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author deray.wang
 */
@Service
public class GroupService {
    @Autowired
    private GroupRepositoty groupRepositoty;
    public List<Group> getList() {  
        return groupRepositoty.findAll();  
    }

    public Page<Group> getPageList(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
        //PageHelper.offsetPage(rule.getOffset(), rule.getLimit(), CamelCaseUtil.toUnderlineName(rule.getSort())+" "+rule.getOrder());
        return groupRepositoty.findAll(pageable);
    }
    public Group getGroupById(Long id){
        return groupRepositoty.getOne(id);
    } 
    
    public List<Group> getGroupByPid(Long id){
        return groupRepositoty.findByPid(id);
    }
    
//    public static List<Group> createTreeMenus(List<Group> menus) {
//    List<Group> treeMenus = null;
//    if (null != menus && !menus.isEmpty()) {
//      // 创建根节点
//      Group root = new Group();
//      root.setName("菜单根目录");
// 
//      // 组装Map数据
//      Map<Long, Group> dataMap = new HashMap<Long, Group>();
//      for (Group menu : menus) {
//        dataMap.put(menu.getId(), menu);
//      }
// 
//      // 组装树形结构
//      Set<Entry<Long, Group>> entrySet = dataMap.entrySet();
//      for (Entry<Long, Group> entry : entrySet) {
//        Group menu = entry.getValue();
//        if (null == menu.getPid() || 0 == menu.getPid()) {
//          root.getChildren().add(menu);
//        } else {
//          dataMap.get(menu.getPid()).getChildren().add(menu);
//        }
//      }
// 
//      // 对树形结构进行二叉树排序
//      //root.sortChildren();
//      treeMenus = root.getChildren();
//    }
//    return treeMenus;
//  }

    List<Group> getRoleByUser(Admin user) {
        //System.out.printf("s:%s%n", user.getAuthorities());
        System.out.println("s:"+user.getGroup());
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
