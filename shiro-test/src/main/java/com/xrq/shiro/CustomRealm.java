package com.xrq.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm
{

    Map<String,String> userMap=new HashMap<String, String>(16);

    {
        super.setName("CustomRealm");
        userMap.put("xrq","2123");
    }

    //认证(用户名密码)
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从主体传过来的认证信息中 获得用户名
        String userName=(String)token.getPrincipal();

        String password=getPasswordByUserName(userName);
        if(password==null)
        {
            return null;
        }

        //这个simpleAuthenticationInfo 对象是模拟查到的结果，在这里表示查到了
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo("xrq",password,"CustomRealm");
        return simpleAuthenticationInfo;
    }
                                         //授权 (此用户拥有哪些权限)
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName=(String)principals.getPrimaryPrincipal();
        // 从数据库或者缓存 中 获取 角色数据
        Set<String> roles= getRolesByUserName(userName);
        Set<String> permissions= getPermissionsByUserName(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> set=new HashSet<String>();
        set.add("user:delete");
        set.add("user:add");
        return set;

    }

    private Set<String> getRolesByUserName(String userName) {
        Set<String> set=new HashSet<String>();
        set.add("admin");
        set.add("user");
        return set;
    }

    private String getPasswordByUserName(String userName) {
        return userMap.get(userName);
    }
}
