package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.data.redis.connection.jedis.JedisUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 2017-02-15
 * @author liangzhenghui
 *
 */
public class WxApiUtil {
	
	public static Logger logger=Logger.getLogger(WxApiUtil.class);
	public static final String ACCESS_TOKEN_TEST = "4M75Tjr0OYZws-UlWJN9H8sL7bC1lVdYRikJPrCYLIxMA74Fo8IMK9yzjtRHHAEUCYoBECBeAB-3hZ2XKkmhHKHM62YED02XB2nygPWz2Gas3VEU-TN38EdUCL8_vJYkZGEcAAAHOM";
	// token 接口(GET)
	private static final String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
	// 素材上传(POST)https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
	private static final String UPLOAD_MEDIA = "https://api.weixin.qq.com/cgi-bin/media/upload";
	// 素材下载:不支持视频文件的下载(GET)
	private static final String DOWNLOAD_MEDIA = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=";

	public static final String KF = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

	public static final String SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

	public static final String UPLOAD_IMAGE = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=";

	public static final String UPLOAD_NEWS = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=";

	public static final String ADD_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=";

	public static final String ADD_NEWS = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=";

	public static final String BATCHGET_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=";

	public static final String MASS_SEND = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=";

	public static String getTokenUrl(String appId, String appSecret) {
		return String.format(ACCESS_TOKEN, appId, appSecret);
	}

	public static String getDownloadUrl(String token, String mediaId) {
		return String.format(DOWNLOAD_MEDIA, token, mediaId);
	}

