package fightingman.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;

import fightingman.model.Role;
import fightingman.model.User;
import fightingman.model.UserDetail;

@Service
public class CustomUserDetailsService extends JdbcDaoImpl {
	private static Logger log = Logger.getLogger(CustomUserDetailsService.class);
	@Resource
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		List<Role> roles = userService.getRoles(user.getId());
		if (roles != null) {
			for (Role role : roles) {
				authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
				log.info("userid:" + user.getUserid() + "rolename:" + role.getRoleName());
			}
			/*
			 * security 通过配置<intercept-url pattern="/**" access="hasRole('ROLE_USER')" />
			 *	可以令任何路径都需要重新登录
			 *  TODO 这样的做法是否正确?有其他解决办法吗?
			 */
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		return new UserDetail(user.getId(), user.getUserid(), user.getUsername(), user.getPassword(), authorities);
	}

}
