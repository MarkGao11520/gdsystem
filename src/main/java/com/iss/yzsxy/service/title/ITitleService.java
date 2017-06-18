package com.iss.yzsxy.service.title;

import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.pojo.title.Title;
import com.iss.yzsxy.pojo.title.TitleDto;

import java.util.List;
import java.util.Map;


/**
 * Created by gzf on 2017/5/25.
 */
public interface ITitleService {
    /**
     * 查询题目类别信息
     * @Param dto
     * @return
     */
    public PageInfo<Title> obtainList(TitleDto dto);

    public List<Title> obtainEchartList(Integer titleClass);


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
    public boolean update(Title obj);

    /**
     * 添加题目类别信息
     * @param obj
     * @return
     */
    public int add(Title obj);

    /**
     * 获取各种数量
     * @return
     */
    public Map<String,Object> obtainCountMap();


}