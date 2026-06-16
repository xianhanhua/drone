/**
 * 文件说明：接口登录过滤器，检查前端请求是否带有有效登录 token。
 */
package com.example.drone.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;

public class ApiUserFilter extends UserFilter {

    /**
     * 前端登录成功后保存的固定 token。
     * 后续请求只要带上这个 token，就允许访问受保护的 API。
     */
    private final String apiToken;

    public ApiUserFilter(String apiToken) {
        this.apiToken = apiToken;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // OPTIONS 是浏览器跨域预检请求，必须放行，否则真正的 GET/POST 请求发不出去。
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            return true;
        }
        // 前端把 token 放在 X-Auth-Token 请求头里，后端在这里进行比对。
        String token = httpRequest.getHeader("X-Auth-Token");
        if (apiToken != null && apiToken.equals(token)) {
            return true;
        }
        // 如果没有 token，就继续走 Shiro 自带的登录状态判断。
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String origin = httpRequest.getHeader("Origin");
        if (origin != null) {
            // 未登录时也补上跨域响应头，否则前端可能拿不到明确的错误信息。
            httpResponse.setHeader("Access-Control-Allow-Origin", origin);
            httpResponse.setHeader("Vary", "Origin");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, X-Requested-With, X-Auth-Token");
            httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        }
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=UTF-8");
        // 返回统一 JSON，前端可以直接显示“请先登录”。
        httpResponse.getWriter().write("{\"success\":false,\"message\":\"Please login first\",\"data\":null}");
        return false;
    }
}
