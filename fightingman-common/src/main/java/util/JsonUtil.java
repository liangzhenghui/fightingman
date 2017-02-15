package util;

import java.util.List;

import com.alibaba.fastjson.JSON;
/**
 * Json工具类
 * @author liangzhenghui
 * @date 2017-02-08
 */
public class JsonUtil {

	public static String object2Json(Object obj){
		return JSON.toJSONString(obj);
	}
	
	public static <T> T json2Bean(String str,Class<T> beanClass){
		return JSON.parseObject(str, beanClass);
	}
	
	public static <T> List<T> json2List(String str,Class<T> beanClass){
		return JSON.parseArray(str, beanClass);
	}
	
}