	/**
	 * 通用接口获取Token凭证
	 *
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static String getToken() {
		//所有的请求都先从缓存里面取,没有再从接口获取
		Jedis jedis=JedisUtil.getJedis();
		String access_token=jedis.get("access_token");
		if(StringUtils.isNotBlank(access_token)){
			return access_token;
		}
		String appId = "wx31382b2308a2b3c8";
		String appSecret = "96a88d83a1f71173d095e06feafd2cb4";
		if (appId == null || appSecret == null) {
			return null;
		}

		String token = null;
		String tockenUrl = WxApiUtil.getTokenUrl(appId, appSecret);
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(tockenUrl);
		HttpResponse httpResponse;
		try {
			httpResponse = httpclient.execute(httpget);
			HttpEntity entity = httpResponse.getEntity();
			String entityString;
			entityString = EntityUtils.toString(entity);
			JSONObject jsonObject = (JSONObject) JSON.parseObject(entityString);
			if (null != jsonObject) {
				token = jsonObject.getString("access_token");
				if(StringUtils.isNotBlank(token)){
					jedis.set("access_token", token);
					//access_token是2小时,防止请求返回过程中消耗了时间所以设置为2个小时减10秒
					jedis.expire("access_token", 2*60*60-10);
				}
			}
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (JSONException e) {
			token = null;// 获取token失败
		}
		return token;
	}

	/**
	 * 微信服务器素材上传
	 *
	 * @param file
	 *            表单名称media
	 * @param token
	 *            access_token
	 * @param type
	 *            type只支持四种类型素材(video/image/voice/thumb)
	 */
	public static JSONObject uploadMedia(File file, String token, String type) {
		if (file == null || token == null || type == null) {
			return null;
		}

		if (!file.exists()) {
			System.out.println("上传文件不存在,请检查!");
			return null;
		}

		String url = UPLOAD_MEDIA;
		FilePart media;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost= new HttpPost();
		JSONObject jsonObject=null;
		try {
			MultipartEntity  entity = new MultipartEntity();
			entity.addPart("access_token",  
                    new StringBody(token, Charset.forName("UTF-8")));  
			entity.addPart("type",  
                    new StringBody(type, Charset.forName("UTF-8")));  
            entity.addPart("media", new FileBody(file, "image/*"));  
			httpPost.setEntity(entity);
			HttpResponse httpResponse;
			httpResponse = httpclient.execute(httpPost);
			HttpEntity result = httpResponse.getEntity();
			 String entityString = EntityUtils.toString(result);
			logger.info(entityString);
			jsonObject=JSON.parseObject(entityString);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 多媒体下载接口
	 *
	 * @param fileName
	 *            素材存储文件路径
	 * @param token
	 *            认证token
	 * @param mediaId
	 *            素材ID（对应上传后获取到的ID）
	 * @return 素材文件
	 * @comment 不支持视频文件的下载
	 */
	public static File downloadMedia(String fileName, String token, String mediaId) {
		String url = getDownloadUrl(token, mediaId);
		return httpRequestToFile(fileName, url, "GET", null);
	}

	/**
	 * 多媒体下载接口
	 *
	 * @param fileName
	 *            素材存储文件路径
	 * @param mediaId
	 *            素材ID（对应上传后获取到的ID）
	 * @return 素材文件
	 * @comment 不支持视频文件的下载
	 */
	public static File downloadMedia(String fileName, String mediaId) {
		String appId = "wxbe4d433e857e8bb1";
		String appSecret = "ccbc82d560876711027b3d43a6f2ebda";
		String token = WxApiUtil.getToken();
		return downloadMedia(fileName, token, mediaId);
	}

	/**
	 * 以http方式发送请求,并将请求响应内容输出到文件
	 *
	 * @param path
	 *            请求路径
	 * @param method
	 *            请求方法
	 * @param body
	 *            请求数据
	 * @return 返回响应的存储到文件
	 */
	public static File httpRequestToFile(String fileName, String path, String method, String body) {
		if (fileName == null || path == null || method == null) {
			return null;
		}

		File file = null;
		HttpURLConnection conn = null;
		InputStream inputStream = null;
		FileOutputStream fileOut = null;
		try {
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(method);
			if (null != body) {
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(body.getBytes("UTF-8"));
				outputStream.close();
			}

			inputStream = conn.getInputStream();
			if (inputStream != null) {
				file = new File(fileName);
			} else {
				return file;
			}

			// 写入到文件
			fileOut = new FileOutputStream(file);
			if (fileOut != null) {
				int c = inputStream.read();
				while (c != -1) {
					fileOut.write(c);
					c = inputStream.read();
				}
			}
		} catch (Exception e) {
		} finally {
			if (conn != null) {
				conn.disconnect();
			}

			/*
			 * 必须关闭文件流 否则JDK运行时，文件被占用其他进程无法访问
			 */
			try {
				inputStream.close();
				fileOut.close();
			} catch (IOException execption) {
			}
		}
		return file;
	}

	/**
	 * 上传素材
	 * 
	 * @param filePath
	 *            媒体文件路径(绝对路径)
	 * @param type
	 *            媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @return
	 */
	public static JSONObject uploadMedia(String filePath, String type) {
		File f = new File(filePath); // 获取本地文件
		String appId = "wxbe4d433e857e8bb1";
		String appSecret = "ccbc82d560876711027b3d43a6f2ebda";
		String token = WxApiUtil.getToken();
		JSONObject jsonObject = uploadMedia(f, token, type);
		return jsonObject;
	}

	/**
	 * 发送请求以https方式发送请求并将请求响应内容以String方式返回
	 *
	 * @param path
	 *            请求路径
	 * @param method
	 *            请求方法
	 * @param body
	 *            请求数据体
	 * @return 请求响应内容转换成字符串信息
	 */
	public static String httpsRequestToString(String path, String method, String body) {
		if (path == null || method == null) {
			return null;
		}

		String response = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		HttpsURLConnection conn = null;
		try {
			TrustManager[] tm = { new JEEWeiXinX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			System.out.println(path);
			URL url = new URL(path);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(method);
			if (null != body) {
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(body.getBytes("UTF-8"));
				outputStream.close();
			}

			inputStream = conn.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			response = buffer.toString();
		} catch (Exception e) {

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
			try {
				bufferedReader.close();
				inputStreamReader.close();
				inputStream.close();
			} catch (IOException execption) {

			}
		}
		return response;
	}

	public static void main(String[] args) throws Exception {
		// 媒体文件路径
		String filePath = "D:/JavaSoftwareDevelopeFolder/IntelliJ IDEA_Workspace/WxStudy/web/media/image/我.jpg";
		// String filePath = "D:/JavaSoftwareDevelopeFolder/IntelliJ
		// IDEA_Workspace/WxStudy/web/media/voice/voice.mp3";
		// String filePath = "D:\\JavaSoftwareDevelopeFolder\\IntelliJ
		// IDEA_Workspace\\WxStudy\\web\\media\\video\\小苹果.mp4";
		// 媒体文件类型
		String type = "image";
		// String type = "voice";
		// String type = "video";
		JSONObject uploadResult = uploadMedia(filePath, type);
		// {"media_id":"dSQCiEHYB-pgi7ib5KpeoFlqpg09J31H28rex6xKgwWrln3HY0BTsoxnRV-xC_SQ","created_at":1455520569,"type":"image"}
		System.out.println(uploadResult.toString());

		// 下载刚刚上传的图片以id命名
		String media_id = uploadResult.getString("media_id");
		File file = downloadMedia("D:/" + media_id + ".png", media_id);
		System.out.println(file.getName());

	}
}

class JEEWeiXinX509TrustManager implements X509TrustManager {
	public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}