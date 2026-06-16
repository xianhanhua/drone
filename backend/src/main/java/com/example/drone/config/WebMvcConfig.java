/**
 * 文件说明：Web MVC 配置类，注册跨域规则和请求日志拦截器。
 */
package com.example.drone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.drone.interceptor.RequestLoggingInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 跨域配置，里面保存允许访问后端接口的前端地址。
     */
    private final CorsProperties corsProperties;

    /**
     * 请求日志拦截器，每次访问 /api/** 时打印请求信息。
     */
    private final RequestLoggingInterceptor requestLoggingInterceptor;

    public WebMvcConfig(CorsProperties corsProperties, RequestLoggingInterceptor requestLoggingInterceptor) {
        this.corsProperties = corsProperties;
        this.requestLoggingInterceptor = requestLoggingInterceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许前端页面调用后端 API。没有这段，浏览器可能会拦截前端请求。
        registry.addMapping("/api/**")
                .allowedOrigins(corsProperties.getAllowedOrigins().toArray(new String[0]))
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 只拦截接口请求，不拦截静态页面。
        registry.addInterceptor(requestLoggingInterceptor)
                .addPathPatterns("/api/**");
    }
}
