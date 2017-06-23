package com.iss.yzsxy.dao.title;

import com.iss.yzsxy.pojo.teacher.Teacher;
import com.iss.yzsxy.pojo.title.Title;
import com.iss.yzsxy.pojo.title.TitleClass;
import com.iss.yzsxy.pojo.title.TitleClassDto;
import com.iss.yzsxy.pojo.title.TitleDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TitleMapper {
    int deleteByPrimaryKey(Integer titleid);

    int insert(Title record);

    int insertSelective(Title record);

    int insertBatch(List<Title> list);

    Title selectByPrimaryKey(Integer titleid);

    Title selectByTitleName(String titlename);

    int updateByPrimaryKeySelective(Title record);

    int updateStudentDesignTitle(Integer titleid);

    int updateByPrimaryKey(Title record);

    /**
     * 获取列表
     * @param titleDto
     * @return
     */
    List<Title> selectList(TitleDto titleDto);

    /**
     * 批量删除
     */
    int deleteBatch(@Param("ids") Integer[] ids);

    @Select("select count(titleId) from tb_title where isdel = 0")
    int selectTitleCount();

    @Select("select count(titleClassId) from tb_title_class where isdel = 0")
    int selectTitleClassCount();

    @Select("select count(classId) from tb_class where isdel = 0")
    int selectClassCount();

    @Select("select count(studentId) from tb_student where isdel = 0")
    int selectStudentCount();

    @Select("select count(studentId) from tb_student where isdel = 0 and titleId = #{titid}")
    int selectCount(int titid);

    List<Title> selectTitleIdByClassId(@Param("classId") Integer classId);


}