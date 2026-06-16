/**
 * 文件说明：Shiro 权限配置类，配置登录认证和接口访问规则。
 */
package com.example.drone.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.drone.security.ApiUserFilter;
import com.example.drone.security.UserRealm;

@Configuration
public class ShiroConfig {

    /**
     * 创建 Shiro 的用户认证对象。
     *
     * username 和 password 来自 application.yml。
     * 当前项目只有一个管理员账号，所以这里直接用配置里的账号密码判断。
     */
    @Bean
    public Realm userRealm(@Value("${app.security.username}") String username,
                           @Value("${app.security.password}") String password) {
        return new UserRealm(username, password);
    }

    /**
     * SecurityManager 是 Shiro 的核心管理器。
     * 它会调用上面的 UserRealm 来完成登录认证和权限判断。
     */
    @Bean
    public SecurityManager securityManager(Realm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 配置哪些接口需要登录、哪些接口可以直接访问。
     *
     * /api/auth/login、/api/auth/logout、/api/auth/me 允许匿名访问；
     * /api/** 下面其他接口都要经过 ApiUserFilter 检查；
     * 其他静态页面不拦截。
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         @Value("${app.security.api-token}") String apiToken) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        // 自定义过滤器支持前端传 X-Auth-Token，方便前后端分离页面调用接口。
        filters.put("apiAuthc", new ApiUserFilter(apiToken));
        bean.setFilters(filters);

        Map<String, String> chain = new LinkedHashMap<String, String>();
        chain.put("/api/auth/login", "anon");
        chain.put("/api/auth/logout", "anon");
        chain.put("/api/auth/me", "anon");
        chain.put("/api/**", "apiAuthc");
        chain.put("/**", "anon");
        bean.setFilterChainDefinitionMap(chain);
        return bean;
    }
}
