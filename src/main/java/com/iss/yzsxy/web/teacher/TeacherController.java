package com.iss.yzsxy.web.teacher;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.dao.teacher.TeacherMapper;
import com.iss.yzsxy.pojo.teacher.Teacher;
import com.iss.yzsxy.pojo.teacher.TeacherPojo;
import com.iss.yzsxy.pojo.user.Login;
import com.iss.yzsxy.service.teacher.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;

/**
 * Created by gzf on 2017/5/25.
 */
@Controller
@RequestMapping("/teacherController")
@Scope("prototype")
public class TeacherController {
    @Autowired
    ITeacherService teacherService;



    /**
     * 查找教师信息
     * @param teacherPojo
     * @return
     */
    @RequestMapping("/teacherSelectList")
    @ResponseBody
    public PageInfo<Teacher> teacherSelectList(TeacherPojo teacherPojo){
        return teacherService.selectTeacherList(teacherPojo);
    }

    /**
     * 查找教师键值对信息
     * @return
     */
    @RequestMapping("/teacherKVSelectList")
    @ResponseBody
    public List<Teacher> teacherKVSelectList(){
        return teacherService.teacherKVList();
    }

    /**
     * 删除教师信息
     * @param teacherids
     * @return
     */
    @RequestMapping("/teacherDelete")
    @ResponseBody
    public String teacherDelete(@RequestBody Integer[] teacherids){
        return teacherService.teacherDelete(teacherids);
    }



    @RequestMapping("/teacherUplate")
    @ResponseBody
    public boolean teacherUplate(Teacher teacher){
        return teacherService.teacherUpdate(teacher);
    }

    @RequestMapping("/teacherAdd")
    @ResponseBody
    public int teacherAdd(Teacher teacher){
        try{
            return teacherService.teacherAdd(teacher);
        }catch (RuntimeException e){
            return 0;
        }
    }
    /**
     * 重置教师密码
     * @param loginids
     * @return
     */
    @RequestMapping("/resetTeacherPassword")
    @ResponseBody
    public int resetTeacherPassword(@RequestBody Integer[] loginids){
        return teacherService.resetTeacherPassword(loginids);
    }

}