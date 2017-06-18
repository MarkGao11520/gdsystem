package com.iss.yzsxy.service.impl.title;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.dao.title.TitleClassMapper;
import com.iss.yzsxy.pojo.teacher.Teacher;
import com.iss.yzsxy.pojo.title.TitleClass;
import com.iss.yzsxy.pojo.title.TitleClassDto;
import com.iss.yzsxy.service.title.ITitleClassService;
import com.iss.yzsxy.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * Created by gaowenfeng on 2017/6/1.
 */
@Service
@Scope("prototype")
public class TitleClassService implements ITitleClassService{
    @Autowired
    TitleClassMapper titleClassMapper;

    @Override
    public PageInfo<TitleClass> obtainList(TitleClassDto dto) {
        PageHelper.startPage(dto.getPage(), dto.getRows());
        if (dto.getTcName()!= null&&!dto.getTcName().equals("")) {
            dto.setTcName("%"+dto.getTcName()+"%");
        }else{
            dto.setTcName("%%");
        }
        List<TitleClass> titleClasses = titleClassMapper.selectList(dto);
        if (resultHandler(titleClasses)) {
            return new PageInfo<TitleClass>(titleClasses);
        } else {
            return new PageInfo<TitleClass>(Collections.emptyList());
        }
    }

    @Override
    public List<TitleClass> obtainKVList() {
        return titleClassMapper.selectList(new TitleClassDto());
    }

    @Override
    public boolean delete(Integer[] ids) {
        try {
            if (titleClassMapper.deleteBatch(ids) == ids.length) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(TitleClass obj) {
        try {
            if (titleClassMapper.updateByPrimaryKeySelective(obj)==1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int add(TitleClass obj) {
        try {
            obj.setCreateuid(Tools.obtainPrincipal().getId());
            if (titleClassMapper.insertSelective(obj) == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int addBatch(List<TitleClass> titleClassList) {
        try {
            for (TitleClass titleclass:titleClassList) {
                if (titleClassMapper.selectByTitleClassName(titleclass.getTitleclassname()) == null){
                    titleClassMapper.insertSelective(titleclass);
                }else {
                    titleclass.setTitleclassid(titleClassMapper.selectByTitleClassName(titleclass.getTitleclassname()).getTitleclassid());
                }
            }
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("titleclassservice题目类型添加失败");
        }
    }

    public boolean resultHandler(List<TitleClass> result) {
        if (result == null || result.size() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
