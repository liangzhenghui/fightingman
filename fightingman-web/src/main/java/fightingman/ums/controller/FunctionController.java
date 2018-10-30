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

import fightingman.ums.service.FunctionService;
import model.Response;

/**
 * 功能管理
 * 
 * @author liangzhenghui
 * 
 */
@Controller
public class FunctionController {

	private Logger logger = Logger.getLogger(FunctionController.class);

	@Resource
	private FunctionService functionService;

	/**
	 * 根据第几页和每页显示几条记录返回功能列表
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/function-list-data")
	@ResponseBody
	public Map functionListData(@RequestParam("page") String page, @RequestParam("rows") String rows) {
		Map map = new HashMap();
		List functionList = functionService.getFunctionByPage(Integer.parseInt(page), Integer.parseInt(rows));
		int total = functionService.getCount();
		map.put("rows", functionList);
		map.put("total", total);
		return map;
	}

	@RequestMapping(value = "/function-list")
	public ModelAndView functionList() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/framework/function/function-list");
		return model;
	}

	@RequestMapping(value = "/functionCreate")
	@ResponseBody
	public Response functionCreate(@RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		boolean isExits = false;
		String functionName = json.getString("functionName");
		String url = json.getString("url");
		isExits = functionService.functionIsExits(functionName);
		Response response = new Response();
		if (!isExits) {
			functionService.createFunction(functionName, url);
		}else {
			response.setResult("isExits");
		}
		return response;
	}

	@RequestMapping(value = "/functionEdit")
	@ResponseBody
	public Response functionEdit(HttpServletRequest req, @RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		String functionName = json.getString("functionName");
		String url = json.getString("url");
		String id = json.getString("id");
		functionService.editFunction(id, functionName, url);
		Response response = new Response();
		return response;
	}

	@RequestMapping(value = "/functionDelete")
	@ResponseBody
	public Response functionDelete(@RequestParam("id") String id) {
		functionService.deleteFunction(id);
		Response response = new Response();
		return response;
	}
}
