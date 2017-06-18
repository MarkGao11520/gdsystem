package com.iss.yzsxy.web.user;

import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.pojo.user.Admin;
import com.iss.yzsxy.pojo.user.AdminDto;
import com.iss.yzsxy.pojo.user.Login;
import com.iss.yzsxy.service.user.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gzf on 2017/6/3.
 */
@Controller
@RequestMapping("/adminController")
@Scope("prototype")
public class AdminController {
    @Autowired
    IAdminService adminService;

    /**
     * 查询管理员列表
     * @param adminDto
     * @return
     */
    @RequestMapping("/selectAdminList")
    @ResponseBody
    public PageInfo<Admin> selectAdminList(AdminDto adminDto){
        return adminService.selectAdminList(adminDto);
    }

    /**
     * 批量刪除管理員
     * @param adminids
     * @return
     */
    @RequestMapping("/deleteAdmin")
    @ResponseBody
    public int deleteAdmin(@RequestBody Integer[] adminids){
        return adminService.deleteByPrimaryKeyBatch(adminids);
    }

    /**
     * 修改管理員權限
     * @param admin
     * @return
     */
    @RequestMapping("/updateAdmin")
    @ResponseBody
    public int updateAdmin(Admin admin){
        return adminService.updateByPrimaryKeySelective(admin);
    }

    /**
     * 添加管理员
     * @param admin
     * @param login
     * @return
     */
    @RequestMapping("/addAdmin")
    @ResponseBody
    public int addAdmin(Admin admin){
        return adminService.addAdmin(admin);
    }
    @RequestMapping("/excelImport")
    @ResponseBody
    public Map<String,Object> excelImport(MultipartFile excel){
        return adminService.importExcel(excel);
    }
}
