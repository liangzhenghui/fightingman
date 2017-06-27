package salesplatform.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author: liangzhenghui
 * @blog: http://my.oschina.net/liangzhenghui/blog
 * @email:715818885@qq.com
 * 2015年2月1日 下午11:52:45
 */
public class Product implements RowMapper{
	private String id;
	private String productName;
	private String lbId;
	private String lbZw;
	private String productDescription;

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Product product= new Product();
		product.setId(rs.getString("id"));
		product.setProductName(rs.getString("product_name"));
		product.setLbId(rs.getString("lb_id"));
		product.setLbZw(rs.getString("lb_zw"));
		product.setProductDescription(rs.getString("product_description"));
		return product;
	}

	public String getId() {
		return id;
	}

	public String getProductName() {
		return productName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getLbId() {
		return lbId;
	}

	public String getLbZw() {
		return lbZw;
	}

	public void setLbId(String lbId) {
		this.lbId = lbId;
	}

	public void setLbZw(String lbZw) {
		this.lbZw = lbZw;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

}
