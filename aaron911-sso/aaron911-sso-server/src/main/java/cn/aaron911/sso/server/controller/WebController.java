package cn.aaron911.sso.server.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.aaron911.sso.core.conf.Conf;
import cn.aaron911.sso.core.entity.ReturnT;
import cn.aaron911.sso.core.login.SsoWebLoginHelper;
import cn.aaron911.sso.core.store.SsoLoginStore;
import cn.aaron911.sso.core.store.SsoSessionIdHelper;
import cn.aaron911.sso.core.user.SsoUser;
import cn.aaron911.sso.server.core.model.UserInfo;
import cn.aaron911.sso.server.service.UserService;

/**
 * sso server (for web)
 *
 */
@Controller
public class WebController {

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String index(Model model, HttpServletRequest request, HttpServletResponse response) {

		// login check
		SsoUser ssoUser = SsoWebLoginHelper.loginCheck(request, response);

		if (ssoUser == null) {
			return "redirect:/login";
		} else {
			model.addAttribute("ssoUser", ssoUser);
			return "index";
		}
	}

	/**
	 * Login page
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(Conf.SSO_LOGIN)
	public String login(Model model, HttpServletRequest request, HttpServletResponse response) {

		// login check
		SsoUser ssoUser = SsoWebLoginHelper.loginCheck(request, response);

		if (ssoUser != null) {

			// success redirect
			String redirectUrl = request.getParameter(Conf.REDIRECT_URL);
			if (redirectUrl != null && redirectUrl.trim().length() > 0) {

				String sessionId = SsoWebLoginHelper.getSessionIdByCookie(request);
				String redirectUrlFinal = redirectUrl + "?" + Conf.SSO_SESSIONID + "=" + sessionId;
				;

				return "redirect:" + redirectUrlFinal;
			} else {
				return "redirect:/";
			}
		}

		model.addAttribute("errorMsg", request.getParameter("errorMsg"));
		model.addAttribute(Conf.REDIRECT_URL, request.getParameter(Conf.REDIRECT_URL));
		return "login";
	}

	/**
	 * Login
	 *
	 * @param request
	 * @param redirectAttributes
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/doLogin")
	public String doLogin(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes, String username, String password, String ifRemember) {

		boolean ifRem = (ifRemember != null && "on".equals(ifRemember)) ? true : false;

		// valid login
		ReturnT<UserInfo> result = userService.findUser(username, password);
		if (result.getCode() != ReturnT.SUCCESS_CODE) {
			redirectAttributes.addAttribute("errorMsg", result.getMsg());

			redirectAttributes.addAttribute(Conf.REDIRECT_URL, request.getParameter(Conf.REDIRECT_URL));
			return "redirect:/login";
		}

		// 1、make sso user
		SsoUser ssoUser = new SsoUser();
		ssoUser.setUserid(String.valueOf(result.getData().getUserid()));
		ssoUser.setUsername(result.getData().getUsername());
		ssoUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
		ssoUser.setExpireMinite(SsoLoginStore.getRedisExpireMinite());
		ssoUser.setExpireFreshTime(System.currentTimeMillis());

		// 2、make session id
		String sessionId = SsoSessionIdHelper.makeSessionId(ssoUser);

		// 3、login, store storeKey + cookie sessionId
		SsoWebLoginHelper.login(response, sessionId, ssoUser, ifRem);

		// 4、return, redirect sessionId
		String redirectUrl = request.getParameter(Conf.REDIRECT_URL);
		if (redirectUrl != null && redirectUrl.trim().length() > 0) {
			String redirectUrlFinal = redirectUrl + "?" + Conf.SSO_SESSIONID + "=" + sessionId;
			return "redirect:" + redirectUrlFinal;
		} else {
			return "redirect:/";
		}

	}

	/**
	 * Logout
	 *
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(Conf.SSO_LOGOUT)
	public String logout(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {

		// logout
		SsoWebLoginHelper.logout(request, response);

		redirectAttributes.addAttribute(Conf.REDIRECT_URL, request.getParameter(Conf.REDIRECT_URL));
		return "redirect:/login";
	}

}