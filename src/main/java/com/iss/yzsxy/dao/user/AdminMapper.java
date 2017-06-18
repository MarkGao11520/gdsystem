package com.iss.yzsxy.dao.user;

import com.iss.yzsxy.pojo.user.Admin;
import com.iss.yzsxy.pojo.user.AdminDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer adminid);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer adminid);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    List<Admin> selectAdminList(AdminDto adminDto);

    int deleteByPrimaryKeyBatch(@Param("adminids") Integer[] adminids);
}