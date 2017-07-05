package salesplatform.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.im4java.core.IM4JavaException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import salesplatform.service.FileService;
import ums.model.Dictionary;
import ums.service.DictionaryService;
import util.ConfigurationUtil;
import util.ImageMagick;

@Controller
@RequestMapping("/api")
public class FileController {
	private final static Logger log = LogManager.getLogger(FileController.class);
	@Resource
	private FileService fileService;
	@Resource
	private DictionaryService dictionaryService;

	@RequestMapping(value = "/product-img-upload")
	public void productImgUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request)
			throws IOException {
		String original_name = file.getOriginalFilename();
		String type = original_name.substring(original_name.indexOf(".") + 1);
		String product_id = request.getParameter("productId");
		//String filePath = request.getSession().getServletContext().getRealPath("/");
		/*
		 * filePath += "images" + File.separator +
		 * DateUtils.DateToStr(currenTime, "yyyyMMdd");
		 */
		org.springframework.core.io.Resource resource= new ClassPathResource("config.properties");
		Properties props;
		props = PropertiesLoaderUtils.loadProperties(resource);
		String filePath = props.get("filepath").toString();
		// 构造路径以及文件夹
		File fileDirectory = new File(filePath);
		if (!fileDirectory.exists()) {
			fileDirectory.mkdirs();
		}
		// 构造名字
		String fileName = new Date().getTime() + "." + type;
		// 保存到本地
		fileService.saveInputStreamToFile(file.getInputStream(), filePath, fileName);
		fileService.saveThumbnail(filePath, fileName);
		// 记录信息到数据库
		fileService.saveFile(product_id, original_name, fileName, type, filePath);
	}

	/**
	 * 如果数据库用blog字段存放文件，通过此方式获取封面图片
	 * 
	 * @param response
	 * @param request
	 * @param bussinessId
	 */
	@RequestMapping(value = "/front-cover-img")
	public void getFrontCoverImg(HttpServletResponse response, HttpServletRequest request,

			@RequestParam("bussinessId") String bussinessId) {
		if (StringUtils.isNotBlank(bussinessId)) {
			Map map = fileService.getCoverImageByBussinessId(bussinessId);
			if (null != map) {
				String directory=(String)map.get("directory");
				String file_name=(String)map.get("file_name");
				try {
					response.setContentType("image/jpg;");
					OutputStream outs;
					outs = response.getOutputStream();
					directory=directory+File.separator+"small"+File.separator+file_name;
					FileInputStream fis = new FileInputStream(new File(directory));
					byte[] b = new byte[1024];
					int n;
					while ((n = fis.read(b)) != -1) {
						outs.write(b, 0, n);
					}
					outs.flush();
					outs.close();
					fis.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			} else { // 如果没设置封面图片获取第一张图片作为图片 
				Map map1 =fileService.getRandomImageBussinessId(bussinessId);
				if (null != map1) {
					String directory=(String)map1.get("directory");
					String file_name=(String)map1.get("file_name");
					directory=directory+File.separator+"small"+File.separator+file_name;
					try {
						response.setContentType("image/jpg;");
						OutputStream outs;
						outs = response.getOutputStream();
						FileInputStream fis = new FileInputStream(new File(directory));
						byte[] b = new byte[1024];
						int n;
						while ((n = fis.read(b)) != -1) {
							outs.write(b, 0, n);
						}
						outs.flush();
						outs.close();
						fis.close();
					} catch (IOException e) {
						log.error(e.getMessage(), e);
					}
				} else {
					readNoPic(request, response);
				}

			}
		}
	}

	@RequestMapping(value = "/get-all-scrolling-images")
	public ModelAndView getAllScrollingImages() {
		ModelAndView model = new ModelAndView();
		List list = fileService.getAllScrollingImages();
		model.addObject("list", list);
		return model;
	}

	@RequestMapping(value = "/get-all-scrolling-images-app")
	public ModelAndView getAllScrollingImagesForApp() {
		ModelAndView model = new ModelAndView();
		List list = fileService.getAllScrollingImages();
		Map map = new HashMap();
		Map gltMap = new HashMap();
		map.put("dataset", list);
		gltMap.put("ncm_glt_slideImgs", map);
		model.addObject("datas", gltMap);
		model.addObject("flag", "1");
		return model;
	}

	@RequestMapping(value = "/get-all-home-images")
	public ModelAndView getAllHomeImages() {
		ModelAndView model = new ModelAndView();
		List list = fileService.getAllHomeImages();
		model.addObject("list", list);
		return model;
	}

	@RequestMapping(value = "/img")
	public void getImgByImgId(HttpServletResponse response, HttpServletRequest request,
			@RequestParam("imgId") String imgId, @RequestParam(required=false,value="type") String type) {
		if (StringUtils.isNotBlank(imgId)) {
			Map map = fileService.getProductImageInfo(imgId);
			String dictory = (String) map.get("directory");
			String file_name = (String) map.get("file_name");
			if (null != map) {
				if (StringUtils.isNotBlank(type)&&type.equals("small")) {
					dictory = dictory + File.separator + "small" + File.separator + file_name;
				} else if (StringUtils.isNotBlank(type)&&type.equals("medium")) {
					dictory = dictory + File.separator + "medium" + File.separator + file_name;
				} else if (StringUtils.isNotBlank(type)&&type.equals("large")) {
					dictory = dictory + File.separator + "large" + File.separator + file_name;
				} else {
					dictory = dictory + File.separator + file_name;
				}
			}
			try {
				File file = new File(dictory);
				response.setContentType("image/jpg;");
				OutputStream outs = null;
				outs = response.getOutputStream();
				FileInputStream fis = new FileInputStream(file);
				byte[] b = new byte[1024];
				int n;
				while ((n = fis.read(b)) != -1) {
					outs.write(b, 0, n);
				}
				outs.flush();
				outs.close();
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/*
	 * @RequestMapping(value = "/img") public void
	 * getImgByImgId(HttpServletResponse response, HttpServletRequest
	 * request, @RequestParam("imgId") String imgId) { if
	 * (StringUtils.isNotBlank(imgId)) { Map map =
	 * fileService.getContentByFileId(imgId); if (null != map) { byte[] bs =
	 * (byte[]) map.get("content"); try { response.setContentType("image/jpg;");
	 * OutputStream outs; outs = response.getOutputStream(); outs.write(bs);
	 * outs.flush(); outs.close(); } catch (IOException e) {
	 * log.error(e.getMessage(), e); } } else { readNoPic(request, response); }
	 * } }
	 */

	public void readNoPic(HttpServletRequest request, HttpServletResponse response) {
		Properties proper=ConfigurationUtil.getConfiguration();
		String dictory=proper.get("filepath").toString()+File.separator + "zwtp.png";
		response.setContentType("image/jpg");
		ServletOutputStream sos;
		try {
			sos = response.getOutputStream();
			File file = new File(dictory);
			response.setContentType("image/jpg;");
			OutputStream outs = null;
			outs = response.getOutputStream();
			FileInputStream fis = new FileInputStream(file);
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				outs.write(b, 0, n);
			}
			sos.close();
			fis.close();
		} catch (IOException e) {
			log.error("无图片读取出错！", e);
		}
	}

	@RequestMapping(value = "/get-images-of-product")
	public ModelAndView getProductImagesByBussinessId(@RequestParam(value = "id") String id,
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		List list = fileService.getFilesByBussinessId(id);
		model.addObject("list", list);
		return model;
	}

	@RequestMapping(value = "/images-list-of-product")
	public ModelAndView productList(@RequestParam("page") String page, @RequestParam("rows") String rows,
			@RequestParam("id") String productId) {
		ModelAndView model = new ModelAndView();
		List imagesList = fileService.getImagesOfProductByPage(Integer.parseInt(page), Integer.parseInt(rows),
				productId);
		int total = fileService.getImagesOfProductCount(productId);
		model.addObject("rows", imagesList);
		model.addObject("total", total);
		return model;
	}

	@RequestMapping(value = "/get-image-type")
	public void getImageType(HttpServletResponse resp, @RequestParam("imageId") String imageId,
			@RequestParam("imageType") String imageType) throws IOException {
		Map fileMap = fileService.getFileById(imageId);
		List list = dictionaryService.getDictionaryByKind("judgement");
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		for (Object object : list) {
			Dictionary dictionary = (Dictionary) object;
			if (null != fileMap && null != dictionary) {
				String id = dictionary.getCode();
				String rolling_image = (String) fileMap.get(imageType);
				if (StringUtils.isNotBlank(id) && id.equals(rolling_image)) {
					dictionary.setSelected(true);
				}
			}
			dataList.add(dictionary);
		}
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(dataList));
	}

	@RequestMapping(value = "/image-edit")
	public ModelAndView imageEdit(@RequestParam("data") String data, HttpServletRequest request) {
		JSONObject json = JSONObject.parseObject(data);
		boolean success = false;
		ModelAndView model = new ModelAndView();
		int result = fileService.editImage(json);
		success = (result == 1 ? true : false);
		model.addObject("result", success);
		return model;
	}

	@RequestMapping(value = "/image-delete")
	public ModelAndView imageDelete(@RequestParam("id") String id) {
		boolean success = false;
		ModelAndView model = new ModelAndView();
		int result = fileService.deleteImage(id);
		success = (result == 1 ? true : false);
		model.addObject("result", success);
		return model;
	}

	/**
	 * 获取封面图片
	 * 
	 * @param response
	 * @param request
	 * @param bussinessId
	 * @throws IM4JavaException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@RequestMapping(value = "/testIm4Java/resize")
	public ModelAndView testIm4Java() throws IOException, InterruptedException, IM4JavaException {
		String webRoot = "/usr/local/webapp/saleswebapp/WebRoot";
		ImageMagick.cropImageCenter(webRoot + "/a.jpg", webRoot + "/a1.jpg", 180, 180);
		ModelAndView model = new ModelAndView();
		return model;
	}
}
