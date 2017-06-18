package com.iss.yzsxy.dao.teacher;

import com.iss.yzsxy.pojo.teacher.Teacher;
import com.iss.yzsxy.pojo.teacher.TeacherPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {
    int deleteByPrimaryKey(Integer teacherid);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Integer teacherid);

    int updateByPrimaryKeySelective(Teacher record);

    int deleteByPrimaryKeySelective(@Param("teacherids") Integer[] teacherids);

    int updateByPrimaryKey(Teacher record);

    List<Teacher> selectTeacherList(TeacherPojo teacherPojo);

    Teacher selectTeacherByTeacherName(String teachername);
}