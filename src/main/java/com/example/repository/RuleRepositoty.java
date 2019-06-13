/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.repository;

import com.example.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * @author deray.wang
 */
public interface RuleRepositoty extends JpaRepository<Rule,Long> {

    @Query("select t from Rule t where t.type = 'menu'")
    List<Rule> getMenu();
}
