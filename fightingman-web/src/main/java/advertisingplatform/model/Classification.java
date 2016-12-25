package advertisingplatform.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ums.model.Function;

/**
 * @author: liangzhenghui
 * @blog: http://my.oschina.net/liangzhenghui/blog
 * @email:715818885@qq.com 2015年1月31日 下午4:07:51
 */
public class Classification implements RowMapper {
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Classification classification= new Classification();
		classification.setId(rs.getString("id"));
		classification.setName(rs.getString("name"));
		return classification;
	}

}
