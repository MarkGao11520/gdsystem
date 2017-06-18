package com.iss.yzsxy.dao.user;

import com.iss.yzsxy.pojo.user.Login;
import com.iss.yzsxy.pojo.user.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LoginMapper {
    int deleteByPrimaryKey(Integer loginid);

    int insert(Login record);

    int insertSelective(Login record);

    Login selectByPrimaryKey(Integer loginid);

    int updateByPrimaryKeySelective(Login record);

    int updateByPrimaryKey(Login record);

    /**
     * 根据用户名查找用户信息
     * @param uname
     * @return
     */
    SysUser findByUname(@Param("uname") String uname);

    /**
     * 修改密码
     * @param newPassword
     * @param username
     * @param password
     * @return
     */
    int updatePassword(@Param("newPassword")String newPassword,@Param("username")String username,@Param("password")String password);

    /**
     * 批量插入
     * @param logins
     * @return
     */
    int insertBatch(List<Login> logins);
}