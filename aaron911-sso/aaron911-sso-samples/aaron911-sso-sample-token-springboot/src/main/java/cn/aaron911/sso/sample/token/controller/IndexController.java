package cn.aaron911.sso.sample.token.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.aaron911.sso.core.conf.Conf;
import cn.aaron911.sso.core.entity.ReturnT;
import cn.aaron911.sso.core.user.SsoUser;

@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public ReturnT<SsoUser> index(HttpServletRequest request) {
        SsoUser ssoUser = (SsoUser) request.getAttribute(Conf.SSO_USER);
        return new ReturnT<SsoUser>(ssoUser);
    }

}