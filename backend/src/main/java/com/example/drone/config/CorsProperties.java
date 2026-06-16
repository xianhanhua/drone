/**
 * 文件说明：跨域配置属性类，读取允许访问后端的前端地址。
 */
package com.example.drone.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {

    /**
     * 允许访问后端接口的前端地址列表。
     *
     * 前后端分离时，浏览器会检查“网页地址”和“接口地址”是不是同一个来源。
     * 如果不是同一个来源，就需要后端明确允许，这就是跨域配置。
     * 这里的值来自 application.yml 里的 app.cors.allowed-origins。
     */
    private List<String> allowedOrigins = new ArrayList<String>();

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }
}
