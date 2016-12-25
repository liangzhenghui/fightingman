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
 * @date NOV 25, 2013    22:12:04 PM
 */
public class DepartmentDeleteServlet extends HttpServlet {

	private static final long serialVersionUID = 9100962471137094302L;
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
		DepartmentService departmentservice = DepartmentService.getInstance();
		departmentservice.deleteDepartment(id);
		result = true;
		json.put("result", result);
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
	    out.println(json.toJSONString());
	}

}
