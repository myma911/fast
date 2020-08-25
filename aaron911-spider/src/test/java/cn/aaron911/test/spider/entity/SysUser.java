package cn.aaron911.test.spider.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUser {
	private String id;
    private String username;
    private String password;
    private String nickname;
    private String mobile;
    private String email;
    private String qq;
    private Date birthday;
    private String avatar;
    private String userType;
    private String company;
    private String blog;
    private String location;
    private String source;
    private String uuid;
    private Integer privacy;
    private Integer notification;
    private Integer score;
    private Integer experience;
    private String regIp;
    private String lastLoginIp;
    private Date lastLoginTime;
    private Integer loginCount;
    private String remark;
    private Integer status;
}
