package fightingman.ums.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fightingman.ums.model.User;


public class UserUtil {
	public static User getLoginUser(HttpServletRequest req){
		HttpSession session  = req.getSession();
		User user = (User)session.getAttribute("user");
		return user;
	}
}
