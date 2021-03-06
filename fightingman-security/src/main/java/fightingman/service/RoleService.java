package fightingman.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import dao.JdbcService;
import fightingman.model.Function;
import fightingman.model.Role;
import fightingman.model.User;
import fightingman.model.UserRole;
import fightingman.util.UserUtil;
import util.SpringUtil;

/**
 * @author liangzhenghui
 * @date Aug 24, 2013    9:59:08 PM
 */
@Service
public class RoleService {
	
	private JdbcService jdbcService;

	/**
	 * 使用迪米特法则,也称为最小知识原则，一个对象应该对其他的对象有最少的了解
	 * 不应该直接在Jsp或者servlet中使用SpringUtil.getBean("xxxx")获取这个bean的实例
	 * 而是通过MenuService.getInstance()获取这个bean的实例
	 * @return
	 */
	public static final RoleService getInstance() {
		return (RoleService)SpringUtil.getBean("roleService");
	}
	
	public int createRole(String roleName) {
		User user = (User)UserUtil.getLoginUser();
		String sql = "insert into s_framework_role (id,role_name,creator,create_time) values(?,?,?,?)";
		Object[] args = new Object[]{UUID.randomUUID(), roleName,user.getUserid(),new Date()};
		int [] argTypes = new int[]{Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP};
		return jdbcService.update(sql, args, argTypes);
	}
	
	/**
	 * 
	 * @param roleId
	 * @param userId
	 * @param roleName
	 * @param userName
	 * @return
	 */
	public int roleGrantUser(String roleId, String userId,String roleName,String userName) {
		String sql = "insert into s_framework_user_role (id, user_id, role_id,role_name,user_name) values(?, ?, ?,?,?)";
		Object[] args = new Object[] {UUID.randomUUID(), userId, roleId,roleName,userName};
		int[] argTypes = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,Types.VARCHAR};
		return jdbcService.update(sql, args, argTypes);
	}
	
	public List<Role> getAllRoles() {
		String sql = "select * from s_framework_role where delete_flag='0'";
		Object [] args = new Object[] {};
		return (List<Role>)jdbcService.queryForList(sql, args ,new Role());
	}
	
	public List<Role> getRolesByPage(int page, int size) {
		String sql = "select * from (select * from s_framework_role where delete_flag='0') t limit ?,?";
		Object[] args = new Object[] { (page - 1) * size, size };
		return jdbcService.queryForList(sql, args, new Role());
	}
	
	public int getCount() {
		String sql = "select count(*) from s_framework_role where delete_flag='0'";
		Object[] args = new Object[] {};
		return jdbcService.count(sql, args);
	}
	
	
	
	public List getRolesByUserId(String userId) {
		String sql = "select * from s_framework_user_role where user_id = ? and delete_flag='0'";
		Object[] args = new Object[]{userId};
		List list = jdbcService.queryForList(sql, args, new UserRole());
		List<Role> roleList = new ArrayList<Role>();
		for(Object object : list) {
			UserRole userRole = (UserRole)object;
			if(null!=userRole){
				roleList.add(getRoleById(userRole.getRoleId()));
			}
		}
		return roleList;
	}
	
	/**
	 * @param string
	 * @return
	 */
	public Role getRoleById(String roleId) {
		String sql = "select * from s_framework_role  where id = ? and delete_flag='0'";
		Object[] args =new Object[]{roleId};
		List list =  jdbcService.queryForList(sql, args, new Role());
		Role role = null;
		if(list != null && list.size() > 0) {
			role = (Role)list.get(0);
		}
		return role;
	}
	/**
	 * 删除角色,同时删除user_role,role_menu中所有包含roleId的记录
	 * @param roleId
	 */
	public void deleteRole(String roleId) {
		//删除role表单中与roleId有关的记录
		String sql =  null;
		Object[] args = null;
		int[] argTypes = null;
		sql = "update  s_framework_role set   delete_flag='1' where id = ? ";
		args = new Object[]{roleId};
		argTypes = new int[]{Types.VARCHAR};
		jdbcService.update(sql, args, argTypes);
		//删除role_menu表单中的所有包含roleId的记录
		sql = "update   s_framework_role_menu  set delete_flag='1' where role_id = ? ";
		jdbcService.update(sql, args, argTypes);
		//删除user_role表单中所有包含roleId的记录
		sql = "update  s_framework_user_role set delete_flag='1'  where role_id = ? ";
		jdbcService.update(sql, args, argTypes);
	}
	
	public boolean roleNameIsExits(String roleName) {
		String sql = "select count(*) from s_framework_role where role_name=? and delete_flag='0'";
		int count = jdbcService.count(sql, new Object[] { roleName });
		if (count > 0) {
			return true;
		} else {
			return false;
		}
		
	}

	public boolean userHasTheRole(String user_id,String role_id) {
		String sql = "select count(*) from s_framework_user_role where user_id=? and role_id=? and delete_flag='0'";
		int count = jdbcService.count(sql, new Object[]{user_id,role_id});
		if(count > 0){
			return true;
		}else{
			return false;
		}
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
						role.setRoleName(rs.getString("ROLE_NAME"));
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
