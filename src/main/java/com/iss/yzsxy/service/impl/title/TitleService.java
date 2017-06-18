package com.iss.yzsxy.service.impl.title;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.dao.title.TitleMapper;
import com.iss.yzsxy.pojo.title.Title;
import com.iss.yzsxy.pojo.title.TitleDto;
import com.iss.yzsxy.service.title.ITitleService;
import com.iss.yzsxy.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by gaowenfeng on 2017/6/1.
 */
@Service
@Scope("prototype")
public class TitleService implements ITitleService{
    @Autowired
    TitleMapper titleMapper;

    @Override
    public PageInfo<Title> obtainList(TitleDto dto) {
        PageHelper.startPage(dto.getPage(), dto.getRows());
        if (dto.getTname()!= null&&!dto.getTname().equals("")) {
            dto.setTname("%"+dto.getTname()+"%");
        }else{
            dto.setTname("%%");
        }
        Integer role = Integer.parseInt(Tools.obtainPrincipal().getRoles());
        if(role==0||role==1){
            dto.setTeacherid(null);
        }else if(role==3){
            dto.setTeacherid(Tools.obtainPrincipal().getId());
        }else{
            return new PageInfo<Title>(Collections.emptyList());
        }
        List<Title> resultlist = titleMapper.selectList(dto);
        if (resultHandler(resultlist)) {
            return new PageInfo<Title>(resultlist);
        } else {
            return new PageInfo<Title>(Collections.emptyList());
        }
    }

    @Override
    public List<Title> obtainEchartList(Integer titleClass) {
        TitleDto titleDto = new TitleDto();
        titleDto.setTitleclassid(titleClass);
        return titleMapper.selectList(titleDto);
    }


    @Override
    public boolean delete(Integer[] ids) {
        try {
            if (titleMapper.deleteBatch(ids) == ids.length) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(Title obj) {
        try {
            if (titleMapper.updateByPrimaryKeySelective(obj)==1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public int add(Title obj) {
        try {
            obj.setCreateteacherid(Tools.obtainPrincipal().getId());
            obj.setCreateTime(new Date().getTime());
            if (titleMapper.insertSelective(obj) == 1) {
                Integer id = obj.getTitleid();
                System.out.println(id);
                Calendar calendar = Calendar.getInstance();
                String code = id.toString().length()==4?id.toString():id.toString().length()==3?"0"+id:id.toString().length()==2?"00"+id:"000"+id;
                StringBuffer sb = new StringBuffer();
                sb.append("ISS").append(calendar.getWeekYear()).append(code);
                obj.setTitleCode(sb.toString());
                if(titleMapper.updateByPrimaryKeySelective(obj)==1){
                    return 1;
                }else{
                    return 0;
                }
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加题目错误");
        }
    }


    /**
     * 获取各种总数
     * @return
     */
    @Override
    public Map<String, Object> obtainCountMap() {
        Map<String, Object> map = new HashMap<String,Object>();
        try{
            map.put("classCount",titleMapper.selectClassCount());
            map.put("studentCount",titleMapper.selectStudentCount());
            map.put("titleCount",titleMapper.selectTitleCount());
            map.put("titleClassCount",titleMapper.selectTitleClassCount());
            map.put("result",1);
        }catch (Exception e){
            e.printStackTrace();
            map.put("result",0);
        }
        return map;
    }

    public boolean resultHandler(List<Title> result) {
        if (result == null || result.size() == 0) {
            return false;
        } else {
            for(Title title:result){
                if(title.getCreateTime()!=null){
                    title.setCreate(Tools.dataLongToString(title.getCreateTime()));
                }
            }
            return true;
        }
    }
}
