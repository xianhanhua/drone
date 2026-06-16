/**
 * 文件说明：数据库初始化类，项目启动后执行建表和初始数据 SQL。
 */
package com.example.drone.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
public class DatabaseInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

    /**
     * JdbcTemplate 是 Spring 提供的数据库操作工具。
     * 这里不用它写业务查询，只用它在项目启动时执行建表 SQL。
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * ResourceLoader 用来读取 resources 目录下的 SQL 文件。
     */
    private final ResourceLoader resourceLoader;

    /**
     * 当前使用的数据库类型。
     * application.yml 默认是 sqlite，也可以切换成 mysql。
     */
    private final String databaseType;

    public DatabaseInitializer(DataSource dataSource,
                               ResourceLoader resourceLoader,
                               @Value("${app.database.type:sqlite}") String databaseType) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.resourceLoader = resourceLoader;
        this.databaseType = databaseType;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 根据数据库类型拼出脚本路径，例如 db/schema-sqlite.sql 或 db/schema-mysql.sql。
        Resource resource = resourceLoader.getResource("classpath:db/schema-" + databaseType + ".sql");
        try {
            // 把 SQL 文件读成字符串，再按分号拆成一条条 SQL 执行。
            String sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            Arrays.stream(sql.split(";"))
                    .map(String::trim)
                    .filter(statement -> !statement.isEmpty())
                    .forEach(jdbcTemplate::execute);
            log.info("数据库初始化完成 type={}", databaseType);
        } catch (IOException ex) {
            throw new IllegalStateException("读取数据库初始化脚本失败：" + databaseType, ex);
        }
    }
}
