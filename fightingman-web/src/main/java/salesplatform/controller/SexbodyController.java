package salesplatform.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import advertisingplatform.model.Page;
import common.Response;
import salesplatform.service.AppService;
import salesplatform.service.SexyBodyService;

@Controller
public class SexbodyController {
	@Resource
	private SexyBodyService sexyBodyService;
	public static
	
	@ResponseBody
	@RequestMapping(value = "/sexy-picture")
	public Response SexyPictureList(HttpServletRequest request) throws ParseException {
		try {
			String pageNum=request.getParameter("pageNum");
			pageService.
			sexyBodyService.queryPicture(pageNum);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
