package com.md.basePlatform.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 基础配置，本期 API 匿名访问.
 */
@Configuration
public class ShiroConfig {

    /**
     * Shiro 安全管理器.
     *
     * @return SecurityManager
     */
    @Bean
    public SecurityManager securityManager() {
        return new DefaultWebSecurityManager();
    }

    /**
     * Shiro 过滤器链.
     *
     * @param securityManager 安全管理器
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChain = new LinkedHashMap<>();
        filterChain.put("/api/**", "anon");
        filterChain.put("/swagger-ui.html", "anon");
        filterChain.put("/swagger-resources/**", "anon");
        filterChain.put("/v2/api-docs", "anon");
        filterChain.put("/webjars/**", "anon");
        filterChain.put("/**", "anon");
        factoryBean.setFilterChainDefinitionMap(filterChain);
        return factoryBean;
    }
}
