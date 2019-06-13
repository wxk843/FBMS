package com.example.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author deray.wang
 * @date 2019/5/7 16:15
 */
@Entity
@Table(name="vm_admin_log")
@Data
public class SysLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(value="本条记录的唯一标识，主键")
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "管理ID")
    @Column(name = "admin_id")
    private Long adminId;

    @ApiModelProperty(value = "管理员名字")
    @Column(name = "username")
    private String username; //用户名

    @ApiModelProperty(value = "URL")
    @Column(name = "url")
    private String url; //用户名

    @ApiModelProperty(value = "操作")
    @Column(name = "title")
    private String title; //操作

    @ApiModelProperty(value = "内容")
    @Column(name = "content")
    private String content; //方法名

    //private String params; //参数
    @ApiModelProperty(value = "IP")
    @Column(name = "ip")
    private String ip; //ip地址

    @ApiModelProperty(value = "用户浏览器")
    @Column(name = "useragent")
    private String useragent;

    @ApiModelProperty(value = "createtime")
    @Column(name = "createtime")
    private Timestamp createTime;
}
