package advertisingplatform.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ums.model.Dictionary;
import ums.service.DictionaryService;
import advertisingplatform.service.FileService;
import advertisingplatform.utils.ParametersUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author: liangzhenghui
 * @blog: http://my.oschina.net/liangzhenghui/blog
 * @email:715818885@qq.com
 * 2015年2月1日 上午1:51:22
 */
@Controller
public class FileController {
	private final static Logger log = LogManager.getLogger(FileController.class);
	@Resource
	private FileService fileService;
	@Resource
	private DictionaryService dictionaryService;
	
	@RequestMapping(value = "/product-img-upload")
	public void productImgUpload(
			@RequestParam(value = "file") MultipartFile file,
			HttpServletRequest request) {
		String product_id = request.getParameter("productId");
		fileService.saveFile(product_id,file);
	}
	
	@RequestMapping(value = "/front-cover-img")
	public void getFrontCoverImg(HttpServletResponse response,HttpServletRequest request,
			@RequestParam("bussinessId") String bussinessId) {
		if (StringUtils.isNotBlank(bussinessId)) {
			Map map = fileService.getCoverImageByBussinessId(bussinessId);
			if (null != map) {
				byte[] bs = (byte[]) map.get("content");
				try {
					response.setContentType("image/jpg;");
					OutputStream outs;
					outs = response.getOutputStream();
					outs.write(bs);
					outs.flush();
					outs.close();
				}catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}else {
				Map map1 = fileService.getContentByBussinessId(bussinessId);
				if(null!=map1){
					byte[] bs = (byte[]) map1.get("content");
					try {
						response.setContentType("image/jpg;");
						OutputStream outs;
						outs = response.getOutputStream();
						outs.write(bs);
						outs.flush();
						outs.close();
					}catch (IOException e) {
						log.error(e.getMessage(), e);
					}
				}else{
					readNoPic(request,response);
				}
				
			}
		}
	}
	
	@RequestMapping(value = "/get-all-scrolling-images")
	public ModelAndView getAllScrollingImages() {
		ModelAndView model = new ModelAndView();
		List list = fileService.getAllScrollingImages();
		model.addObject("list",list );
		return model;
	}
	
	@RequestMapping(value = "/get-all-home-images")
	public ModelAndView getAllHomeImages() {
		ModelAndView model = new ModelAndView();
		List list = fileService.getAllHomeImages();
		model.addObject("list",list );
		return model;
	}
	
	@RequestMapping(value = "/img")
	public void getImgByImgId(HttpServletResponse response,HttpServletRequest request,
			@RequestParam("imgId") String imgId) {
		if (StringUtils.isNotBlank(imgId)) {
			Map map = fileService.getContentByFileId(imgId);
			if (null != map) {
				byte[] bs = (byte[]) map.get("content");
				try {
					response.setContentType("image/jpg;");
					OutputStream outs;
					outs = response.getOutputStream();
					outs.write(bs);
					outs.flush();
					outs.close();
				}catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}else{
				readNoPic(request,response);
			}
		}
	}
	
	public void readNoPic(HttpServletRequest request, HttpServletResponse response) {
		String fileDir = request.getSession().getServletContext().getRealPath("/") + File.separator + "images" + File.separator
				+ "zwtp.png";
		response.setContentType("image/jpg");
		ServletOutputStream sos;
		try {
			sos = response.getOutputStream();

			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			FileInputStream fis = new FileInputStream(new File(fileDir));
			byte[] buf = new byte[fis.available()];
			fis.read(buf);
			response.setContentLength(buf.length);
			sos.write(buf);
			sos.close();
			fis.close();
		} catch (IOException e) {
			log.error("无图片读取出错！", e);
		}
	}
	
	@RequestMapping(value = "/get-images-of-product")
	public ModelAndView getProductImagesByBussinessId(
			@RequestParam(value = "id") String id,
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		List list = fileService.getFilesByBussinessId(id);
		model.addObject("list",list );
		return model;
	}
	
	@RequestMapping(value = "/images-list-of-product")
	public ModelAndView productList(@RequestParam("page") String page,
			@RequestParam("rows") String rows,@RequestParam("id") String productId) {
		ModelAndView model = new ModelAndView();
		List imagesList = fileService.getImagesOfProductByPage(Integer.parseInt(page),
				Integer.parseInt(rows),productId);
		int total = fileService.getImagesOfProductCount(productId);
		model.addObject("rows", imagesList);
		model.addObject("total", total);
		return model;
	}
	
	
	@RequestMapping(value = "/get-image-type")
	public void getImageType(HttpServletResponse resp,@RequestParam("imageId") String imageId,@RequestParam("imageType") String imageType)
			throws IOException {
		Map fileMap = fileService.getFileById(imageId);
		List list = dictionaryService.getDictionaryByKind("judgement");
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : list) {
			Map map = (Map) object;
			data = new Dictionary();
			if (null!=fileMap&&null!=map) {
				String id = (String)map.get("code");
				String rolling_image = (String)fileMap.get(imageType);
				if(StringUtils.isNotBlank(id)&&id.equals(rolling_image)){
					data.setSelected(true);
				}
			}
			data.setCode((String) map.get("code"));
			data.setDetail((String) map.get("detail"));
			dataList.add(data);
		}
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(dataList));
	}
	

	
	@RequestMapping(value = "/image-edit")
	public ModelAndView imageEdit(@RequestParam("data") String data,
			HttpServletRequest request) {
		JSONObject json = JSONObject.parseObject(data);
		boolean success = false;
		ModelAndView model = new ModelAndView();
		int result = fileService.editImage(json);
		success=(result==1?true:false);
		model.addObject("result",success );
		return model;
	}
	
	@RequestMapping(value = "/image-delete")
	public ModelAndView imageDelete(@RequestParam("id") String id) {
		boolean success = false;
		ModelAndView model = new ModelAndView();
		int result = fileService.deleteImage(id);
		success=(result==1?true:false);
		model.addObject("result",success );
		return model;
	}
	
	@RequestMapping(value = "/app/images-list-of-product")
	public ModelAndView ImagesList(HttpServletRequest request) {
		JSONObject jsonObject = ParametersUtils.getTmpGtDatas(request);
		String productId = (String)jsonObject.get("productId");
		List imagesList = fileService.getImagesOfProduct(productId);
		ModelAndView model = new ModelAndView();
		Map map = new HashMap();
		Map gltMap = new HashMap();
		map.put("dataset", imagesList);
		gltMap.put("ncm_glt_imagesList", map);
		model.addObject("datas",gltMap);
		model.addObject("flag","1");
		return model;
	}
}
