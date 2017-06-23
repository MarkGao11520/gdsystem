package com.iss.yzsxy.service.teacher;

import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.pojo.teacher.Teacher;
import com.iss.yzsxy.pojo.teacher.TeacherPojo;
import com.iss.yzsxy.pojo.user.Login;

import java.util.List;
import java.util.Map;

/**
 * Created by gzf on 2017/5/25.
 */
public interface ITeacherService {
    /**
     * 查询教师信息
     * @Param teacherPojo
     * @return
     */
    public PageInfo<Teacher> selectTeacherList(TeacherPojo teacherPojo);

    /**
     * 删除一条教师信息
     * @param teacherids
     * @return
     */
    public String teacherDelete(Integer[] teacherids);

    /**
     * 修改教师信息
     * @param teacher
     * @return
     */
    public boolean teacherUpdate(Teacher teacher);

    /**
     * 添加教师信息
     * @param teacher
     * @return
     */
    public int teacherAdd(Teacher teacher);

    /**
     * 获取教师键值对
     * @return
     */
    public List<Teacher> teacherKVList();

    /**
     * 批量插入教师
     * @param teacherList
     * @return
     */
    public int addBatch(List<Teacher> teacherList);
    /**
     * 重置教师密码
     * @param loginids
     * @return
     */
    public int resetTeacherPassword(Integer[] loginids);

}