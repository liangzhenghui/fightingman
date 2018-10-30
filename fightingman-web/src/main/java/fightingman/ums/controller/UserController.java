package fightingman.ums.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonView;

import fightingman.model.User;
import fightingman.service.UserService;
import model.Response;
import util.ResponseUtil;

/**
 * 用户管理
 * 
 * @author liangzhenghui
 * 
 */
@Controller
public class UserController {

	private Logger logger = Logger.getLogger(UserController.class);

	@Resource
	private UserService userService;

	@RequestMapping(value = "/user-list")
	public ModelAndView rolelistPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/framework/user/user-list");
		return model;
	}

	/**
	 * 根据第几页和每页显示几条记录返回用户列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/user-list-data")
	@ResponseBody
	public Map userList(@RequestParam("page") String page, @RequestParam("rows") String rows) {
		Map map = new HashMap();
		List userList = userService.getUserByPage(Integer.parseInt(page), Integer.parseInt(rows));
		int total = userService.getCount();
		map.put("rows", userList);
		map.put("total", total);
		return map;
	}

	@RequestMapping(value = "/userCreate")
	@ResponseBody
	public Response userCreate(@RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		String userid = json.getString("userid");
		String username = json.getString("username");
		String mobile = json.getString("mobile");
		User user = userService.getUserByUserid(userid);
		Response response = new Response();
		if (user != null) {
			response.setError(true);
			response.setResult("isExits");
		} else {
			userService.createUser(userid, username, mobile);
		}
		return response;
	}

	@RequestMapping(value = "/userEdit")
	@ResponseBody
	public Response userEdit( @RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		String userid = json.getString("userid");
		String username = json.getString("username");
		String mobile = json.getString("mobile");
		String id = json.getString("id");
		userService.editUser(id, userid, username, mobile);
		return ResponseUtil.createSuccessResponse(null);
	}

	@RequestMapping(value = "/userDelete")
	public ModelAndView userDelete(@RequestParam("id") String id) {
		ModelAndView model = new ModelAndView();
		int result = userService.deleteUser(id);
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		return model;
	}

	@RequestMapping(value = "/user-role-delete")
	public ModelAndView userRoleDelete(@RequestParam("id") String id) {
		ModelAndView model = new ModelAndView();
		int result = userService.deleteUserRole(id);
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		return model;
	}

	@RequestMapping(value = "/user-role-list")
	public ModelAndView userRoleList(@RequestParam("page") String page, @RequestParam("rows") String rows,
			@RequestParam("userId") String userId) {
		ModelAndView model = new ModelAndView();
		List userRoleList = userService.getUserRoleByPage(Integer.parseInt(page), Integer.parseInt(rows), userId);
		int total = userService.getUserRoleCount();
		model.addObject("rows", userRoleList);
		model.addObject("total", total);
		return model;
	}
}
