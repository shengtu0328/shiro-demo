package com.xrq.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.Map;

public class CustomRealm extends AuthorizingRealm
{

    Map<String,String> userMap=new HashMap<String, String>(16);

    {

        userMap.put("xrq","2123");
    }


   //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }
    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从主体传过来的认证信息中 获得用户名
        String userName=(String)token.getPrincipal();

        String password=getPasswordByUserName(userName);
        if(password==null)
        {
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo("xrq",password,"CustomRealm");


        return null;
    }

    private String getPasswordByUserName(String userName) {
        return userMap.get(userName);
    }
}
