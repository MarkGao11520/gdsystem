package com.iss.yzsxy.service.classs;

import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.pojo.classs.ClassDto;
import com.iss.yzsxy.pojo.classs.Classs;
import com.iss.yzsxy.pojo.teacher.Teacher;
import com.iss.yzsxy.pojo.teacher.TeacherPojo;
import com.iss.yzsxy.pojo.user.Login;

import java.util.List;

/**
 * Created by gzf on 2017/5/25.
 */
public interface IClassService {
    /**
     * 查询班级信息
     * @Param teacherPojo
     * @return
     */
    public PageInfo<Classs> obtainClassList(ClassDto classDto);

    /**
     * 获取班级键值对
     * @return
     */
    public List<Classs> obtainClassKVList();

    /**
     * 删除一条班级信息
     * @param classids
     * @return
     */
    public boolean classDelete(Integer[] classids);

    /**
     * 修改班级信息
     * @param classes
     * @return
     */
    public boolean classUpdate(Classs classes);

    /**
     * 添加班级信息
     * @param classes
     * @return
     */
    public int classAdd(Classs classes);

    /**
     * 批量插入班级
     * @param classsList
     * @return
     */
    public int addBatch(List<Classs> classsList);
}