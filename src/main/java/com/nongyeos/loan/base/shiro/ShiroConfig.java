package com.nongyeos.loan.base.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;

@Configuration
public class ShiroConfig {
	@Bean(name="shirFilter")
	public ShiroFilterFactoryBean shirFilter(@Qualifier("securityManager") SecurityManager securityManager) {
		
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		// 仍然是通過controller請求响应  
		shiroFilterFactoryBean.setLoginUrl("/");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/user/main");
		//未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/user/403");
		
		//自定义拦截器
		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/admin/**", "anon");
		filterChainDefinitionMap.put("/user/login", "anon");
		filterChainDefinitionMap.put("/app/**", "anon");
		filterChainDefinitionMap.put("/resource/**", "anon");
		filterChainDefinitionMap.put("/getVerify", "anon");
		filterChainDefinitionMap.put("/asyncInter/**", "anon");//业务系统异步统一回调地址
		filterChainDefinitionMap.put("/nj/**", "anon");
		filterChainDefinitionMap.put("/appVersion/download/**", "anon");
		filterChainDefinitionMap.put("/appVersion/load/**", "anon");
		//配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/logout", "logout");
		//<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		//<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		//移动端权限认证，异常处理
		Map<String,Filter> defineFilter = new LinkedHashMap<String, Filter>();
		defineFilter.put("nj", nj());
		shiroFilterFactoryBean.setFilters(defineFilter);
		return shiroFilterFactoryBean;
	}
	
	@Bean(name="nj")
    public CheckAppTokenFilter nj() {
        return new CheckAppTokenFilter();
    }
	
	//配置核心安全事务管理器
	@Bean(name="securityManager")
	public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm){
		System.err.println("--------------shiro已经加载----------------");
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		securityManager.setRealm(authRealm);
		// 自定义缓存实现 使用redis
        //securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        //securityManager.setSessionManager(sessionManager());
        //注入记住我管理器;
        //securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	//配置自定义的权限登录器
	@Bean(name="authRealm")
	public AuthRealm authRealm(){
		return new AuthRealm();
	}
    
	/**
	 *  开启shiro aop注解支持.
	 * @param securityManager
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(manager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean(name="simpleMappingExceptionResolver")
	public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
		mappings.setProperty("UnauthorizedException","403");
		r.setExceptionMappings(mappings);  // None by default
		r.setDefaultErrorView("error");    // No default
		r.setExceptionAttribute("ex");     // Default is "exception"
		//r.setWarnLogCategory("example.MvcLogger");     // No default
		return r;
	}
}