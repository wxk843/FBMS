/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.entity;

import static com.oracle.jrockit.jfr.ContentType.Timestamp;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author deray.wang
 */
@Entity
@Table(name="vm_admin")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","groups","auths"})
public class Admin implements UserDetails,Serializable{
    @Id
    @Column(name = "id",columnDefinition="bigint COMMENT 'ID，自动生成'")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min=2, max=30)
    @Column(name = "username")
    private String username;

    private String nickname;
    private String password;
    private String salt;
    private String avatar;

    @NotNull(message = "email不能为空")
    @Column(name = "email")
    private String email;
    private int loginfailure;
    private Timestamp logintime;
    private Timestamp createtime;
    private Timestamp updatetime;
    private String token;
    private String status;
    
    public Admin() {

    }
    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<Group> group;

    public Admin(Admin user) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Basic
    @Column(name = "username",columnDefinition="varchar(150) COMMENT '用户名'")
    public void setUsername(String username) {
        this.username = username;
    }
    @Basic
    @Column(name = "username",columnDefinition="varchar(150) COMMENT '昵称'")
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname(){
        return this.nickname;
    }

    public void setLoginfailure(int loginfailure) {
        this.loginfailure = loginfailure;
    }
    public int getLoginfailure(){
        return this.loginfailure;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSalt(){
        return this.salt;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar(){
        return this.avatar;
    }
    public Timestamp getLogintime() {
            return this.logintime;
    }

    public void setLogintime(Timestamp logintime) {
            this.logintime = logintime;
    }
    @UpdateTimestamp
    @Column(name = "updatetime",columnDefinition="DATETIME COMMENT '最后更新时间'")
    public Timestamp getUpdatetime() {
            return this.updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
            this.updatetime = updatetime;
    }

    @CreationTimestamp
    @Column(name = "createtime",columnDefinition="DATETIME COMMENT '添加时间'")
    public Timestamp getCreatetime() {
            return this.createtime;
    }

    public void setCreatetime(Timestamp createtime) {
            this.createtime = createtime;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

    public List<Group> getGroup() {
        return this.group;
    }

    public void setGroups(List<Group> groups) {
        this.group = group;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        List<Group> groups = this.getGroup();
        if(groups != null){
            for (Group group : groups) {
                //auths.add(new SimpleGrantedAuthority(group.getName()));
                auths.add(new SimpleGrantedAuthority(group.getRules()));
            }
        }
        return auths;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
