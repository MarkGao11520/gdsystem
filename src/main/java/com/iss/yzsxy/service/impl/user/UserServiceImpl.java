package com.iss.yzsxy.service.impl.user;

import com.alibaba.fastjson.JSON;
import com.iss.yzsxy.dao.student.StudentMapper;
import com.iss.yzsxy.dao.teacher.TeacherMapper;
import com.iss.yzsxy.dao.user.AdminMapper;
import com.iss.yzsxy.dao.user.LoginMapper;
import com.iss.yzsxy.pojo.student.Student;
import com.iss.yzsxy.pojo.teacher.Teacher;
import com.iss.yzsxy.pojo.user.Admin;
import com.iss.yzsxy.pojo.user.Login;
import com.iss.yzsxy.pojo.user.SysUser;
import com.iss.yzsxy.service.user.IUserService;
import com.iss.yzsxy.tools.CustomXWPFDocument;
import com.iss.yzsxy.tools.Tools;
import com.iss.yzsxy.tools.WordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by gaowenfeng on 2017/5/20.
 */
@Service
@Scope("prototype")
@ConfigurationProperties(prefix = "myTemp")
public class UserServiceImpl implements IUserService{
    @Autowired
    LoginMapper loginMapper;
    @Autowired
    HttpServletRequest request;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    TeacherMapper teacherMapper;

    private String tempUrl;

    public String getTempUrl() {
        return tempUrl;
    }

    public void setTempUrl(String tempUrl) {
        this.tempUrl = tempUrl;
    }

    @Override
    public Map<String, Object> updatePassword(String username, String password, String newPassword) {
        System.out.println(username+" "+password+" "+newPassword);
        int result = 0;
        try {
            result = loginMapper.updatePassword(newPassword, username, password);
        }catch (Exception e){
            e.printStackTrace();
            result = 0;
        }finally {
            Map<String, Object> map = new HashMap<String,Object>();
            map.put("result",result);
            return map;
        }
    }

