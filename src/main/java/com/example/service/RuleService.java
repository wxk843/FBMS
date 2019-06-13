/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.service;

import com.example.entity.Rule;
import com.example.repository.RuleRepositoty;
import java.util.List;
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
public class RuleService {
    @Autowired
    private RuleRepositoty ruleRepositoty;
    public List<Rule> getList() {
        return ruleRepositoty.findAll();
    }

    public List<Rule> getListMenu() {
        return ruleRepositoty.getMenu();
    }

    public Page<Rule> getPageList(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "id");
        //PageHelper.offsetPage(rule.getOffset(), rule.getLimit(), CamelCaseUtil.toUnderlineName(rule.getSort())+" "+rule.getOrder());
        return ruleRepositoty.findAll(pageable);
    }
}
