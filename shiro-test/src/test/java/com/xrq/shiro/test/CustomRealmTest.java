package com.xrq.shiro.test;

import com.xrq.shiro.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomRealmTest {

    @Test
    public void testAuthentication()
    {
        CustomRealm customRealm=new CustomRealm();

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);
        //2.主题提交请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject= SecurityUtils.getSubject();

        UsernamePasswordToken token =new UsernamePasswordToken("xrq","2123");
        subject.login(token);

        System.out.println("isAuthenticated:"+subject.isAuthenticated());
   /*     subject.checkRole("admin");

        subject.checkPermission("user:delete");
        subject.checkPermission("user:update");
*/
        /*subject.checkRole("admin");
        subject.checkRoles("admin","user");*/

        /*subject.logout();

        System.out.println("isAuthenticated:"+subject.isAuthenticated());*/
        subject.checkRole("user");
        subject.checkRole("admin");
        subject.checkPermissions("user:delete","user:add");

    }
}
