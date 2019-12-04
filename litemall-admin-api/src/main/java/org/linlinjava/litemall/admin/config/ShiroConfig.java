package org.linlinjava.litemall.admin.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.linlinjava.litemall.admin.shiro.AdminAuthorizingRealm;
import org.linlinjava.litemall.admin.shiro.AdminWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 功能描述:
     * 自定义ream认证，授权
     * <br>
     * 〈〉
     * @Param:
     * @Return:
     * @Author: zbzbzzz
     * @Date: 2019/10/26 15:06
     */
    @Bean
    public Realm realm() {
        return new AdminAuthorizingRealm();
    }

    /**
     * 功能描述:
     * shiro拦截资源文件
     * <br>
     * 〈〉
     * @Param:
     * @Return:
     * @Author: zbzbzzz
     * @Date: 2019/10/26 15:05
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/admin/auth/login", "anon");
        filterChainDefinitionMap.put("/admin/auth/401", "anon");
        filterChainDefinitionMap.put("/admin/auth/index", "anon");
        filterChainDefinitionMap.put("/admin/auth/403", "anon");
        filterChainDefinitionMap.put("/admin/index/index", "anon");

        filterChainDefinitionMap.put("/admin/**", "authc");
        shiroFilterFactoryBean.setLoginUrl("/admin/auth/401");
        shiroFilterFactoryBean.setSuccessUrl("/admin/auth/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/admin/auth/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 功能描述:
     * 创建sessionManager
     * 管理session
     * <br>
     * 〈〉
     * @Param:
     * @Return:
     * @Author: zbzbzzz
     * @Date: 2019/10/26 15:07
     */
    @Bean
    public SessionManager sessionManager() {

        return new AdminWebSessionManager();
    }

    /**
     * 功能描述:
     * 设置SecurityManager
     * <br>
     * 〈〉
     * @Param:
     * @Return:
     * @Author: zbzbzzz
     * @Date: 2019/10/26 15:08
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 功能描述:
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     * <br>
     * 〈〉
     * @Param:
     * @Return:
     * @Author: zbzbzzz
     * @Date: 2019/10/26 15:11
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 功能描述:
     * 管理shiro bean生命周期
     * <br>
     * 〈〉
     * @Param:
     * @Return:
     * @Author: zbzbzzz
     * @Date: 2019/10/26 15:12
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
}
