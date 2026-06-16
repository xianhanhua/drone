/**
 * 文件说明：Servlet 容器部署入口，用于外部 Tomcat 等传统 Java Web 部署方式。
 */
package com.example.drone;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * 这个方法用于传统 Servlet 容器部署。
     *
     * 平时双击 bat 启动时主要走 DroneManagementApplication；
     * 如果以后把项目打成 war 包放到外部 Tomcat，这里会告诉 Tomcat
     * 应该从 DroneManagementApplication 这个主类启动项目。
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DroneManagementApplication.class);
    }
}
