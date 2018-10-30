package fightingman.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @Date:2018年10月10日
 * @Author:liangzhenghui
 * @Github:https://github.com/liangzhenghui
 * @Email:liangzhenghui@gmail.com
 * @Description:
 */
public class UserMapper implements RowMapper {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getString("id"));
		user.setUserid(rs.getString("user_id"));
		user.setUsername(rs.getString("user_name"));
		user.setPassword(rs.getString("password"));
		user.setMobile(rs.getString("mobile"));
		return user;
	}
}
