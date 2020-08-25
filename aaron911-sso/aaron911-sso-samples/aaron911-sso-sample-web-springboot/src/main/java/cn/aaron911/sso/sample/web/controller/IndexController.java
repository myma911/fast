package cn.aaron911.sso.sample.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.aaron911.sso.core.conf.Conf;
import cn.aaron911.sso.core.entity.ReturnT;
import cn.aaron911.sso.core.user.SsoUser;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request) {

        SsoUser ssoUser = (SsoUser) request.getAttribute(Conf.SSO_USER);
        model.addAttribute("ssoUser", ssoUser);
        return "index";
    }
    

    @RequestMapping("/json")
    @ResponseBody
    public ReturnT<SsoUser> json(Model model, HttpServletRequest request) {
    	SsoUser ssoUser = (SsoUser) request.getAttribute(Conf.SSO_USER);
        return new ReturnT<SsoUser>(ssoUser);
    }

}