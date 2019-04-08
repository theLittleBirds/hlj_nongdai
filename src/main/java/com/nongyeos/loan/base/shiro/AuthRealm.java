package com.nongyeos.loan.base.shiro;

import java.util.Date;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.nongyeos.loan.admin.entity.SysWebUser;
import com.nongyeos.loan.admin.service.IWebUserService;
import com.nongyeos.loan.base.util.Constants;

import javax.annotation.Resource;

public class AuthRealm extends AuthorizingRealm {
	@Resource  
    private IWebUserService userService; 
	
	/**
     * 授权用户权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        SysWebUser userInfo  = (SysWebUser)principals.getPrimaryPrincipal();
//        for(SysRole role:userInfo.getRoleList()){
//            authorizationInfo.addRole(role.getRole());
//            for(SysPermission p:role.getPermissions()){
//                authorizationInfo.addStringPermission(p.getPermission());
//            }
//        }
//        return authorizationInfo;
        return null;
    }

    /**
     * 验证用户身份
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        //获取用户的输入的账号
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        //盐值
        ByteSource salt = ByteSource.Util.bytes(token.getUsername());
        SimpleHash simpleHash = new SimpleHash("MD5", token.getPassword(), salt, 1024);
        token.setPassword(String.valueOf(simpleHash).toCharArray());
        
		String name = token.getUsername();
		SysWebUser user = userService.selectUserByUserName(name);
		// 从数据库获取用户
		if (null == user) {
			throw new AccountException("该用户不存在！");
		}else if(user.getStatus() == null || user.getStatus().shortValue() != Constants.USER_STATUS_ENABLED){
			throw new DisabledAccountException("用户状态不正常！");
		}
        
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		user, //用户
        		user.getPassword(), //密码
                getName()  //realm name
        );
        return authenticationInfo;
    }

}