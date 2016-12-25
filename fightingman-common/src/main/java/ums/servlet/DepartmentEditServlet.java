package ums.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ums.service.DepartmentService;
import ums.service.MenuService;
import util.SpringUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author liangzhenghui
 * @date NOV 24, 2013    16:25:54 PM
 */
public class DepartmentEditServlet extends HttpServlet {

	
	private static final long serialVersionUID = 6698023905718559884L;
	private Boolean result = false;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		JSONObject json = new JSONObject();
		String id = req.getParameter("id");
		String pid = req.getParameter("pid");
		String name = req.getParameter("name");
		DepartmentService departmentService = DepartmentService.getInstance();
		departmentService.editDepartment(id, pid, name);
		result = true;
		json.put("result", result);
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
	    out.println(json.toJSONString());
	}

}
