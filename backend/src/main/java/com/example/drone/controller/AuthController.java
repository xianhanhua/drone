/**
 * 文件说明：登录接口控制器，负责登录、退出和检查登录状态。
 */
package com.example.drone.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.drone.dto.ApiResponse;
import com.example.drone.dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    /**
     * 前端保存和传递的固定接口令牌。
     * 登录成功后后端把它返回给前端，前端以后请求接口时放到 X-Auth-Token 请求头里。
     */
    private final String apiToken;

    /**
     * 默认管理员用户名，来自 application.yml。
     */
    private final String username;

    public AuthController(@Value("${app.security.api-token}") String apiToken,
                          @Value("${app.security.username}") String username) {
        this.apiToken = apiToken;
        this.username = username;
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        // 交给 Shiro 校验用户名和密码。校验失败会抛异常，由 GlobalExceptionHandler 统一返回错误。
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(request.getUsername(), request.getPassword()));
        return ApiResponse.ok(currentUser(subject));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        // 清除 Shiro 当前登录状态。
        SecurityUtils.getSubject().logout();
        return ApiResponse.ok("Logged out", null);
    }

    @GetMapping("/me")
    public ApiResponse<Map<String, Object>> me(@RequestHeader(value = "X-Auth-Token", required = false) String token) {
        // 前后端分离页面刷新后，优先用 X-Auth-Token 判断是否登录。
        if (apiToken.equals(token)) {
            Map<String, Object> user = new LinkedHashMap<String, Object>();
            user.put("authenticated", true);
            user.put("username", username);
            user.put("token", apiToken);
            return ApiResponse.ok(user);
        }
        return ApiResponse.ok(currentUser(SecurityUtils.getSubject()));
    }

    private Map<String, Object> currentUser(Subject subject) {
        // 统一组装用户信息，前端根据 authenticated 判断是否显示主页面。
        Map<String, Object> user = new LinkedHashMap<String, Object>();
        user.put("authenticated", subject.isAuthenticated());
        user.put("username", subject.getPrincipal());
        if (subject.isAuthenticated()) {
            user.put("token", apiToken);
        }
        return user;
    }
}
