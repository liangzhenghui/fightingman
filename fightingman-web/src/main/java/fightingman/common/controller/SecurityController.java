package fightingman.common.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import fightingman.model.MenuTree;
import fightingman.model.User;
import fightingman.service.RoleService;
import fightingman.ums.service.MenuService;
import fightingman.util.UserUtil;
import model.Response;

/**
 * @Date:2018年10月10日
 * @Author:liangzhenghui
 * @Github:https://github.com/liangzhenghui
 * @Email:liangzhenghui@gmail.com
 * @Description:
 */
@Controller
public class SecurityController {
	@Resource
	private RoleService roleService;
	@Resource
	private MenuService menuService;

	@RequestMapping("/403")
	public String deny() {
		return "403";
	}
	@ResponseBody
	@RequestMapping("/index")
	public ModelAndView welcome(ModelAndView modelView) {
		User user = UserUtil.getLoginUser();
		List<MenuTree> menuTreeList = null;
		List roleList = roleService.getRolesByUserId(user.getId());
		menuTreeList = menuService.getMenuTreeByRoles(roleList);
		modelView.addObject("result",JSON.toJSONString(menuTreeList));
		modelView.setViewName("index");
		return modelView;
	}
}
