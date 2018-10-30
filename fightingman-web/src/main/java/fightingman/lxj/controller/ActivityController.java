/**
 * 
 * 2018年7月8日
 * liangzhenghui
 
 */
package fightingman.lxj.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import controller.AppExceptionController;
import fightingman.lxj.model.Activity;
import fightingman.lxj.model.Demo;
import fightingman.lxj.service.ActivityService;
import model.Response;
import util.ResponseUtil;

@RequestMapping("lxj/activity")
@Controller
public class ActivityController extends AppExceptionController {
	@Resource
	private ActivityService activityService;

	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Response activity_add(@Valid @RequestBody Activity activity) {
		activityService.add(activity);
		return ResponseUtil.createSuccessResponse(null);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public Response activity_list() {
		List list = activityService.list();
		return ResponseUtil.createSuccessResponse(list);
	}
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	@ResponseBody
	public Response test(@Valid Demo demo) {
		return ResponseUtil.createSuccessResponse(null);
	}
}