    @Override
    public Map<String, Object> updateHeadPic(MultipartFile photo) {
        Map<String, Object> map = Tools.uploadFile(request,"upload/user",photo);
        Map<String,Object> map1 = new HashMap<String,Object>();
        if((boolean)map.get("isSuccess")){
            SysUser sysUser = Tools.obtainPrincipal();
            Integer roleid = Integer.parseInt(sysUser.getRoles());
            int result = 0;
            try {
                switch (roleid){
                    case 0:
                        Admin admin1 = new Admin();
                        admin1.setHeadpic((String) map.get("url"));
                        admin1.setAdminid(sysUser.getId());
                        result = adminMapper.updateByPrimaryKeySelective(admin1);
                        break;
                    case 1:
                        Admin admin = new Admin();
                        admin.setHeadpic((String) map.get("url"));
                        admin.setAdminid(sysUser.getId());
                        result = adminMapper.updateByPrimaryKeySelective(admin);
                        break;
                    case 2:
                        Student student = new Student();
                        student.setStudentpic((String) map.get("url"));
                        student.setStudentid(sysUser.getId());
                        result = studentMapper.updateByPrimaryKeySelective(student);
                        break;
                    case 3:
                        Teacher teacher = new Teacher();
                        teacher.setTeacherpic((String) map.get("url"));
                        teacher.setTeacherid(sysUser.getId());
                        result = teacherMapper.updateByPrimaryKeySelective(teacher);
                        break;
                }
                map1.put("url",(String) map.get("url"));
                map1.put("result",result);
            }catch (Exception e){
                e.printStackTrace();
                map1.put("url","");
                map1.put("result",0);
            }finally {
                return map1;
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> updateUserInfo(Student student, Teacher teacher, Admin admin) {
        Map<String,Object> map1 = new HashMap<String,Object>();
        SysUser sysUser = Tools.obtainPrincipal();
        Integer roleid = Integer.parseInt(sysUser.getRoles());
        int result = 0;
        try {
            switch (roleid){
                case 1:
                    admin.setAdminid(sysUser.getId());
                    result = adminMapper.updateByPrimaryKeySelective(admin);
                    break;
                case 0:
                    admin.setAdminid(sysUser.getId());
                    result = adminMapper.updateByPrimaryKeySelective(admin);
                    break;
                case 2:
                    student.setStudentid(sysUser.getId());
                    if(student.getOpenreport()!=null){
                        if(exportWord(student)){
                            result = studentMapper.updateByPrimaryKeySelective(student);
                            map1.put("wordUrl",student.getWordUrl());
                        }else{
                            result = 0;
                        }
                    }else{
                        result = studentMapper.updateByPrimaryKeySelective(student);
                    }
                    break;
                case 3:
                    teacher.setTeacherid(sysUser.getId());
                    result = teacherMapper.updateByPrimaryKeySelective(teacher);
                    break;
            }
            map1.put("result",result);
        }catch (Exception e){
            e.printStackTrace();
            map1.put("result",0);
        }finally {
            return map1;
        }
    }

    @Override
    public Map<String, Object> obtianUserInfo() {
        Map<String,Object> map1 = new HashMap<String,Object>();
        SysUser sysUser = Tools.obtainPrincipal();
        Integer roleid = Integer.parseInt(sysUser.getRoles());
        Object o = null;
        map1.put("username",sysUser.getUsername());
        map1.put("role",roleid);
        try {
            switch (roleid){
                case 1:
                    Admin admin = adminMapper.selectByPrimaryKey(sysUser.getId());
                    o = admin;
                    map1.put("pic",admin.getHeadpic());
                    map1.put("realname",admin.getRealname());
                    break;
                case 0:
                    Admin admin1 = adminMapper.selectByPrimaryKey(sysUser.getId());
                    o = admin1;
                    map1.put("pic",admin1.getHeadpic());
                    map1.put("realname",admin1.getRealname());
                    break;
                case 2:
                    Student student = studentMapper.selectByPrimaryKey(sysUser.getId());
                    o = student;
                    map1.put("pic",student.getStudentpic());
                    map1.put("realname",student.getStudentname());
                    map1.put("code",student.getStudentcode());
                    break;
                case 3:
                    Teacher teacher = teacherMapper.selectByPrimaryKey(sysUser.getId());
                    int num = studentMapper.selectChangeStudentList(teacher.getTeacherid()).size();
                    o = teacher;
                    map1.put("pic",teacher.getTeacherpic());
                    map1.put("realname",teacher.getTeachername());
                    map1.put("code",teacher.getTeachercode());
                    map1.put("num",num);
                    break;
            }
            map1.put("result",o);
        }catch (Exception e){
            e.printStackTrace();
            map1.put("result",-1);
        }finally {
            return map1;
        }
    }

    private boolean exportWord(Student student1) {
        Student student = studentMapper.selectByPrimaryKey(student1.getStudentid());
        student.setOpenreport(student1.getOpenreport());
        System.out.println(JSON.toJSONString(student));
        boolean result = false;
        try {
            String path = request.getServletContext().getRealPath(
                    "/");
            String userPath = "/"+"studentWord"+"/";
            String fileName = UUID.randomUUID().toString()+".docx";
            File dir = new File(path+userPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            Map<String, Object> param = new HashMap<String, Object>();
            param.put("${name}", student.getStudentname());
            param.put("${classes}",student.getClasses()==null?"未填写班级":student.getClasses().getClassname());
            param.put("${title}", student.getTitle()==null?"未分配题目":student.getTitle().getTitlename());
            param.put("${teacher}", student.getTeacher1()==null?"未分配教师":student.getTeacher1().getTeachername());
            param.put("${depart}",student.getDepartments()==null?"未填写部门":student.getDepartments());
            param.put("${major}",student.getMajor()==null?"未填写专业":student.getMajor());
            param.put("${openreport}",Tools.filterHtml(student.getOpenreport()));
            //CustomXWPFDocument doc = WordUtil.generateWord(param, path+"/temp/word.docx");
            CustomXWPFDocument doc = WordUtil.generateWord(param, tempUrl);
            FileOutputStream fopts = null;
//            fopts = new FileOutputStream("/Users/gaowenfeng/Desktop/bbb.docx");
            fopts = new FileOutputStream(path+userPath+fileName);
            student1.setWordUrl(userPath+fileName);
            doc.write(fopts);
            fopts.close();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }finally {
            return result;
        }
    }
}
