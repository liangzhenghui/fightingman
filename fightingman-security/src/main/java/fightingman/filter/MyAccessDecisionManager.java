package fightingman.filter;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class MyAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
		if(configAttributes == null) {
			return;
		}
		for(ConfigAttribute ca : configAttributes) {
			String needRole = ((SecurityConfig)ca).getAttribute();
			if(needRole.equals("ROLE_ANONYMOUS")) {
				return;
			}
			
			for(GrantedAuthority ga : authentication.getAuthorities()) {
				//在CustomUserDetailsService中已经默认给每个用户添加了ROLE_USER角色,所以这里需要添加过滤掉ROLE_USER角色
				if(!needRole.equals("ROLE_USER")&&needRole.equals(ga.getAuthority())) {
					return;
				}
			}
		}
		
		throw new AccessDeniedException("Intercept!!!!!");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
	
}
