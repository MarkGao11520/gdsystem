package com.iss.yzsxy.dao.classs;

import com.iss.yzsxy.pojo.classs.ClassDto;
import com.iss.yzsxy.pojo.classs.Classs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ClasssMapper {
    int deleteByPrimaryKey(Integer classid);

    int insert(Classs record);

    int insertSelective(Classs record);

    Classs selectByPrimaryKey(Integer classid);

    int updateByPrimaryKeySelective(Classs record);

    int updateByPrimaryKey(Classs record);

    /**
     * 获取列表
     * @param classDto
     * @return
     */
    List<Classs> selectClassList(ClassDto classDto);

    /**
     * 批量删除班级
     */
    int deleteBatch(@Param("ids") Integer[] ids);


    Classs selectClassByClassName(String classname);

    int insertBatch(List<Classs> classsList);
}