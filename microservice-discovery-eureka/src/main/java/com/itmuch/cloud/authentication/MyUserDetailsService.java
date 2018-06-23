//package com.itmuch.cloud.authentication;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
///**
// * 在登录页面登录后, 会执行该方法从数据库获取用户名和密码, 然后返回UserDetails的实现类传给spring后进行认证
// * @author longxn
// */
////@Component
//public class MyUserDetailsService implements UserDetailsService {
//
//	private Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Value("${security.user.name}")
//	String username;
//
//	@Value("${security.user.password}")
//	String password;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		logger.info("获取到的用户名为: {}", username);
//		// 根据用户名查找用户信息
//		String encode = passwordEncoder.encode(password);
//		logger.info("数据库密码是:"+encode);
//		// 返回UserDetails的实现类(具体项目中使用实现了UserDetails的User类), 第一个参数:数据库获取到的用户名, 第二个参数:数据库获取到的密码(一般是加密过的),
//		// 第三个参数权限的集合List<GrantedAuthority>
////		return new User(username, "123456", AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
//		//实现加密和校验
//		return new User(username,encode, true,true,true,true,AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
//	}
//
//}
