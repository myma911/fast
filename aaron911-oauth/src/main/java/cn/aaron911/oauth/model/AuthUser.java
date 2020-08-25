package cn.aaron911.oauth.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import cn.aaron911.oauth.enums.AuthUserGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 授权成功后的用户信息，根据授权平台的不同，获取的数据完整性也不同
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
     * 用户第三方系统的唯一id。在调用方集成该组件时，可以用uuid + source唯一确定一个用户
     *
     * @since 1.3.3
     */
    private String uuid;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户网址
     */
    private String blog;
    /**
     * 所在公司
     */
    private String company;
    /**
     * 位置
     */
    private String location;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户备注（各平台中的用户个人介绍）
     */
    private String remark;
    /**
     * 性别
     */
    private AuthUserGender gender;
    /**
     * 用户来源
     */
    private String source;
    /**
     * 用户授权的token信息
     */
    private AuthToken token;
    /**
     * 第三方平台返回的原始用户信息
     */
    private JSONObject rawUserInfo;

}
