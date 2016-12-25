package util;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
/**
 * 
 * 参数params对应的值如下,常需要取到@tmpGtDatas中的值
 * {
    "oper": "#/cxzs/bzgf/qylx.do",
    "params": {},
    "datas": {
        "ncm_glt_药品": {
            "dataset": [],
            "heads": [],
            "heads_change": []
        },
        "@tmpGtDatas": {
            "fl": "药品"
        }
    }
}
 * @author liangzhenghui
 *
 */
public class ParametersUtils {

	public static JSONObject getTmpGtDatas(HttpServletRequest request){
		JSONObject jsonObject = getParams(request);
		JSONObject datas = jsonObject.getJSONObject("datas");
		return datas.getJSONObject("@tmpGtDatas");
	}
	public static JSONObject getParams(HttpServletRequest request){
		return JSONObject.parseObject(request.getParameter("params"));
	}
}
