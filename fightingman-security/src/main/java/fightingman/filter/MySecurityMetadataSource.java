package fightingman.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import fightingman.model.Role;
import fightingman.service.AuthorityService;

public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private static Map<RequestMatcher, Collection<ConfigAttribute>> resourceMap = null;
	private AuthorityService authorityService;

	public MySecurityMetadataSource() {
		loadResourceDefine();
	}

	/**
    * 根据相应的查询语句去数据库加载资源和权限 初始化map集合
    * 这里初始化map的key时 固定使用RequestMatcher接口中的AntPathRequestMatcher
    * RequestMatcher还有很多实现类 不过目前还不是很明确具体是如何使用和配置这些类 暂定固定使用固定使用RequestMatcher接口中的AntPathRequestMatcher
    *
    * 这个类会在web第一次启动的时候把权限和资源初始化 并缓存起来
    * 但是如果在后面的权限发生改变了，那么就会导致无法更新
    * 一种解决方案是：在getAttributes那里直接从数据库中查询相应的url权限
    * 另一种解决方案:在有更新权限和资源集合的时候 再次调用loadResourcesDefine去重新加载一次新的资源和权限集合
    */
	private void loadResourceDefine() {
		//List list = authorityService.getAllResource();
		resourceMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		Collection<ConfigAttribute> atts3 = new ArrayList<ConfigAttribute>();
		ConfigAttribute ca3 = new SecurityConfig("ROLE_ANONYMOUS");
		atts3.add(ca3);
		resourceMap.put(new AntPathRequestMatcher("/login.do*"), atts3);
		resourceMap.put(new AntPathRequestMatcher("/loginFail.do*"), atts3);

		Collection<ConfigAttribute> atts1 = new ArrayList<ConfigAttribute>();
		ConfigAttribute ca = new SecurityConfig("ADMIN");
		atts1.add(ca);
		resourceMap.put(new AntPathRequestMatcher("/lxj/activity/list.do"), atts1);
		/*
		 * ConfigAttribute ca1 = new SecurityConfig("USER"); atts2.add(ca1);
		 * resourceMap.put(new AnyRequestMatcher(), atts2);
		 */
		//{Ant [pattern='/login.do*']=[ROLE_ANONYMOUS], Ant [pattern='/loginFail.do*']=[ROLE_ANONYMOUS], Ant [pattern='/admin*']=[ADMIN]}
		System.out.println("loadResourceDefine-----");
	}

	// 获取访问资源权限
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		resourceMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
		List<fightingman.model.Resource> resources = authorityService.getAllResource();
		for(fightingman.model.Resource resource:resources) {
			List<Role> roles = authorityService.getAllRole(resource.getUrl());
			Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
			for(Role role:roles) {
				ConfigAttribute ca = new SecurityConfig(role.getName());
				atts.add(ca);
			}
			resourceMap.put(new AntPathRequestMatcher(resource.getUrl()), atts);
		}
		HttpServletRequest request = ((FilterInvocation) object).getRequest();
		System.out.println(((FilterInvocation) object).getRequestUrl());
		//Ant [pattern='/login.do*']=[ROLE_ANONYMOUS]
		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
			if (entry.getKey().matches(request))
				return entry.getValue();
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

}
