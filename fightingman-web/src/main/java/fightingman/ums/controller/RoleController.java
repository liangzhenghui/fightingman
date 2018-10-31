package fightingman.ums.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import fightingman.model.Dictionary;
import fightingman.model.MenuTree;
import fightingman.model.Role;
import fightingman.service.RoleService;
import fightingman.ums.service.MenuService;
import model.Response;

/**
 * 角色管理
 * 
 * @author liangzhenghui
 * 
 */
@Controller
public class RoleController {

	private Logger logger = Logger.getLogger(RoleController.class);

	@Resource
	private RoleService roleService;
	@Resource
	private MenuService menuService;

	/**
	 * 根据第几页和每页显示几条记录返回用户列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/menuTreeForOneRole")
	public ModelAndView menuTreeForOneRole(HttpServletResponse resp, @RequestParam("roleId") String roleId)
			throws IOException {
		ModelAndView model = new ModelAndView();
		List<MenuTree> menuTreeList = null;
		if (StringUtils.isNotBlank(roleId)) {
			menuTreeList = menuService.getMenuTreeByRoleId(roleId);
		}
		model.addObject("menuTreeList", menuTreeList);
		return model;
	}

	@RequestMapping(value = "/role-create",method=RequestMethod.POST)
	@ResponseBody
	public Response roleCreate(@RequestParam("data") String data) {
		boolean isExits = false;
		JSONObject json = JSONObject.parseObject(data);
		String roleName = json.getString("roleName");
		isExits = roleService.roleNameIsExits(roleName);
		int result = 0;
		Response response = new Response();
		if (!isExits) {
			roleService.createRole(roleName);
		}else {
			response.setError(true);
		}
		return response;
	}

	@RequestMapping(value = "/role-delete")
	@ResponseBody
	public Response deleteRole(@RequestParam("roleId") String roleId) {
		roleService.deleteRole(roleId);
		Response response = new Response();
		return response;
	}

	@RequestMapping(value = "/roleGrantUser")
	@ResponseBody
	public Response roleGrantUser(@RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		String userId = json.getString("userId");
		String roleId = json.getString("roleId");
		String roleName = json.getString("roleName");
		String userName = json.getString("userName");
		boolean hasTheRole = false;
		hasTheRole = roleService.userHasTheRole(userId, roleId);
		if (!hasTheRole) {
			roleService.roleGrantUser(roleId, userId, roleName, userName);
		}
		Response response = new Response();
		Map map = new HashMap();
		map.put("hasTheRole", hasTheRole);
		response.setResult(map);
		return response;
	}

	@RequestMapping(value = "/getAllRoles")
	public void getAllRoles(HttpServletResponse resp) throws IOException {
		List RolesList = roleService.getAllRoles();
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : RolesList) {
			Role role = (Role) object;
			if (role != null) {
				data = new Dictionary();
				data.setCode(role.getRoleId());
				data.setDetail(role.getRoleName());
				dataList.add(data);
			}
		}
		data = new Dictionary();
		data.setCode("");
		data.setDetail("");
		data.setSelected(true);
		dataList.add(data);
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.write(JSONObject.toJSONString(dataList));
	}

	@RequestMapping(value = "/role-list")
	public ModelAndView rolelistPage() {
		ModelAndView model = new ModelAndView();
		List roleList = roleService.getAllRoles();
		model.addObject("roleList", roleList);
		model.setViewName("/framework/role/role-list");
		return model;
	}
	
	@RequestMapping(value = "/role-list-data")
	@ResponseBody
	public Map roleList(@RequestParam("page") String page, @RequestParam("rows") String rows) {
		Map model = new HashMap();
		List roleList = roleService.getRolesByPage(Integer.parseInt(page), Integer.parseInt(rows));
		int total = roleService.getCount();
		model.put("rows", roleList);
		model.put("total", total);
		return model;
	}
}
