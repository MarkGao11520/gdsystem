package com.iss.yzsxy.pojo.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by gaowenfeng on 2017/2/5.
 *
 * 让我们的用户实体实现UserDetails接口,我们的用户实体即为Spring Security 所使用的用户
 */
public class SysUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String username;
    private String password;
    private String roles;

    /**
     * 重写getAuthorities方法,将用户的角色作为权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        List<SysRole> roles = this.getRoles();
//        for (SysRole role:roles){
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
        authorities.add(new SimpleGrantedAuthority(this.getRoles()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    /**
     * 账户是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否被锁定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 证书是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否有效
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
