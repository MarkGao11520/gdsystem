package com.iss.yzsxy.dao.student;

import com.iss.yzsxy.pojo.student.Student;
import com.iss.yzsxy.pojo.student.StudentDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    int deleteByPrimaryKey(Integer studentid);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer studentid);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    int deleteByPrimaryKeyBatch(@Param("studentids") Integer[] studentids);

    List<Student> selectStudentList(StudentDto studentDto);

    Student selectByStudentCode(String studentcode);

    int insertBatch(List<Student> list);

    List<Student> selectUnSelectedStudentByClassId(@Param("classId") Integer classId);

    int updateStudentBatch(List<Student> list);

    List<Student> selectChangeStudentList(Integer teacherid);

    int updateStateBatch(@Param("studentids") Integer[] studentids,@Param("state") String state);


}