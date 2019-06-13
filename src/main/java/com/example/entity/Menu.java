/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 *
 * @author deray.wang
 */
public class Menu {
    @Id
    @GeneratedValue
    private Long id;
    private String type;
    private String pid;
    private String name;
    private String title;
    private String icon;
    private String conditions;
    private String remark;
    private int ismenu;
    private int createtime;
    private int updatetime;
    private int weigh;
    private String status;

    @Transient
    private List<Menu> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the pid
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid the pid to set
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the conditions
     */
    public String getConditions() {
        return conditions;
    }

    /**
     * @param conditions the conditions to set
     */
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the ismenu
     */
    public int getIsmenu() {
        return ismenu;
    }

    /**
     * @param ismenu the ismenu to set
     */
    public void setIsmenu(int ismenu) {
        this.ismenu = ismenu;
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
     * @return the weigh
     */
    public int getWeigh() {
        return weigh;
    }

    /**
     * @param weigh the weigh to set
     */
    public void setWeigh(int weigh) {
        this.weigh = weigh;
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

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}
