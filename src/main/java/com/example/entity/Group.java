/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author deray.wang
 */
@Entity
@Table(name="vm_group")
@ApiModel(value = "组表头", description = "vm_group")
//@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Group {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    //父ID
    @ApiModelProperty(value = "父ID")
    @Column(name = "pid")
    private Long pid;

    @Column(name = "name")
    private String name;

    @Column(name = "rules")
    private String rules;
    @Column(name = "createtime")
    private int createtime;
    @Column(name = "updatetime")
    private int updatetime;
    //状态
    @Column(name = "status")
    private String status;
    @Transient
    public List nodes = new ArrayList();
    /**
    * 子菜单
    */
    @Transient
    public List<Group> children = new ArrayList<Group>();

    /**
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the pid
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the rules
     */
    public String getRules() {
        return rules;
    }

    /**
     * @param rules the rules to set
     */
    public void setRules(String rules) {
        this.rules = rules;
    }

    /**
     * @return the createtime
     */
    public int getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime the createtime to set
     */
    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    /**
     * @return the updatetime
     */
    public int getUpdatetime() {
        return updatetime;
    }

    /**
     * @param updatetime the updatetime to set
     */
    public void setUpdatetime(int updatetime) {
        this.updatetime = updatetime;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    } 
    
    public List getNodes(){
        return this.nodes;
    }
    
}
