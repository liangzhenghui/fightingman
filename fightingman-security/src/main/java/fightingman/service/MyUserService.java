package fightingman.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dao.JdbcService;
import fightingman.model.User;
import fightingman.model.UserMapper;

/**
 * @Date:2018年10月10日
 * @Author:liangzhenghui
 * @Github:https://github.com/liangzhenghui
 * @Email:liangzhenghui@gmail.com
 * @Description:
 */
@Service
public class MyUserService {
	private JdbcService jdbcService;

	public JdbcService getJdbcService() {
		return jdbcService;
	}

	public void setJdbcService(JdbcService jdbcService) {
		this.jdbcService = jdbcService;
	}

	public User getUser(String username) {
		String sql = "select * from user where username = ? ";
		Object[] args = new Object[] { username };
		List list = jdbcService.queryForList(sql, args, new UserMapper());
		User user = null;
		if (list != null && list.size() != 0) {
			return (User) list.get(0);
		}
		return user;
	}
}
