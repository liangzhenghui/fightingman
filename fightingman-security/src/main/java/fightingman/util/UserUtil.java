package fightingman.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import fightingman.model.User;
import fightingman.model.UserDetail;

public class UserUtil {
	public static User getLoginUser(HttpServletRequest req) {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		return user;
	}

	public static User getLoginUser() {
		UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = new User();
		BeanUtils.copyProperties(userDetail, user);
		return user;
	}
}
