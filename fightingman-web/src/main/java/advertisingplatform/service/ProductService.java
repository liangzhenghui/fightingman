package advertisingplatform.service;

import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

import advertisingplatform.model.Classification;
import advertisingplatform.model.Product;
import dao.JdbcService;

/**
 * @author: liangzhenghui
 * @blog: http://my.oschina.net/liangzhenghui/blog
 * @email:715818885@qq.com 2015年1月31日 下午3:12:11
 */
public class ProductService {
	private JdbcService jdbcService;

	/**
	 * 创建产品分类
	 * 
	 * @param classification
	 * @return
	 */
	public int createClassification(String classification, String id) {
		String sql = "insert into b_classification(id,name,create_time) values(?,?,?)";
		return jdbcService.update(sql, new Object[] { id, classification,
				new Date() }, new int[] { Types.VARCHAR, Types.VARCHAR,
				Types.TIMESTAMP });
	}

	/**
	 * 判断该类别是否存在
	 * 
	 * @param name
	 * @return
	 */
	public boolean isClassificationExits(String name) {
		String sql = "select count(*) from b_classification t where t.name=? and t.delete_flag='0'";
		int count = jdbcService.count(sql, new Object[] { name });
		return count >= 1 ? true : false;
	}

	public int editClassification(String id, String name) {
		String sql = "update b_classification set name = ? where id = ? and delete_flag='0'";
		Object[] args = new Object[] { name, id };
		int[] argTypes = new int[] { Types.VARCHAR, Types.VARCHAR };
		return jdbcService.update(sql, args, argTypes);
	}

	public int getClassificationCount() {
		String sql = "select count(*) from b_classification where delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.count(sql, args);
	}

	public List<Product> getClassificationByPage(int page, int size) {
		String sql = "select * from (select * from b_classification where delete_flag='0') t limit ?,?";
		Object[] args = new Object[] { (page - 1) * size, size };
		return jdbcService.queryForList(sql, args, new Classification());
	}

	public int deleteClassification(String id) {
		String sql = "update b_classification set delete_flag='1' where id=? ";
		Object[] args = new Object[] { id };
		int result = 0;
		int[] argTypes = new int[] { Types.VARCHAR };
		jdbcService.update(sql, args, argTypes);
		List list = getProductByLbId(id);
		for (Object object : list) {
			Map map = (Map) object;
			String productId = (String) map.get("id");
			if (StringUtils.isNotBlank(productId)) {
				deleteImagesOfProduct(productId);
			}
		}
		// 先干掉儿子，再干掉父亲
		deleteProductByLb(id);
		result = 1;
		return result;
	}

	public int deleteImagesOfProduct(String productId) {
		String sql = "delete from s_file  where bussiness_id=?";
		return jdbcService.update(sql, new Object[] { productId },
				new int[] { Types.VARCHAR });
	}

	public int deleteProductByLb(String lbId) {
		String sql = "update b_product set delete_flag='1' where lb_id=?";
		return jdbcService.update(sql, new Object[] { lbId },
				new int[] { Types.VARCHAR });
	}

	public int createProduct(String id, JSONObject json) {
		String product_classification_id = json
				.getString("product_classification_id");
		String product_name = json.getString("product_name");
		String product_description = json.getString("product_description");
		String product_classification_zw = json
				.getString("product_classification_zw");
		String sql = "insert into b_product(id,lb_id,lb_zw,product_name,product_description) values(?,?,?,?,?)";
		return jdbcService.update(sql, new Object[] { id,
				product_classification_id, product_classification_zw,
				product_name, product_description }, new int[] { Types.VARCHAR,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });

	}

	public int EditProduct(String id, JSONObject json) {
		String product_classification_id = json
				.getString("lb_id");
		String product_name = json.getString("productName");
		String product_description = json.getString("productDescription");
		String product_classification_zw = json
				.getString("lb_zw");
		String sql = "update b_product set lb_id=?,lb_zw=?,product_name=?,product_description=?,update_time=? where id=?";
		return jdbcService.update(sql, new Object[] {
				product_classification_id, product_classification_zw,
				product_name, product_description, new Date(), id }, new int[] {
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
				Types.TIMESTAMP, Types.VARCHAR });
	}

	public List getClassification() {
		String sql = "select t.id as code ,t.name as detail from b_classification t where delete_flag='0' order by t.create_time asc";
		return jdbcService.queryForList(sql, new Object[] {});
	}

	public List getProductByLb(String lb) {
		String sql = "select t.* from b_product t where t.delete_flag='0' and t.lb_zw=? order by t.create_time asc";
		return jdbcService.queryForList(sql, new Object[] { lb });
	}

	public List getProductByLbId(String lbId) {
		String sql = "select t.* from b_product t where t.delete_flag='0' and t.lb_id=?";
		return jdbcService.queryForList(sql, new Object[] { lbId });
	}

	public Map getProductById(String productId) {
		String sql = "select t.* from b_product t where t.delete_flag='0' and t.id=?";
		Map map = jdbcService
				.queryForSingleRow(sql, new Object[] { productId });
		return map;
	}
	
	public List getProductDetialDataById(String productId) {
		String sql = "select t1.product_name,t1.product_description,t2.id as imageId from b_product t1 left join  s_file t2 on t1.id=t2.bussiness_id where t1.delete_flag='0' and t2.delete_flag='0' and t1.id=?";
		List mapList = jdbcService
				.queryForList(sql, new Object[] { productId });
		return mapList;
	}

	public List<Product> getProductByPage(int page, int size) {
		String sql = "select * from (select * from b_product where delete_flag='0' order by create_time asc) t limit ?,?";
		Object[] args = new Object[] { (page - 1) * size, size };
		return jdbcService.queryForList(sql, args, new Product());
	}

	public int getProductCount() {
		String sql = "select count(*) from b_product where delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.count(sql, args);
	}

	public int deleteProduct(String id) {
		String sql = "update b_product set delete_flag='1' where id=?";
		jdbcService.update(sql, new Object[] { id },
				new int[] { Types.VARCHAR });
		deleteImagesOfProduct(id);
		return 1;
	}

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}
}
