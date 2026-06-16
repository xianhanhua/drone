/**
 * 文件说明：后端 Spring Boot 启动入口，负责启动整个无人机管理系统后端。
 */
package com.example.drone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.drone.mapper")
public class DroneManagementApplication {

    /**
     * 后端项目的启动入口。
     *
     * SpringApplication.run 会启动 Spring Boot 内置的 Tomcat，
     * 同时扫描 controller、service、dao、config 等包里的组件。
     * @MapperScan 用来告诉 MyBatis：mapper 接口都放在 com.example.drone.mapper 包里。
     */
    public static void main(String[] args) {
        SpringApplication.run(DroneManagementApplication.class, args);
    }
}
