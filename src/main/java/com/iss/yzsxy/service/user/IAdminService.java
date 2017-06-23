package com.iss.yzsxy.service.user;

import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.pojo.user.Admin;
import com.iss.yzsxy.pojo.user.AdminDto;
import com.iss.yzsxy.pojo.user.Login;
import org.springframework.web.multipart.MultipartFile;

import java.nio.channels.MulticastChannel;
import java.util.Map;

/**
 * Created by gzf on 2017/6/3.
 */
public interface IAdminService {
    /**
     * 查询管理员列表
     * @param adminDto
     * @return
     */
    public PageInfo<Admin> selectAdminList(AdminDto adminDto);

    /**
     * 批量删除管理员
     * @param integers
     * @return
     */
    public int deleteByPrimaryKeyBatch(Integer[] adminids);

    /**
     * 修改管理員信息
     * @param admin
     * @return
     */
    public int updateByPrimaryKeySelective(Admin admin);

    /**
     * 添加管理員信息
     * @param admin
     * @return
     */
    public int addAdmin(Admin admin);

    public Map<String,Object> importExcel(MultipartFile importExcel);

    public int resetAdminPassword(Integer[] loginids);
}
