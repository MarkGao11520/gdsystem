package com.iss.yzsxy.service.impl.classs;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.dao.classs.ClasssMapper;
import com.iss.yzsxy.dao.user.LoginMapper;
import com.iss.yzsxy.pojo.classs.ClassDto;
import com.iss.yzsxy.pojo.classs.Classs;
import com.iss.yzsxy.pojo.teacher.Teacher;
import com.iss.yzsxy.pojo.user.Login;
import com.iss.yzsxy.service.classs.IClassService;
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
public class ClassServiceImpl implements IClassService {
    @Autowired
    LoginMapper loginMapper;
    @Autowired
    ClasssMapper classsMapper;

    @Override
    public PageInfo<Classs> obtainClassList(ClassDto classDto) {
        PageHelper.startPage(classDto.getPage(), classDto.getRows());
        if (classDto.getClassName() != null&&!classDto.getClassName().equals("")) {
            classDto.setClassName("%" + classDto.getClassName() + "%");
        }else{
            classDto.setClassName("%%");
        }
        List<Classs> classses = classsMapper.selectClassList(classDto);
        if (resultHandler(classses)) {
            return new PageInfo<Classs>(classses);
        } else {
            return new PageInfo<Classs>(Collections.emptyList());
        }
    }

    @Override
    public List<Classs> obtainClassKVList() {
        return classsMapper.selectClassList(new ClassDto());
    }

    @Override
    public boolean classDelete(Integer[] classids) {
        try {
            if (classsMapper.deleteBatch(classids) == classids.length) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean classUpdate(Classs classes) {
        try {
            if (classsMapper.updateByPrimaryKeySelective(classes)==1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int classAdd(Classs classes) {
        try {
            classes.setCreateuid(Tools.obtainPrincipal().getId());
            if (classsMapper.insertSelective(classes) == 1) {
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
    public int addBatch(List<Classs> classsList) {
        try{
        for(Classs classs:classsList){
            if(classsMapper.selectClassByClassName(classs.getClassname())==null){
                classsMapper.insertSelective(classs);
            }else{
                classs.setClassid(classsMapper.selectClassByClassName(classs.getClassname()).getClassid());
            }
        }
        return 1;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("班级表添加失败");
        }
    }

    public boolean resultHandler(List<Classs> result) {
        if (result == null || result.size() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
