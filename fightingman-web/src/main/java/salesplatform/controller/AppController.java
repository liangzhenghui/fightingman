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

import salesplatform.service.AppService;

@Controller
@RequestMapping("/api")
public class AppController {
	@Resource
	private AppService appService;
	
	@ResponseBody
	@RequestMapping(value = "/app-version-create")
	public Map productCreate(
			@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws ParseException {
		Map map=new HashMap();
		try {
			String original_name = file.getOriginalFilename();
			String type = original_name.substring(original_name.indexOf(".") + 1);
			String versionCode = request.getParameter("versionCode");
			String versionName = request.getParameter("versionName");
			byte[] content;
			content = file.getBytes();
			int result = appService.createAppVersion(versionCode, versionName, original_name, type, content);
			if (result == 1) {
				map.put("result", true);
			} else {
				map.put("result", true);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	@ResponseBody
	@RequestMapping(value = "/app-version-delete")
	public Map ProductDelete(@RequestParam("id") String id) {
		int result = appService.deleteAppService(id);
		Map map=new HashMap();
		if (result == 1) {
			map.put("result", true);
		} else {
			map.put("result", true);
		}
		return map;
	}

}
