package com.iss.yzsxy.service.title;

import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.pojo.classs.ClassDto;
import com.iss.yzsxy.pojo.classs.Classs;
import com.iss.yzsxy.pojo.title.TitleClass;
import com.iss.yzsxy.pojo.title.TitleClassDto;

import java.util.List;

/**
 * Created by gzf on 2017/5/25.
 */
public interface ITitleClassService {
    /**
     * 查询题目类别信息
     * @Param dto
     * @return
     */
    public PageInfo<TitleClass> obtainList(TitleClassDto dto);

    /**
     * 获取题目类别键值对
     * @return
     */
    public List<TitleClass> obtainKVList();

    /**
     * 删除一条题目类别信息
     * @param ids
     * @return
     */
    public boolean delete(Integer[] ids);

    /**
     * 修改题目类别信息
     * @param obj
     * @return
     */
    public boolean update(TitleClass obj);

    /**
     * 添加题目类别信息
     * @param obj
     * @return
     */
    public int add(TitleClass obj);

    /**
     * 添加题目类型
     * @param titleClassList
     * @return
     */
    public int addBatch(List<TitleClass> titleClassList);
}