package org.linlinjava.litemall.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    // 当前跨域请求最大有效时长。这里默认30天
    private long maxAge = 30 * 24 * 60 * 60;

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1 设置访问源地址
        corsConfiguration.addAllowedHeader("*"); // 2 设置访问源请求头
        corsConfiguration.addAllowedMethod("*"); // 3 设置访问源请求方法
        corsConfiguration.setMaxAge(maxAge);
        return corsConfiguration;
    }

    /**
     * 功能描述:
     * 放在shiro的监听器之前，这样就可以保证在所有请求到来的时候，都会给response加上跨域headers。
     * 解决跨域时发送的OPTIONS被shiro拦截的问题
     * <br>
     * 〈〉
     * @Param:
     * @Return:
     * @Author: zbzbzzz
     * @Date: 2019/11/6 17:33
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4 对接口配置跨域设置
        return new CorsFilter(source);
    }
}
