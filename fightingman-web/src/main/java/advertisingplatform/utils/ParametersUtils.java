package advertisingplatform.utils;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class ParametersUtils {
	public static JSONObject getTmpGtDatas(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject();
		json = jsonObject.parseObject(request.getParameter("params"));
		JSONObject datas = (JSONObject)json.get("datas");
		JSONObject tmpGtDatas = (JSONObject)datas.get("@tmpGtDatas");
		return tmpGtDatas;
	}
	public static JSONObject getDatas(HttpServletRequest request){
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject();
		json = jsonObject.parseObject(request.getParameter("params"));
		JSONObject datas = (JSONObject)json.get("datas");
		return datas;
	}
}
