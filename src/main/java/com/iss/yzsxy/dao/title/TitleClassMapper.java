package com.iss.yzsxy.dao.title;

import com.iss.yzsxy.pojo.classs.ClassDto;
import com.iss.yzsxy.pojo.classs.Classs;
import com.iss.yzsxy.pojo.title.TitleClass;
import com.iss.yzsxy.pojo.title.TitleClassDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TitleClassMapper {
    int deleteByPrimaryKey(Integer titleclassid);

    int insert(TitleClass record);

    int insertSelective(TitleClass record);

    TitleClass selectByPrimaryKey(Integer titleclassid);

    int updateByPrimaryKeySelective(TitleClass record);

    int updateByPrimaryKey(TitleClass record);

    /**
     * 获取列表
     * @param titleClassDto
     * @return
     */
    List<TitleClass> selectList(TitleClassDto titleClassDto);

    /**
     * 批量删除
     */
    int deleteBatch(@Param("ids") Integer[] ids);

    /**
     * 根据题目类型名称查题目信息
     * @param titleclassname
     * @return
     */
    TitleClass selectByTitleClassName(String titleclassname);
}