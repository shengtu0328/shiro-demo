package com.xrq.shiro.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmTest {


    DruidDataSource druidDataSource=new DruidDataSource();
    {
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/shirotest");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");
    }

    @Test
    public void testAuthentication()
    {
        JdbcRealm jdbcRealm=new JdbcRealm();
        jdbcRealm.setDataSource(druidDataSource);
        jdbcRealm.setPermissionsLookupEnabled(true);

        String sql="select password from test_user where user_name =?";
        jdbcRealm.setAuthenticationQuery(sql);

        String rolesql="select role_name from test_user_role where user_name =?";
        jdbcRealm.setUserRolesQuery(rolesql);

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);
        //2.主题提交请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject= SecurityUtils.getSubject();

        UsernamePasswordToken token =new UsernamePasswordToken("xrq","123");
        System.out.println("要开始loign");
        subject.login(token);

        System.out.println("isAuthenticated:"+subject.isAuthenticated());
        subject.checkRole("user");

        subject.checkPermission("user:delete");
        subject.checkPermission("user:update");

        /*subject.checkRole("admin");
        subject.checkRoles("admin","user");*/

        /*subject.logout();

        System.out.println("isAuthenticated:"+subject.isAuthenticated());*/


    }
}
