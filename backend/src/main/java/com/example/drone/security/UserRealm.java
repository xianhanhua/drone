/**
 * 文件说明：Shiro 用户认证类，校验管理员用户名和密码。
 */
package com.example.drone.security;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class UserRealm extends AuthorizingRealm {

    // 默认管理员用户名，来自 application.yml。
    private final String username;

    // 默认管理员密码，来自 application.yml。
    private final String password;

    public UserRealm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 当前系统只有管理员一种角色，给它全部无人机管理权限。
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("admin");
        info.addStringPermission("drone:*");
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // Shiro 会把登录表单提交的用户名和密码封装到 token 里。
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        if (!username.equals(usernamePasswordToken.getUsername())) {
            throw new AuthenticationException("用户名或密码错误");
        }
        String providedPassword = new String(usernamePasswordToken.getPassword());
        if (!password.equals(providedPassword)) {
            throw new AuthenticationException("用户名或密码错误");
        }
        // 用户名和密码都正确，就返回认证成功信息。
        return new SimpleAuthenticationInfo(username, usernamePasswordToken.getPassword(), getName());
    }
}
