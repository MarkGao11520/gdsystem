package com.iss.yzsxy.service.user;

import com.iss.yzsxy.pojo.student.Student;
import com.iss.yzsxy.pojo.teacher.Teacher;
import com.iss.yzsxy.pojo.user.Admin;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by gaowenfeng on 2017/5/20.
 */
public interface IUserService {
    /**
     * 修改密码
     * @param username
     * @param password
     * @param newPassword
     * @return
     */
    Map<String,Object> updatePassword(String username,String password,String newPassword);

    /**
     * 手机号
     * @param photo
     * @return
     */
    Map<String,Object> updateHeadPic(MultipartFile photo);

    /**
     * 修改个人信息
     * @param student
     * @param teacher
     * @param admin
     * @return
     */
    Map<String,Object> updateUserInfo(Student student, Teacher teacher, Admin admin);

    /**
     * 获取个人信息
     * @return
     */
    Map<String,Object> obtianUserInfo();
}
