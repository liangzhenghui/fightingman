package salesplatform.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import salesplatform.service.FileService;
import salesplatform.service.ProductService;
import util.UUIDGenerator;

@Controller
public class ProductController {
	@Resource
	private ProductService productService;
	@Resource
	private FileService fileService;

	
	@RequestMapping(value = "/product-list")
	public ModelAndView productList(@RequestParam("page") String page,
			@RequestParam("rows") String rows) {
		ModelAndView model = new ModelAndView();
		List productList = productService.getProductByPage(Integer.parseInt(page),
				Integer.parseInt(rows));
		int total = productService.getProductCount();
		model.addObject("rows", productList);
		model.addObject("total", total);
		return model;
	}
	/**
	 * app产品查询列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/app/product-list")
	public ModelAndView productListForApp(@RequestParam("pageNum") String pageNum,
			@RequestParam("pageSize") String pageSize) {
		ModelAndView model = new ModelAndView();
		List productList = productService.getProductByPage(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));
		//设置封面图片(规则:如果有设定，就默认，没设定就将第一个图片设置为封面)
		for(Object object:productList){
			Map map = (Map)object;
			map.put("coverImageName", productService.setCoverImage((String)map.get("id")));
		}
		int total = productService.getProductCount();
		int totalPage = (total-1)/Integer.parseInt(pageSize)+1;
		model.addObject("productList", productList);
		model.addObject("total", totalPage);
		return model;
	}
	
	/**
	 * app产品详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/app/product-detail")
	public ModelAndView productDetailForApp(@RequestParam("id") String id) {
		ModelAndView model = new ModelAndView();
		Map map = productService.getProductById(id);
		List fileList = fileService.getFilesByBussinessId(id);
		model.addObject("fileList", fileList);
		model.addObject("product", map);
		return model;
	}

	
	@RequestMapping(value = "/product-create")
	public ModelAndView productCreate(@RequestParam("data") String data) throws ParseException {
		ModelAndView model = new ModelAndView();
		JSONObject json = JSONObject.parseObject(data);
		String id = UUIDGenerator.generateUUID();
		int result = productService.createProduct(id,json);
		model.addObject("result", result);
		model.addObject("id", id);
		return model;
	}
	

	@RequestMapping(value = "/product-edit")
	public ModelAndView productEdit(@RequestParam("data") String data) {
		ModelAndView model = new ModelAndView();
		boolean success  = false;
		JSONObject json = JSONObject.parseObject(data);
		String id = json.getString("product_id");
		int result = productService.EditProduct(id,json);
		success = result==1?true:false;
		model.addObject("result", success);
		model.addObject("id", id);
		return model;
	}
		
	@RequestMapping(value = "/product-delete")
	public ModelAndView ProductDelete(@RequestParam("id") String id) {
		ModelAndView model = new ModelAndView();
		int result = productService.deleteProduct(id);
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		return model;
	}
	/*
	@RequestMapping(value = "/get-product-by-lb")
	public ModelAndView getProductByLb(@RequestParam("lb") String lb)
			throws IOException {
		ModelAndView model = new ModelAndView();
		List ProductList = productService.getProductByLb(lb);
		model.addObject("list", ProductList);
		return model;
	}
	@RequestMapping(value = "/get-product-by-id")
	public ModelAndView getProductById(@RequestParam("id") String id)
			throws IOException {
		ModelAndView model = new ModelAndView();
		Map product = productService.getProductById(id);
		model.addObject("product", product);
		return model;
	}
	*//**
	 * 产品分类创建
	 * @param request
	 * @return
	 *//*
	@RequestMapping(value = "/product-classification-create")
	public ModelAndView productClassificationCreate(@RequestParam("data") String data) {
		JSONObject json = JSONObject.parseObject(data);
		String classification = json.getString("product_classification");
		ModelAndView model = new ModelAndView();
		boolean success = false;
		int result = 0;
		String id = UUID.randomUUID().toString();
		boolean isExits = productService.isClassificationExits(classification);
		if(!isExits){
			result = productService.createClassification(classification,id);
			model.addObject("id", id);
			success = (result==1)?true:false;
		}
		model.addObject("isExits", isExits);
		model.addObject("result", success);
		return model;
	}
	
	@RequestMapping(value = "/product-classification-list")
	public ModelAndView productClassificationList(@RequestParam("page") String page,
			@RequestParam("rows") String rows) {
		ModelAndView model = new ModelAndView();
		List classificationList = productService.getClassificationByPage(Integer.parseInt(page),
				Integer.parseInt(rows));
		int total = productService.getClassificationCount();
		model.addObject("rows", classificationList);
		model.addObject("total", total);
		return model;
	}
	
	@RequestMapping(value = "/classification-edit")
	public ModelAndView classificationEdit(HttpServletRequest req,
			@RequestParam("data") String data) {
		boolean success = false;
		JSONObject json = JSONObject.parseObject(data);
		ModelAndView model = new ModelAndView();
		String name = json.getString("name");
		String id = json.getString("id");
		int result = productService.editClassification(id,name);
		success = result==1?true:false;
		model.addObject("result", true);
		return model;
	}
	
	@RequestMapping(value = "/classification-delete")
	public ModelAndView classificationDelete(@RequestParam("id") String id) {
		ModelAndView model = new ModelAndView();
		int result = productService.deleteClassification(id);
		if (result == 1) {
			model.addObject("result", true);
		} else {
			model.addObject("result", false);
		}
		return model;
	}
	
	@RequestMapping(value = "/getClassification")
	public void getClassification(HttpServletResponse resp)
			throws IOException {
		List classificationList = productService.getClassification();
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : classificationList) {
			Map map = (Map) object;
			data = new Dictionary();
			data.setCode((String) map.get("code"));
			data.setDetail((String) map.get("detail"));
			dataList.add(data);
		}
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(JSON.toJSONString(dataList));
	}
	
	@RequestMapping(value = "/get-classification-edit")
	public void getClassificationEdit(HttpServletResponse resp,@RequestParam("productId") String productId)
			throws IOException {
		List classificationList = productService.getClassification();
		Map productMap = productService.getProductById(productId);
		List<Dictionary> dataList = new ArrayList<Dictionary>();
		Dictionary data = null;
		for (Object object : classificationList) {
			Map map = (Map) object;
			data = new Dictionary();
			if (null!=productMap&&null!=map) {
				String id = (String)map.get("code");
				String lb_id = (String)productMap.get("lb_id");
				if(StringUtils.isNotBlank(id)&&id.equals(lb_id)){
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
	
	@RequestMapping(value = "/get-all-classification")
	public ModelAndView getAllClassification()
			throws IOException {
		ModelAndView model = new ModelAndView();
		List classificationList = productService.getClassification();
		model.addObject("list", classificationList);
		return model;
	}
	*/
}
