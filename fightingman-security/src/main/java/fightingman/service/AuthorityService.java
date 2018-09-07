package fightingman.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import dao.JdbcService;
import fightingman.model.Role;
import fightingman.model.User;
import fightingman.model.UserAuth;

public class AuthorityService {
	private JdbcService jdbcService;

	public User getUser(String username) {
		String sql = "select * from user where login_name=?";
		Map map = (Map) jdbcService.queryForSingleRow(sql, new Object[] { username });
		User user = null;
		if (map != null) {
			user = new User();
			// USER_ID=1, LOGIN_NAME=admin, PASSWORD=123456, GMT_CREATE=2018-01-15
			// 17:44:39.0
			user.setUser_id((long) map.get("USER_ID"));
			user.setLogin_name((String) map.get("LOGIN_NAME"));
			user.setPassword((String) map.get("PASSWORD"));
		}
		return user;
	}

	public List<UserAuth> queryAuthList(long user_id) {
		String sql = "select * from user_auth where user_id=?";
		@SuppressWarnings("unchecked")
		List<UserAuth> list = jdbcService.queryForList(sql, new Object[] { user_id }, new RowMapper<UserAuth>() {
			@Override
			public UserAuth mapRow(ResultSet rs, int index) throws SQLException {
				UserAuth userAuth = new UserAuth();
				userAuth.setAuth_code(rs.getString("AUTH_CODE"));
				userAuth.setUser_id(rs.getLong("USER_ID"));
				userAuth.setUser_auth_id(rs.getLong("USER_AUTH_ID"));
				return userAuth;
			}
		});
		return list;
	}

	/**
	 * 获取配置的所有url
	 * 
	 * @return
	 */
	public List<fightingman.model.Resource> getAllResource() {
		String sql = "select * from s_framework_function where delete_flag='0'";
		@SuppressWarnings("unchecked")
		List<fightingman.model.Resource> list = jdbcService.queryForList(sql, new Object[] {},
				new RowMapper<fightingman.model.Resource>() {
					@Override
					public fightingman.model.Resource mapRow(ResultSet rs, int index) throws SQLException {
						fightingman.model.Resource resource = new fightingman.model.Resource();
						resource.setUrl(rs.getString("URL"));
						return resource;
					}
				});
		return list;
	}
	
	public List<Role> getAllRole(String url) {
		String sql = "select * from s_framework_role t1,s_framework_role_menu t2,s_framework_menu t3,s_framework_function t4 where t1.id=t2.role_id and t2.menu_id=t3.id and t4.url=? and t3.function_id=t4.id and t1.delete_flag='0' and t2.delete_flag='0' and t3.delete_flag='0' and t4.delete_flag='0'";
		@SuppressWarnings("unchecked")
		List<Role> list = jdbcService.queryForList(sql, new Object[] { url },
				new RowMapper<Role>() {
					@Override
					public Role mapRow(ResultSet rs, int index) throws SQLException {
						Role role = new Role();
						role.setName(rs.getString("ROLE_NAME"));
						return role;
					}
				});
		return list;
	}
	

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

}
