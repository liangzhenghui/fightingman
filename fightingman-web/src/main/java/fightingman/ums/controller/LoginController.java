package fightingman.ums.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fightingman.service.RoleService;
import fightingman.service.UserService;
@Controller
public class LoginController {

	private Logger logger = Logger.getLogger(LoginController.class);

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@RequestMapping("/login")
	public String login(Model model,
			@RequestParam(value = "error", required = false) String error) {
		if (error != null) {
			model.addAttribute("error", "用户名或密码错误");
		}
		return "login";
	}


	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest req) {
		ModelAndView model = new ModelAndView();
		HttpSession session = req.getSession(true);
		session.setAttribute("user", null);
		model.addObject("result", true);
		return model;
	}

	@RequestMapping(value = "/register")
	public ModelAndView register(@RequestParam("userId") String userId, @RequestParam("password") String password,
			HttpServletRequest req) {
		ModelAndView model = new ModelAndView();
		boolean isExits = userService.isExists(userId);
		if (!isExits) {
			int result = userService.createUser(userId, password);
			model.addObject("result", result);
		} else {
			// 3表示存在相同的用户名
			model.addObject("result", 3);
		}
		return model;
	}

	/**
	 * 判断session是否过期
	 * 
	 * @param sessionId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/session-expired")
	public ModelAndView login(@RequestParam("sessionId") String sessionId, HttpServletRequest req) {
		ModelAndView model = new ModelAndView();
		boolean result = false;
		// 如果没有session就返回null
		HttpSession session = req.getSession(false);
		if (session != null) {
			String id = session.getId();
			if (StringUtils.isNotBlank(sessionId) && !sessionId.equals(id)) {
				model.addObject("result", true);
			}
		} else {
			// session过期了则为true
			model.addObject("result", true);
		}
		model.addObject("result", result);
		return model;
	}
}
