package fightingman.lxj.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fightingman.lxj.model.Demo;
import model.Response;
import util.ResponseUtil;

/**
 * @Date:2018年9月23日
 * @Author:liangzhenghui
 * @Github:https://github.com/liangzhenghui
 * @Email:liangzhenghui@gmail.com
 * @Description:
 */
@Controller
public class DemoController {
	@RequestMapping(value="/test",method=RequestMethod.GET)
	@ResponseBody
	public Response test(@Valid Demo demo) {
		return ResponseUtil.createSuccessResponse(null);
	}
}
