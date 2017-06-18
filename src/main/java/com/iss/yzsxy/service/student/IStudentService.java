package com.iss.yzsxy.service.student;

import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.pojo.student.Student;
import com.iss.yzsxy.pojo.student.StudentDto;
import com.iss.yzsxy.pojo.title.Title;
import com.iss.yzsxy.pojo.user.Login;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by gzf on 2017/6/1.
 */
public interface IStudentService {
    /**
     * 查询学生信息
     * @param studentDto
     * @return
     */
    public PageInfo<Student> selectStudentList(StudentDto studentDto);

    /**
     * 根据id删除学生信息（实现批量删除）
     * @param studentids
     * @return
     */
    public Integer studentDelete(Integer[] studentids);

    /**
     * 修改学生信息
     * @param student
     * @return
     */
    public boolean studentUpdate(Student student);

    /**
     * 添加学生信息
     * @param student
     * @param login
     * @return
     */
    public Integer studentAdd(Student student, Login login);

    /**
     * excel导入
     * @param importFile
     * @return
     */
    Map<String,Object> exportExcel(MultipartFile importFile);

    /**
     * excel导出
     * @param classid
     * @return
     */
    Map<String,Object> importExcel(Integer classid) throws IOException;

    /**
     * 给全班同学分配题目
     * @param classid
     * @return
     */
    public Map<String,Object> doAssignedSubject(Integer classid);

    /**
     * 给某个同学随机分配题目
     * @param classid
     * @param studentid
     * @return
     */
    public Map<String,Object> doAssignedSubject(Integer classid,Integer studentid);

    /**
     * 给某个同学分配指定题目
     * @param classid
     * @param studentid
     * @param titleid
     * @return
     */
    public Map<String,Object> doAssignedSubject(Integer classid,Integer studentid,Integer titleid);

    /**
     * 获取可供分配的题目
     * @param classid
     * @return
     */
    public List<Title> getUnSelectTitleByClassId(Integer classid);

    /**
     * 获取申请换题列表学生列表
     * @param page
     * @param rows
     * @return
     */
    PageInfo<Student> getChangeStudentList(int page,int rows);

    /**
     * 拒绝换题
     * @param studentids
     * @param state
     * @return
     */
    Map<String,Object> doChangeState(@Param("studentids") Integer[] studentids, @Param("state") String state);

    /**
     * 全部换题
     * @param sids
     * @param cids
     * @return
     */
    Map<String,Object> doAssignedSubject(String sids,String cids);

    Map<String,Object> getWordList(Integer classid);
}
