/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.repository;

import com.example.entity.Group;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author deray.wang
 */
public interface GroupRepositoty extends JpaRepository<Group,Long>{
    @Query("select t from Group t where t.pid = ?1")
    List<Group> findByPid(Long id);
}
