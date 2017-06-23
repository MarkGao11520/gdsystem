package com.iss.yzsxy.web.student;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.pojo.student.Student;
import com.iss.yzsxy.pojo.student.StudentDto;
import com.iss.yzsxy.pojo.title.Title;
import com.iss.yzsxy.pojo.user.Login;
import com.iss.yzsxy.service.impl.student.StudentServiceImpl;
import com.iss.yzsxy.service.student.IStudentService;
import com.iss.yzsxy.tools.Tools;
import com.iss.yzsxy.tools.ZipUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gzf on 2017/6/1.
 */
@Controller
@RequestMapping("/studentController")
@Scope("prototype")
public class StudentController {
    @Autowired
    IStudentService studentService;

    @ExceptionHandler
    @ResponseBody
    public Map<String,Object> exception(Exception e){
        Map<String,Object> map = new HashMap<>();
        map.put("result","-1");
        return map;
    }

    /**
     * 查询学生列表
     *
     * @param studentDto
     * @return
     */
    @RequestMapping("/studentSelectList")
    @ResponseBody
    public PageInfo<Student> studentSelectList(StudentDto studentDto) {
        return studentService.selectStudentList(studentDto);
    }

    /**
     * 根据id删除学生信息（实现批量删除）
     *
     * @param studentids
     * @return
     */
    @RequestMapping("studentDelete")
    @ResponseBody
    public Integer studentDelete(@RequestBody Integer[] studentids) {
        return studentService.studentDelete(studentids);
    }

    /**
     * 修改学生信息
     *
     * @param student
     * @return
     */
    @RequestMapping("/studentUpdate")
    @ResponseBody
    public boolean studentUplate(Student student) {
        return studentService.studentUpdate(student);
    }


    /**
     * 添加学生
     * @param student
     * @param login
     * @return
     */
    @RequestMapping("/studentAdd")
    @ResponseBody
    public Integer studentAdd(Student student, Login login) {
        try {
            return studentService.studentAdd(student, login);
        } catch (Exception e) {
            return 0;
        }
    }
    /**
     * 重置学生密码
     * @param loginids
     * @return
     */
    @RequestMapping("/resetStudentPassword")
    @ResponseBody
    public int resetStudentPassword(@RequestBody Integer[] loginids){
        return studentService.resetStudentPassword(loginids);
    }

    /**
     * excel导入
     * @param excel
     * @return
     */
    @RequestMapping("/excelImport")
    @ResponseBody
    public Map<String,Object> excelImport(MultipartFile excel){
        return studentService.exportExcel(excel);
    }

    /**
     * 给一个班的学生随机分配题目
     * @param classid
     * @return
     */
    @RequestMapping("assignedSubjectAll")
    @ResponseBody
    public Map<String,Object> assignedSubjectAll(Integer classid){
        return studentService.doAssignedSubject(classid);
    }

    /**
     * 给某一个学生随机分配题目
     * @param classid
     * @param studentid
     * @return
     */
    @RequestMapping("assignedSubjectOne")
    @ResponseBody
    public Map<String,Object> assignedSubjectOne(Integer classid,Integer studentid){
        return studentService.doAssignedSubject(classid,studentid);
    }

    /**
     * 给某一个学生分配指定题目
     * @param classid
     * @param studentid
     * @param titleid
     * @return
     */
    @RequestMapping("assignedSubjectOneSpecified")
    @ResponseBody
    public Map<String,Object> assignedSubjectOneSpecified(Integer classid,Integer studentid,Integer titleid){
        return studentService.doAssignedSubject(classid, studentid, titleid);
    }

    /**
     * 获取可分配题目列表
     * @param classid
     * @return
     */
    @RequestMapping("getUnSelectTitle")
    @ResponseBody
    public List<Title> getUnSelectTitle(Integer classid){
        return studentService.getUnSelectTitleByClassId(classid);
    }

    @RequestMapping("agreeDesignTitle")
    @ResponseBody
    public int agreeDesignTitle(Integer studentid){
        return studentService.agreeDesignTitleByStudentId(studentid);
    }

    @RequestMapping("getChangeStudentList")
    @ResponseBody
    public PageInfo<Student> getChangeStudentList(int page,int rows){
        return studentService.getChangeStudentList(page, rows);
    }
    @RequestMapping("getStudentDesignTitleList")
    @ResponseBody
    public PageInfo<Student> getStudentDesignTitleList(int page, int rows){
        return studentService.studentDesignTitleList(page, rows);
    }

    @RequestMapping("changeStateBatch")
    @ResponseBody
    public Map<String,Object> changeStateBatch(String studentIds, String state){
        return studentService.doChangeState(Tools.stringsToIntegers(studentIds.split(",")),state);
    }

    @RequestMapping("changeTitleBatch")
    @ResponseBody
    public Map<String,Object> doAssignedSubject(String sids,String cids){
        return studentService.doAssignedSubject(sids,cids);
    }

    @RequestMapping("exportWordBatch")
    @ResponseBody
    public void exportWordBatch(HttpServletRequest request, HttpServletResponse response,Integer classid) throws IOException {
        Map<String,Object> map = studentService.getWordList(classid);
        response.setCharacterEncoding("GBK");
        if((int)map.get("result")==1){
            try {
                response = ZipUtil.downLoadFiles((List<File>) map.get("fileList"),request,response);
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().append("创建压缩包失败，3秒后自动调回");
                response.setHeader("refresh","3;URL=../student");
            }
        }else if((int)map.get("result")==-1){
            response.getWriter().append("该班学生均为完善开题报告，无法导出，3秒后自动调回");
            response.setHeader("refresh","3;URL=../student");
        }else {
            response.getWriter().append("创建压缩包失败，3秒后自动调回");
            response.setHeader("refresh","3;URL=../student");
        }
    }

    @RequestMapping("exportExcel")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response,Integer classid) throws IOException {
        try {
            Map<String,Object> map = studentService.importExcel(classid);
            if((int)map.get("result")==1){
                response = ZipUtil.downloadZip((File) map.get("file"),response);
            }else{
                response.getWriter().append("导出excel失败，3秒后自动调回");
                if(Integer.parseInt(Tools.obtainPrincipal().getRoles())==0||Integer.parseInt(Tools.obtainPrincipal().getRoles())==1){
                    response.setHeader("refresh","3;URL=../student");
                }else {
                    response.setHeader("refresh","3;URL=../assigen");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().append("导出excel失败，3秒后自动调回");
            if(Integer.parseInt(Tools.obtainPrincipal().getRoles())==0||Integer.parseInt(Tools.obtainPrincipal().getRoles())==1){
                response.setHeader("refresh","3;URL=../student");
            }else {
                response.setHeader("refresh","3;URL=../assigen");
            }        }

    }

    @RequestMapping("getStudentListByTeacherId")
    @ResponseBody
    public PageInfo<Student> getStudentListByTeacherId(StudentDto studentDto){
        return studentService.getStudentByTeacherId(studentDto);
    }
}
