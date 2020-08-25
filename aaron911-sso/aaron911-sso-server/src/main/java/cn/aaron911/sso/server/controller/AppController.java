package cn.aaron911.sso.server.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.aaron911.sso.core.entity.ReturnT;
import cn.aaron911.sso.core.login.SsoTokenLoginHelper;
import cn.aaron911.sso.core.store.SsoLoginStore;
import cn.aaron911.sso.core.store.SsoSessionIdHelper;
import cn.aaron911.sso.core.user.SsoUser;
import cn.aaron911.sso.server.core.model.UserInfo;
import cn.aaron911.sso.server.service.UserService;

/**
 * sso server (for app)
 *
 */
@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private UserService userService;


    /**
     * Login
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public ReturnT<String> login(String username, String password) {

        // valid login
        ReturnT<UserInfo> result = userService.findUser(username, password);
        if (result.getCode() != ReturnT.SUCCESS_CODE) {
            return new ReturnT<String>(result.getCode(), result.getMsg());
        }

        // 1、make sso user
        SsoUser ssoUser = new SsoUser();
        ssoUser.setUserid(String.valueOf(result.getData().getUserid()));
        ssoUser.setUsername(result.getData().getUsername());
        ssoUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
        ssoUser.setExpireMinite(SsoLoginStore.getRedisExpireMinite());
        ssoUser.setExpireFreshTime(System.currentTimeMillis());


        // 2、generate sessionId + storeKey
        String sessionId = SsoSessionIdHelper.makeSessionId(ssoUser);

        // 3、login, store storeKey
        SsoTokenLoginHelper.login(sessionId, ssoUser);

        // 4、return sessionId
        return new ReturnT<String>(sessionId);
    }


    /**
     * Logout
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public ReturnT<String> logout(String sessionId) {
        // logout, remove storeKey
        SsoTokenLoginHelper.logout(sessionId);
        return ReturnT.SUCCESS;
    }

    /**
     * logincheck
     *
     * @param sessionId
     * @return
     */
    @RequestMapping("/logincheck")
    @ResponseBody
    public ReturnT<SsoUser> logincheck(String sessionId) {

        // logout
    	SsoUser ssoUser = SsoTokenLoginHelper.loginCheck(sessionId);
        if (ssoUser == null) {
            return new ReturnT<SsoUser>(ReturnT.FAIL_CODE, "sso not login.");
        }
        return new ReturnT<SsoUser>(ssoUser);
    }

}