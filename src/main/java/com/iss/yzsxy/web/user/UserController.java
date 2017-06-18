package com.iss.yzsxy.web.user;

import com.alibaba.fastjson.JSON;
import com.iss.yzsxy.pojo.student.Student;
import com.iss.yzsxy.pojo.teacher.Teacher;
import com.iss.yzsxy.pojo.user.Admin;
import com.iss.yzsxy.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by gaowenfeng on 2017/5/20.
 */
@Controller
@RequestMapping("userController")
@Scope("prototype")
public class UserController {
    @Autowired
    IUserService iUserService;

    /**
     * 修改密码
     * @param username
     * @param password
     * @param newPassword
     * @return
     */
    @RequestMapping("updatePassword")
    @ResponseBody
    public Map<String,Object> updatePassword(String username,String password,String newPassword){
        return iUserService.updatePassword(username, password, newPassword);
    }

    /**
     * 修改头像
     * @param photo
     * @return
     */
    @RequestMapping("updatePic")
    @ResponseBody
    public Map<String,Object> updatePic(MultipartFile photo){
        return iUserService.updateHeadPic(photo);
    }

    /**
     * 修改个人信息
     * @param student
     * @param teacher
     * @param admin
     * @return
     */
    @RequestMapping("updateUserInfo")
    @ResponseBody
    public Map<String,Object> updateUserInfo(Student student, Teacher teacher, Admin admin){
        return iUserService.updateUserInfo(student, teacher, admin);
    }

    /**
     * 获取个人信息
     * @return
     */
    @RequestMapping("getUserInfo")
    @ResponseBody
    public Map<String,Object> getUserInfo() {
        return iUserService.obtianUserInfo();
    }
}
