package model;

import java.util.HashMap;
import java.util.Map;

/**
 * *构造返回如下形式json的对象 
 * {
	 * "flag": "1",
     * "message": "",
     * "datas": {
     *   	"ncm_glt": {
     *   		"dataset": [
     *   			{.....},{.....}
     *   		],
     *   		"params": {
     *   			"rowsCount": 10,
     *   			"name": "ncm_glt_信息查询"
     *   		}
     *    	}
     *   }
	 * }
 * @author liangzhenghui
 *
 */
public class AppResponse {
	private String flag;
	private ErrorInfo error;
	private Map<String,Object> datas;

	public AppResponse(String glt, NcmGlt ncmGlt) {
		if(this.datas == null){
			this.datas = new HashMap<String, Object>();
		}
		this.datas.put(glt, ncmGlt);
	}
	
	public AppResponse(ErrorInfo error) {
		this.error=error;
	}
	
	public AppResponse(String gt, NcmGt ncmGt) {
		if(this.datas == null){
			this.datas = new HashMap<String, Object>();
		}
		this.datas.put(gt, ncmGt);
	}

	public Map<String, Object> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, Object> datas) {
		this.datas = datas;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	public ErrorInfo getError() {
		return error;
	}

	public void setError(ErrorInfo error) {
		this.error = error;
	}
}
