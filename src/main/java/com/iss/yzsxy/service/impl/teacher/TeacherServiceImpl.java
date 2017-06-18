package com.iss.yzsxy.service.impl.teacher;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.dao.teacher.TeacherMapper;
import com.iss.yzsxy.dao.user.LoginMapper;
import com.iss.yzsxy.pojo.teacher.Teacher;
import com.iss.yzsxy.pojo.teacher.TeacherPojo;
import com.iss.yzsxy.pojo.user.Login;
import com.iss.yzsxy.service.teacher.ITeacherService;
import com.iss.yzsxy.tools.ChineseToEnglish;
import com.iss.yzsxy.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gzf on 2017/5/25.
 */
@Service
@Scope("prototype")
public class TeacherServiceImpl implements ITeacherService {
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    LoginMapper loginMapper;

    /**
     * 查询教师列表
     * @param teacherPojo
     * @return
     */
    @Override
    public PageInfo<Teacher> selectTeacherList(TeacherPojo teacherPojo) {
        PageHelper.startPage(teacherPojo.getPage(),teacherPojo.getRows());
        if (teacherPojo.getTname()!=null&&!teacherPojo.getTname().equals("")){
            teacherPojo.setTname("%"+teacherPojo.getTname()+"%");
        }else{
            teacherPojo.setTname(null);
        }
        if (teacherPojo.getTcode()!=null&&!teacherPojo.getTcode().equals("")){
            teacherPojo.setTcode(teacherPojo.getTcode());
        }else {
            teacherPojo.setTcode(null);
        }
        List<Teacher> teachers = teacherMapper.selectTeacherList(teacherPojo);
        if(resultHandler(teachers)){
            return new PageInfo<Teacher>(teachers);
        }else{
            return new PageInfo<Teacher>(Collections.emptyList());
        }
    }

    /**
     * 根据id删除教师信息
     * @param teacherids
     * @return
     */
    @Override
    public String teacherDelete(Integer[] teacherids) {
        String result="0";
        try {
            if (teacherMapper.deleteByPrimaryKeySelective(teacherids)==teacherids.length){
                result="1";
            }else {
                result="0";
            }
        }catch (Exception e){
            e.printStackTrace();
            result="0";
        }finally {
            return result;
        }
    }

    /**
     * 修改教师信息
     * @param teacher
     * @return
     */
    @Override
    public boolean teacherUpdate(Teacher teacher) {
        try {
            if(teacherMapper.updateByPrimaryKeySelective(teacher)==1){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 添加教师信息
     * @param teacher
     * @return
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int teacherAdd(Teacher teacher) {
        Login login = new Login();
        login.setUsername(ChineseToEnglish.getPingYin(teacher.getTeachername()));
        if(loginMapper.findByUname(login.getUsername())!=null){
            return -1;
        }else {
            try {
                login.setPassword("88888888");
                login.setRoleid(3);
                if(loginMapper.insertSelective(login)==1){
                    teacher.setTeacherid(login.getLoginid());
                    teacher.setCreateuid(Tools.obtainPrincipal().getId());
                    if(teacherMapper.insertSelective(teacher)==1){
                        return 1;
                    }else{
                        throw new RuntimeException("Login表添加成功，teacher表添加失敗");
                    }
                }else {
                    return 0;
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("Login表添加異常");
            }
        }

    }

    @Override
    public List<Teacher> teacherKVList() {
        return teacherMapper.selectTeacherList(new TeacherPojo());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int addBatch(List<Teacher> teacherList) {
        try {
            for (Teacher teacher:teacherList) {
                if(teacherMapper.selectTeacherByTeacherName(teacher.getTeachername())==null){
                    Login login = new Login();
                    login.setUsername(ChineseToEnglish.getPingYin(teacher.getTeachername()));
                        try {
                            login.setPassword("88888888");
                            login.setRoleid(3);
                            if(loginMapper.insertSelective(login)==1){
                                teacher.setTeacherid(login.getLoginid());
                                teacher.setCreateuid(Tools.obtainPrincipal().getId());
                                if(teacherMapper.insertSelective(teacher)==1){
                                    return 1;
                                }else{
                                    throw new RuntimeException("Login表添加成功，teacher表添加失敗");
                                }
                            }else {
                                return 0;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            throw new RuntimeException("Login表添加異常");
                        }
                }else {
                    teacher.setTeacherid(teacherMapper.selectTeacherByTeacherName(teacher.getTeachername()).getTeacherid());;
                }
            }
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("teacherservice教师表添加失败");
        }
    }


    public boolean resultHandler(List<Teacher> result){
        if(result == null||result.size()==0){
            return false;
        }else{
            return true;
        }
    }
}