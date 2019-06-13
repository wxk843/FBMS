package com.example.service;

import com.example.entity.Rule;
import com.example.repository.RuleRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author deray.wang
 * @date 2018/1/3 14:12
 */
@Service
public class MenuService {
    @Autowired
    private RuleRepositoty ruleRepositoty;

    public List<Rule> getMenuList(){
        return ruleRepositoty.getMenu();
    }
}
