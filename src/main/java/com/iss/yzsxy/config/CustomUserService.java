package com.iss.yzsxy.config;

import com.iss.yzsxy.dao.user.LoginMapper;
import com.iss.yzsxy.pojo.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by gaowenfeng on 2017/2/5.
 */
public class CustomUserService implements UserDetailsService{
    @Autowired
    LoginMapper loginMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = loginMapper.findByUname(s);
        if (user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        System.out.println(user.getPassword()+" "+user.getUsername()+user.getId()+""+user.getRoles());
//        user.setRoles(basUserMapper.findUserRoleByUserName(s));
        return user;
    }
}
