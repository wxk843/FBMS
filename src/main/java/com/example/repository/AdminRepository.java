/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.repository;

import com.example.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author deray.wang
 */
@Repository
public interface AdminRepository  extends JpaRepository<Admin,Long>{
    
     public Admin findByUsername(String username);

     public Admin findById(Long id);
}
