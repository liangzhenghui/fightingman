package fightingman.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Date:2018年10月11日
 * @Author:liangzhenghui
 * @Github:https://github.com/liangzhenghui
 * @Email:liangzhenghui@gmail.com
 * @Description:
 */
public class UserDetail extends org.springframework.security.core.userdetails.User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String userid;

	public UserDetail(String id, String userid, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id=id;
		this.userid=userid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}
