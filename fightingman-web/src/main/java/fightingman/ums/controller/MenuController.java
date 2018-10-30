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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import fightingman.model.Dictionary;
import fightingman.model.Function;
import fightingman.model.Menu;
import fightingman.model.MenuTree;
import fightingman.model.User;
import fightingman.service.RoleService;
import fightingman.ums.service.FunctionService;
import fightingman.ums.service.MenuService;
import fightingman.util.UserUtil;
import model.Response;
import util.ResponseUtil;

/**
 * 菜单管理
 * 
 * @author liangzhenghui
 * 
 */
@Controller
public class MenuController {

	private Logger logger = Logger.getLogger(MenuController.class);

	@Resource
	private MenuService menuService;

	@Resource
	private FunctionService functionService;

	@Resource
	private RoleService roleService;

	@RequestMapping(value = "/menu-list")
	public ModelAndView rolelistPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/framework/menu/menu-list");
		return model;
	}

	/**
	 * 根据第几页和每页显示几条记录返回菜单列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/menu-list-data")
	@ResponseBody
	public Map menuList(@RequestParam("page") String page, @RequestParam("rows") String rows) {
		List menuList = menuService.getMenuByPage(Integer.parseInt(page), Integer.parseInt(rows));
		int total = menuService.getCount();
		Map map = new HashMap();
		map.put("rows", menuList);
		map.put("total", total);
		return map;
	}

	@RequestMapping(value = "/menuDelete")
	@ResponseBody
	public Response menuDelete(@RequestParam("id") String id) {
		menuService.deleteMenu(id);
		return ResponseUtil.createSuccessResponse(null);
	}

	@RequestMapping(value = "/menuCreate")
	@ResponseBody
	public Response menuCreate(@RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		ModelAndView model = new ModelAndView();
		boolean isExits = false;
		String menuName = json.getString("menuName");
		isExits = menuService.menuIsExits(menuName);
		int result = 0;
		if (!isExits) {
			result = menuService.createMenu(json);
		}
		Response response = new Response();
		if(isExits) {
			response.setError(true);
			response.setResult("isExits");
		}
		return response;
	}

	@RequestMapping(value = "/menuEdit")
	@ResponseBody
	public Response menuEdit(HttpServletRequest req, @RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		menuService.UpdateMenu(json);
		Response response = new Response();
		return response;
	}

	@RequestMapping(value = "/menuTree")
	public void menuTree(HttpServletResponse resp) throws IOException {
		List<MenuTree> menuTreeList = menuService.getMenuTree();
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(menuTreeList));
	}

	@RequestMapping(value = "/menuGrantRole")
	@ResponseBody
	public Response menuGrantRole(@RequestParam("menuId") String[] menuIds, @RequestParam("roleId") String roleId)
			throws IOException {
		menuService.grantMenuToRole(roleId, menuIds);
		Response response = new Response();
		return response;
	}

	@RequestMapping(value = "/getParentMenuSelectWhileCreate")
	@ResponseBody
	public List<Dictionary> getParentMenuWhileCreate() {
		menuService.createRootMenu();
		List menuList = menuService.getAllMenu();
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : menuList) {
			Menu menu = (Menu) object;
			data = new Dictionary();
			data.setCode(menu.getId());
			data.setDetail(menu.getMenuName());
			dataList.add(data);
		}
		data = new Dictionary();
		data.setCode("");
		data.setDetail("");
		data.setSelected(true);
		dataList.add(data);
		return dataList;
	}

	@RequestMapping(value = "/getParentMenuSelectWhileEdit")
	public void getParentMenuWhileEdit(HttpServletResponse resp, @RequestParam("pid") String pid) throws IOException {
		List menuList = menuService.getAllMenu();
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : menuList) {
			Menu menu = (Menu) object;
			data = new Dictionary();
			if (pid.equals(menu.getId())) {
				data.setSelected(true);
			}
			data.setCode(menu.getId());
			data.setDetail(menu.getMenuName());
			dataList.add(data);
		}
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(dataList));
	}

	@RequestMapping(value = "/getMenuTypeWhileCreate")
	@ResponseBody
	public List<Dictionary> getMenuTypeWhileCreate() {
		List menuTypeList = menuService.getMenuType();
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : menuTypeList) {
			Map map = (Map) object;
			data = new Dictionary();
			data.setCode((String) map.get("code"));
			data.setDetail((String) map.get("detail"));
			dataList.add(data);
		}
		return dataList;
	}

	@RequestMapping(value = "/getMenuTypeWhileEdit")
	public void getMenuTypeWhileEdit(HttpServletResponse resp, @RequestParam("menuId") String menuId)
			throws IOException {
		List menuTypeList = menuService.getMenuType();
		Menu menu = menuService.getMenuById(menuId);
		String menuType = menu.getMenuType();
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : menuTypeList) {
			Map map = (Map) object;
			if (null != map) {
				String code = (String) map.get("code");
				data = new Dictionary();
				if (StringUtils.isNotBlank(code) && code.equals(menuType)) {
					data.setSelected(true);
				}
				data.setCode((String) map.get("code"));
				data.setDetail((String) map.get("detail"));
				dataList.add(data);
			}
		}
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(dataList));
	}

	@RequestMapping(value = "/getFunctionSelectWhileCreate")
	@ResponseBody
	public List<Dictionary> getFunctionSelectWhileCreate()  {
		List functionList = functionService.getAllFunction();
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : functionList) {
			Function function = (Function) object;
			if (function != null) {
				data = new Dictionary();
				data.setCode(function.getId());
				data.setDetail(function.getFunctionName());
				dataList.add(data);
			}
		}
		data = new Dictionary();
		data.setCode("");
		data.setDetail("");
		data.setSelected(true);
		dataList.add(data);
		return dataList;
	}

	@RequestMapping(value = "/getFunctionSelectWhileEdit")
	public void getFunctionSelectWhileEdit(HttpServletResponse resp, @RequestParam("menuId") String menuId)
			throws IOException {
		List functionList = functionService.getAllFunction();
		Menu menu = menuService.getMenuById(menuId);
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : functionList) {
			Function function = (Function) object;
			if (function != null) {
				data = new Dictionary();
				if (function.getId().equals(menu.getFunctionId())) {
					data.setSelected(true);
				}
				data.setCode(function.getId());
				data.setDetail(function.getFunctionName());
				dataList.add(data);
			}
		}
		data = new Dictionary();
		data.setCode("");
		data.setDetail("");
		dataList.add(data);
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.write(JSONObject.toJSONString(dataList));
	}

	@ResponseBody
	@RequestMapping(value = "/systemMenuTree")
	public List<MenuTree> SystemMenuTree(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		User user = UserUtil.getLoginUser();
		List<MenuTree> menuTreeList = null;
		List roleList = roleService.getRolesByUserId(user.getId());
		menuTreeList = menuService.getMenuTreeByRoles(roleList);
		return menuTreeList;
	}

	@RequestMapping(value = "/editMenusOfRole")
	@ResponseBody
	public Response EditMenusOfRole(@RequestParam("roleId") String roleId) {
		List<MenuTree> menuTreeListOfOneRole = null;
		List<MenuTree> menuTreeOfAll = null;
		if (StringUtils.isNotBlank(roleId)) {
			menuTreeListOfOneRole = menuService.getMenuTreeByRoleId(roleId);
		}
		// 取得所有的菜单,并且将菜单都放到List<MenuTree>中返回来
		menuTreeOfAll = menuService.getMenuTree();
		for (MenuTree menuTree : menuTreeOfAll) {
			for (MenuTree menuTreeOfOneRole : menuTreeListOfOneRole) {
				if (menuTree.getId().equals(menuTreeOfOneRole.getId())) {
					menuTree.setChecked(true);
				}
			}
		}
		return ResponseUtil.createSuccessResponse(menuTreeOfAll);
	}
}
