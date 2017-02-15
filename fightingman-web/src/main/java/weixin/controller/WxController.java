package weixin.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import dao.JdbcService;
import model.AppResponse;
import model.ErrorInfo;
import model.NcmGt;
import util.MessageHandlerUtil;
import util.ParametersUtils;
import util.SignUtil;
import util.WxApiUtil;

/**
 * @author: liangzhenghui
 * @blog: http://my.oschina.net/liangzhenghui/blog
 * @email:715818885@qq.com 2015年9月2日
 */
@Controller
public class WxController {
	@Resource
	private JdbcService jdbcService;
	private static String tempopenid;

	
	/**
	 * test
	 * @param request
	 * @return
	 */
	@RequestMapping("/test")
	@ResponseBody
	public AppResponse test(HttpServletRequest request){
		AppResponse appResponse = null;
		/*JSONObject tmpGtDatas = ParametersUtils.getTmpGtDatas(request);
		String flag=(String)tmpGtDatas.get("flag");*/
		String flag="1";
		if(flag.equals("1")){
			Map map=new HashMap();
			map.put("key", "success");
			NcmGt ncmGt = new NcmGt(map);
			appResponse = new AppResponse("ncm_glt_test", ncmGt);
			appResponse.setFlag("1");
		}else{
			Map map=new HashMap();
			map.put("key", "error");
			ErrorInfo error=new ErrorInfo();
			error.setCode("0001");
			error.setMessage("wrong message");
			NcmGt ncmGt = new NcmGt(map);
			appResponse = new AppResponse(error);
			appResponse.setFlag("-1");
		}
		
		return appResponse;
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/wx",method = {RequestMethod.GET})
	public void wx_get(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		PrintWriter writer = response.getWriter();
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			/*String sql1 = "delete  from s_wx";
			String sql = "insert into s_wx(id,signature,timestamp,nonce,echostr) values(?,?,?,?,?)";
			jdbcService.update(sql, new Object[] {
					UUIDGenerator.generateUUID(), signature, timestamp, nonce,
					echostr }, new int[] {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR});*/
			writer.write(echostr);
		}
		writer.flush();
		writer.close();
		writer = null;
	}
	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/wx",method = {RequestMethod.POST})
	public void wx_post(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		 request.setCharacterEncoding("UTF-8");
		 response.setCharacterEncoding("UTF-8");
		  System.out.println("请求进入");
	        String responseMessage;
	        try {
	            //解析微信发来的请求,将解析后的结果封装成Map返回
	            Map<String,String> map = MessageHandlerUtil.parseXml(request);
	            String openid=map.get("FromUserName");
	            tempopenid=openid;
	            System.out.println("--------------------------------");
	            System.out.println(openid);
	            System.out.println("开始构造响应消息");
	            responseMessage = MessageHandlerUtil.buildResponseMessage(map);
	            System.out.println(responseMessage);
	            if(responseMessage.equals("")){
	                responseMessage ="未正确响应";
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("发生异常："+ e.getMessage());
	            responseMessage ="未正确响应";
	        }
	        //发送响应消息
	        response.getWriter().println(responseMessage);
	
	}
	private HttpMethodBase createMethod(String url, int timeout) {
        PostMethod method = null;
        try {
            method = new PostMethod(url);
            JSONObject jsonObject = new JSONObject();
 
            jsonObject.put("blog", "https://www.iteblog.com");
            jsonObject.put("Author", "iteblog");
 
            String transJson = jsonObject.toString();
            RequestEntity se = new StringRequestEntity(transJson, "application/json", "UTF-8");
            method.setRequestEntity(se);
            //使用系统提供的默认的恢复策略
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
            //设置超时的时间
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
        } catch (IllegalArgumentException e) {
            //logger.error("非法的URL：{}", url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
 
        return method;
}
	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/kf",method = {RequestMethod.POST})
	public void kf(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String test = request.getParameter("test");
		Map map = new HashMap();
		//String openid=(String) request.getSession().getAttribute("openid");
		System.out.println("------aaa--aa-----------------------");
        System.out.println(tempopenid);
		map.put("touser",tempopenid);
		map.put("msgtype", "text");
		Map map1 = new HashMap();
		map1.put("content", test);
		map.put("text", map1);
  		String url =WxApiUtil.KF+"jXSPJNHogYRCYS7pOtqDAZT40yBRDIowTzQuSsov15joMR-W3DrfMo82jvOJzpcIWqtNTh0Mms-WBOFTVzX3aXiN4lfBZlHR2JO4oAujo97StXmAJ1YkE7H-KSjFu6cCBBAbAIAGGF";
  		/*{
  		    "touser":"OPENID",
  		    "msgtype":"text",
  		    "text":
  		    {
  		         "content":"Hello World"
  		    }
  		}*/
  		
  		HttpResponse httpResponse;
		try {
			//httpResponse = HttpClientUtil.makeRequest(url,map);
			 //instantiates httpclient to make request
		    DefaultHttpClient httpclient = new DefaultHttpClient();

		    //url with the post data
		    HttpPost httpost = new HttpPost(url);
		    //passes the results to a string builder/entity
		    System.out.println("------mmmmmma--nnn-----------------------"+JSON.toJSONString(map));
		    StringEntity se = new StringEntity(JSON.toJSONString(map),"UTF-8");

		    //sets the post request as the resulting string
		    httpost.setEntity(se);
		    //sets a request header so the page receving the request
		    //will know what to do with it
		    httpost.setHeader("Accept", "application/json");
		    httpost.setHeader("Content-type", "application/json");
		    httpclient.execute(httpost);
		    //Handles what is returned from the page 
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		
	
	}
	 /**
     * 解析微信发来的请求（XML）
     *
     * @param request 封装了请求信息的HttpServletRequest对象
     * @return map 解析结果
     * @throws Exception
     */
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();
        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList) {
            System.out.println(e.getName() + "|" + e.getText());
            map.put(e.getName(), e.getText());
        }

        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }

	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/access_token")
	public void access_token(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx31382b2308a2b3c8&secret=96a88d83a1f71173d095e06feafd2cb4");
		HttpResponse httpResponse = httpclient.execute(httpget);
		HttpEntity entity = httpResponse.getEntity();
		String entityString = EntityUtils.toString(entity);
		JSONObject jsonObject = new JSONObject();
		JSONObject json = (JSONObject) jsonObject.parseObject(entityString);
		String access_token = (String) json.get("access_token");
		PrintWriter writer = response.getWriter();
		writer.write(access_token);
		writer.flush();
		writer.close();
		writer = null;
	}

	@RequestMapping(value = "/ticket")
	public void ticket(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpClient httpclient = new DefaultHttpClient();
		String access_token = request.getParameter("access_token");
		System.out.println("access_token" + access_token);
		HttpGet httpget = new HttpGet(
				"https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
						+ access_token + "&type=jsapi");
		HttpResponse httpResponse1 = httpclient.execute(httpget);
		HttpEntity entity = httpResponse1.getEntity();
		String entityString1 = EntityUtils.toString(entity);
		JSONObject jsonObject1 = new JSONObject();
		JSONObject json1 = (JSONObject) jsonObject1.parseObject(entityString1);
		String ticket = (String) json1.get("ticket");
		String ticket1 = "jsapi_ticket=" + ticket;
		String timestamp = "timestamp=1441166501";
		String noncestr = "noncestr=1143143726";
		String url1 = "url=http://test.wemakers.net/salesplatform/WeiXinDemo/WeiXinDemo/index.html";
		String getSignature = SignUtil.getSignature(timestamp, noncestr,
				ticket1, url1);
		PrintWriter writer = response.getWriter();
		writer.write(getSignature);
		writer.flush();
		writer.close();
		writer = null;
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/send",method = {RequestMethod.GET})
	public void send(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String test = request.getParameter("test");
		System.out.println("aaaaaaaaaaaaaaaaaaaa");	Map map = new HashMap();
		//String openid=(String) request.getSession().getAttribute("openid");
		map.put("touser","oupAet4plPZC43EvDtR_9m2HNjTQ");
		map.put("template_id", "iEEUgXnVJlKq9V1XobpnS_0pRuIpbbsmVLWMv4jPxA4");
		map.put("url", "http://baidu.com");
		Map titleMap=new HashMap();
		Map titleDataMap = new HashMap();
		titleDataMap.put("value", "工资提醒");
		titleDataMap.put("color", "#173177");
		Map moneyMap = new HashMap();
		Map moneyDataMap = new HashMap();
		moneyDataMap.put("value", "1亿");
		moneyDataMap.put("color", "#173177");
		Map dataMap=new HashMap();
		dataMap.put("title", titleDataMap);
		dataMap.put("money", moneyDataMap);
		map.put("data", dataMap);
  		String url =WxApiUtil.SEND_TEMPLATE_MESSAGE+"BdO-M6WUysgO23ja9J1UzA1iTBl94hFZhPeydWZipBrTITqmWj2RZgsToxSPwKbZMKD5HryP52-SpyepnX5H4GcRAmoQIjoOtDHsmUW7GIx4J_5EJ7vJSFKE61VUYMD8TWZaAEADGJ";
  		HttpResponse httpResponse;
		try {
			//httpResponse = HttpClientUtil.makeRequest(url,map);
			 //instantiates httpclient to make request
		    DefaultHttpClient httpclient = new DefaultHttpClient();

		    //url with the post data
		    HttpPost httpost = new HttpPost(url);
		    //passes the results to a string builder/entity
		    System.out.println("------mmmmmma--nnn-----------------------"+JSON.toJSONString(map));
		    StringEntity se = new StringEntity(JSON.toJSONString(map),"UTF-8");

		    //sets the post request as the resulting string
		    httpost.setEntity(se);
		    //sets a request header so the page receving the request
		    //will know what to do with it
		    httpost.setHeader("Accept", "application/json");
		    httpost.setHeader("Content-type", "application/json");
		    httpclient.execute(httpost);
		    //Handles what is returned from the page 
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		
	}
	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadImage",method = {RequestMethod.GET})
	public void uploadImage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		 String filePath = "E:/test.jpg";
        //String filePath = "D:/JavaSoftwareDevelopeFolder/IntelliJ IDEA_Workspace/WxStudy/web/media/voice/voice.mp3";
        //String filePath = "D:\\JavaSoftwareDevelopeFolder\\IntelliJ IDEA_Workspace\\WxStudy\\web\\media\\video\\小苹果.mp4";
        //媒体文件类型
		 String type="";
		 File file=new File(filePath);
		WxApiUtil.uploadMedia(file, "BdO-M6WUysgO23ja9J1UzA1iTBl94hFZhPeydWZipBrTITqmWj2RZgsToxSPwKbZMKD5HryP52-SpyepnX5H4GcRAmoQIjoOtDHsmUW7GIx4J_5EJ7vJSFKE61VUYMD8TWZaAEADGJ", type);
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/addMaterial",method = {RequestMethod.GET})
	public void addMaterial(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		 String filePath = "E:/test.jpg";
        //String filePath = "D:/JavaSoftwareDevelopeFolder/IntelliJ IDEA_Workspace/WxStudy/web/media/voice/voice.mp3";
        //String filePath = "D:\\JavaSoftwareDevelopeFolder\\IntelliJ IDEA_Workspace\\WxStudy\\web\\media\\video\\小苹果.mp4";
        //媒体文件类型
		 File file=new File(filePath);
        String type = "thumb";
		WxApiUtil.uploadMedia(file, WxApiUtil.ACCESS_TOKEN_TEST, type);
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadNews",method = {RequestMethod.GET})
	public void uploadNews(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map articles=new HashMap();
		Map article=new HashMap();
		article.put("thumb_media_id", "Q8PQ05g6Qse6bd3omybgQG8wykDu2bGiMXrTcoRocGc");
		article.put("author", "xxxxxxxxx");
		article.put("title", "titlexxx");
		article.put("content_source_url", "www.baidu.com");
		article.put("content", "xxxxxxxxxxxxxxx");
		article.put("digest", "描述");
		article.put("show_cover_pic", "1");
		List list=new ArrayList();
		list.add(article);
		articles.put("articles", list);
		String url =WxApiUtil.UPLOAD_NEWS+WxApiUtil.ACCESS_TOKEN_TEST;
  		HttpResponse httpResponse;
		try {
			//httpResponse = HttpClientUtil.makeRequest(url,map);
			 //instantiates httpclient to make request
		    DefaultHttpClient httpclient = new DefaultHttpClient();

		    //url with the post data
		    HttpPost httpost = new HttpPost(url);
		    //passes the results to a string builder/entity
		    System.out.println("------uploadNews---------------------"+JSON.toJSONString(articles));
		    StringEntity se = new StringEntity(JSON.toJSONString(articles),"UTF-8");

		    //sets the post request as the resulting string
		    httpost.setEntity(se);
		    //sets a request header so the page receving the request
		    //will know what to do with it
		    httpost.setHeader("Accept", "application/json");
		    httpost.setHeader("Content-type", "application/json");
		    HttpResponse httpresponse=httpclient.execute(httpost);
		    System.out.println(EntityUtils.toString(httpresponse.getEntity()));
		    
		    //Handles what is returned from the page 
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/addNews",method = {RequestMethod.GET})
	public void addNews(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map articles=new HashMap();
		Map article=new HashMap();
		Map article1=new HashMap();
		article.put("thumb_media_id", "Q8PQ05g6Qse6bd3omybgQG8wykDu2bGiMXrTcoRocGc");
		article.put("author", "xxxxxxxxx");
		article.put("title", "titlexxx");
		article.put("content_source_url", "www.baidu.com");
		article.put("content", "xxxxxxxxxxxxxxx");
		article.put("digest", "描述");
		article.put("show_cover_pic", "1");
		article1.put("thumb_media_id", "Q8PQ05g6Qse6bd3omybgQG8wykDu2bGiMXrTcoRocGc");
		article1.put("author", "xxxxxxxxx");
		article1.put("title", "titlexxx");
		article1.put("content_source_url", "www.baidu.com");
		article1.put("content", "<button>1111</button>");
		article1.put("digest", "描述");
		article1.put("show_cover_pic", "0");
		List list=new ArrayList();
		list.add(article);
		list.add(article1);
		articles.put("articles", list);
		String url =WxApiUtil.ADD_NEWS+WxApiUtil.ACCESS_TOKEN_TEST;
  		HttpResponse httpResponse;
		try {
			//httpResponse = HttpClientUtil.makeRequest(url,map);
			 //instantiates httpclient to make request
		    DefaultHttpClient httpclient = new DefaultHttpClient();

		    //url with the post data
		    HttpPost httpost = new HttpPost(url);
		    //passes the results to a string builder/entity
		    System.out.println("------uploadNews---------------------"+JSON.toJSONString(articles));
		    StringEntity se = new StringEntity(JSON.toJSONString(articles),"UTF-8");

		    //sets the post request as the resulting string
		    httpost.setEntity(se);
		    //sets a request header so the page receving the request
		    //will know what to do with it
		    httpost.setHeader("Accept", "application/json");
		    httpost.setHeader("Content-type", "application/json");
		    HttpResponse httpresponse=httpclient.execute(httpost);
		    System.out.println(EntityUtils.toString(httpresponse.getEntity()));
		    
		    //Handles what is returned from the page 
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	/*@RequestMapping(value = "/batchget_material",method = {RequestMethod.GET})
	public void batchget_material(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String url =WeChatApiUtil.UPLOAD_NEWS+WeChatApiUtil.ACCESS_TOKEN_TEST;
  		HttpResponse httpResponse;
		try {
			//httpResponse = HttpClientUtil.makeRequest(url,map);
			 //instantiates httpclient to make request
		    DefaultHttpClient httpclient = new DefaultHttpClient();

		    //url with the post data
		    HttpPost httpost = new HttpPost(url);
		    //passes the results to a string builder/entity
		    System.out.println("------batchget_material---------------------");
		    //sets a request header so the page receving the request
		    //will know what to do with it
		    httpost.setHeader("Accept", "application/json");
		    httpost.setHeader("Content-type", "application/json");
		    HttpResponse httpresponse=httpclient.execute(httpost);
		    System.out.println(EntityUtils.toString(httpresponse.getEntity()));
		    
		    //Handles what is returned from the page 
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/**
	 * {
	   "type":TYPE,
	   "offset":OFFSET,
	   "count":COUNT
		}
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/batchget_material",method = {RequestMethod.GET})
	public void batchget_material(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String url =WxApiUtil.BATCHGET_MATERIAL+WxApiUtil.ACCESS_TOKEN_TEST;
  		HttpResponse httpResponse;
		try {
			//httpResponse = HttpClientUtil.makeRequest(url,map);
			 //instantiates httpclient to make request
		    DefaultHttpClient httpclient = new DefaultHttpClient();

		    //url with the post data
		    HttpPost httpost = new HttpPost(url);
		    //passes the results to a string builder/entity
		    System.out.println("------batchget_material---------------------");
		    Map map = new HashMap();
		    map.put("type", "image");
		    map.put("offset", "0");
		    map.put("count", "20");
		    //sets a request header so the page receving the request
		    StringEntity se = new StringEntity(JSON.toJSONString(map),"UTF-8");

		    //sets the post request as the resulting string
		    httpost.setEntity(se);
		    //will know what to do with it
		    httpost.setHeader("Accept", "application/json");
		    httpost.setHeader("Content-type", "application/json");
		    HttpResponse httpresponse=httpclient.execute(httpost);
		    System.out.println(EntityUtils.toString(httpresponse.getEntity()));
		    
		    //Handles what is returned from the page 
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * {
   "touser":[
    "OPENID1",
    "OPENID2"
   ],
   "mpnews":{
      "media_id":"123dsdajkasd231jhksad"
   },
    "msgtype":"mpnews"
}
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/mass-send",method = {RequestMethod.GET})
	public void massSend(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Map all=new HashMap();
		Map media_id=new HashMap();
		media_id.put("media_id", "Q8PQ05g6Qse6bd3omybgQAajh1euM4gjlmRxl45LNiY");
		List touser=new ArrayList();
		touser.add("oupAet4plPZC43EvDtR_9m2HNjTQ");
		touser.add("oupAet4GXEI7dTjKgdPlEaDQ26as");
		all.put("touser", touser);
		all.put("mpnews", media_id);
		all.put("msgtype", "mpnews");
		String url =WxApiUtil.MASS_SEND+WxApiUtil.ACCESS_TOKEN_TEST;
  		HttpResponse httpResponse;
		try {
			//httpResponse = HttpClientUtil.makeRequest(url,map);
			 //instantiates httpclient to make request
		    DefaultHttpClient httpclient = new DefaultHttpClient();

		    //url with the post data
		    HttpPost httpost = new HttpPost(url);
		    //passes the results to a string builder/entity
		    System.out.println("------sendNews---------------------"+JSON.toJSONString(all));
		    StringEntity se = new StringEntity(JSON.toJSONString(all),"UTF-8");

		    //sets the post request as the resulting string
		    httpost.setEntity(se);
		    //sets a request header so the page receving the request
		    //will know what to do with it
		    httpost.setHeader("Accept", "application/json");
		    httpost.setHeader("Content-type", "application/json");
		    HttpResponse httpresponse=httpclient.execute(httpost);
		    System.out.println(EntityUtils.toString(httpresponse.getEntity()));
		    
		    //Handles what is returned from the page 
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
