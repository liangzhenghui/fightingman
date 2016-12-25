package ums.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ums.model.DepartmentTree;
import ums.model.MenuTree;
import ums.service.DepartmentService;
import ums.service.MenuService;
import util.SpringUtil;

import com.alibaba.fastjson.JSON;

/**
 * @author liangzhenghui
 * @date Aug 24, 2013    7:57:39 PM
 */
public class DepartmentTreeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 4315110956905406813L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DepartmentService departmentService = DepartmentService.getInstance();
		List<DepartmentTree> departmentreeList = departmentService.getAllDepartments();
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String departmentTree = JSON.toJSONString(departmentreeList);
		out.print(departmentTree);
	}
}
