package util;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import model.Response;

/**
 * 
 *  @date   2018年7月8日
 *	@author liangzhenghui
 */
public class ResponseUtil {
	/**
	 * 成功情况下返回结果
	 * @param result
	 * @return
	 */
    public static Response createSuccessResponse(Object result){
        Response response = new Response();
        response.setError(false);
        response.setResult(result);
        return response;
    }
    /**
     * 失败情况下返回结果
     * @param type
     * @param errorMessage
     * @return
     */
    public static Response createErrorResponse(String type, String errorMessage){
        Response response = new Response();
        response.setError(true);
        response.setErrorType(type);
        response.setErrorMessage(errorMessage);
        return response;
    }

    public static boolean isSuccess(String result){
        JSONObject jsonObject = JSON.parseObject(result);
        String errcode = jsonObject.getString("errcode");
        if(StringUtils.isNotEmpty(errcode)){
            if("0".equals(errcode)){
                return  true;
            }
            return false;
        }
        return true;
    }

  /*  public static Response wxResultToResponse(String result){
        if(isSuccess(result)){
            return createSuccessResponse(result);
        }else{
            JSONObject jsonObject = JSON.parseObject(result);
            String errcode=jsonObject.getString("errcode");
            String _msg=StringUtils.isNotBlank(errcode)?MapUtils.getString(ErrorCode.ERROR_CODE, errcode):"";
            _msg=StringUtils.isNotBlank(_msg)?_msg+":":"";
            return createErrorResponse(errcode, _msg+jsonObject.getString("errmsg"));
        }
    }*/
   /* public static Response getResponse(String url, Object parameter) {
		try {
			String result = AppHttpUtil.doPost(JSON.toJSONString(parameter), url);
			return ResponseUtil.wxResultToResponse(result);
		} catch (IOException e) {
			return ResponseUtil.createErrorResponse(ErrorCode.SERVICE_ERROR, "服务调用失败！");
		}
	}*/
}
