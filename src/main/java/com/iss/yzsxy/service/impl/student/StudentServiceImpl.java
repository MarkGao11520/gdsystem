package com.iss.yzsxy.service.impl.student;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.dao.student.StudentMapper;
import com.iss.yzsxy.dao.title.TitleMapper;
import com.iss.yzsxy.dao.user.LoginMapper;
import com.iss.yzsxy.pojo.classs.Classs;
import com.iss.yzsxy.pojo.student.Student;
import com.iss.yzsxy.pojo.student.StudentDto;
import com.iss.yzsxy.pojo.title.Title;
import com.iss.yzsxy.pojo.user.Login;
import com.iss.yzsxy.service.classs.IClassService;
import com.iss.yzsxy.service.student.IStudentService;
import com.iss.yzsxy.tools.CustomXWPFDocument;
import com.iss.yzsxy.tools.Tools;
import com.iss.yzsxy.tools.WordUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by gzf on 2017/6/1.
 */
@Service
@Scope("prototype")
public class StudentServiceImpl implements IStudentService {
    static DecimalFormat df = new DecimalFormat("0");
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    LoginMapper loginMapper;
    @Autowired
    IClassService iClassService;
    @Autowired
    TitleMapper titleMapper;
    @Autowired
    HttpServletRequest request;
    @Autowired
    Md5PasswordEncoder passwordEncoder;

    /**
     * 根据id查询学生信息，并且student和ClassName，student和titleClass一对一
     *
     * @param studentDto
     * @return
     */
    @Override
    public PageInfo<Student> selectStudentList(StudentDto studentDto) {
        PageHelper.startPage(studentDto.getPage(), studentDto.getRows());
        if (studentDto.getSname() != null && !studentDto.getSname().equals("")) {
            studentDto.setSname("%" + studentDto.getSname() + "%");
        } else {
            studentDto.setSname(null);
        }
        if (studentDto.getScode() != null && !studentDto.getScode().equals("")) {
            studentDto.setScode(studentDto.getScode());
        } else {
            studentDto.setScode(null);
        }
        if (studentDto.getClassid() != null && !studentDto.getClassid().equals("")) {
            studentDto.setClassid(studentDto.getClassid());
        } else {
            studentDto.setClassid(null);
        }
        List<Student> students = studentMapper.selectStudentList(studentDto);
        if (resultHandler(students)) {
            return new PageInfo<Student>(students);
        } else {
            return new PageInfo<Student>(Collections.emptyList());
        }
    }

    /**
     * 根据id删除学生信息（实现批量删除）
     *
     * @param studentids
     * @return
     */
    @Override
    public Integer studentDelete(Integer[] studentids) {
        int result = 0;
        try {
            if (studentMapper.deleteByPrimaryKeyBatch(studentids) == studentids.length) {
                result = 1;
            } else {
                result = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        } finally {
            return result;
        }
    }

    /**
     * 修改学生信息
     *
     * @param student
     * @return
     */
    @Override
    public boolean studentUpdate(Student student) {
        try {
            if (studentMapper.updateByPrimaryKeySelective(student) == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 添加学生
     *
     * @param student
     * @param login
     * @return
     */
    @Override
    @Transactional
    public Integer studentAdd(Student student, Login login) {
        int result = 0;
        if (loginMapper.findByUname(login.getUsername()) != null) {
            result = -1;
        }else {
            login.setPassword(passwordEncoder.encodePassword("66666666",null));
            login.setRoleid(2);
            try {
                if (loginMapper.insertSelective(login) == 1) {
                    student.setStudentid(login.getLoginid());
                    student.setCreateuid(Tools.obtainPrincipal().getId());
                    if (studentMapper.insertSelective(student) == 1) {
                        result = 1;
                    } else {
                        throw new RuntimeException("login表中添加成功，student表中未添加成功");
                    }
                } else {
                    result = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("login表添加失败抛出异常");
            }
        }
        return result;
    }

    /**
     * 重置学生密码
     * @param loginids
     * @return
     */
    @Override
    public int resetStudentPassword(Integer[] loginids) {
        int result = 0;
        String password;
        boolean hasRole = Tools.obtainPrincipal().getRoles().equals("0")||
                          Tools.obtainPrincipal().getRoles().equals("1")||
                          Tools.obtainPrincipal().getRoles().equals("3");
        if (hasRole){
            try {
                password = passwordEncoder.encodePassword("66666666",null);
                if (loginMapper.resetStudentPassword(loginids,password)==loginids.length){
                    result = 1;//重置成功；
                }else {
                    result = 0;
                }
            }catch (Exception e){
                e.printStackTrace();
                result = 0;
            }
        }else {
            result = -1;//您沒有權限
        }
        return result;
    }
    /**
     * 学生excel导入
     *
     * @param importFile
     * @return
     */
    @Override
    public Map<String, Object> exportExcel(MultipartFile importFile) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            ArrayList<Student> students = new ArrayList<Student>();
            ArrayList<Classs> classses = new ArrayList<Classs>();
            ArrayList<Login> logins = new ArrayList<Login>();

            InputStream is = importFile.getInputStream();
            HSSFWorkbook hwb = new HSSFWorkbook(is);

            for (int i = 0; i < hwb.getNumberOfSheets(); i++) {
                HSSFSheet hs = hwb.getSheetAt(i);
                if (hs == null)
                    continue;
                else {
                    int firstrownum = hs.getFirstRowNum();  //第一行
                    int lastrownum = hs.getLastRowNum();    //最后一行

                    int firstCol = hs.getRow(firstrownum + 1).getFirstCellNum();
                    int lastCol = hs.getRow(firstrownum + 1).getLastCellNum();
                    String[] valueFirst = new String[lastCol];
                    for (int k = firstCol; k < lastCol; k++) {
                        HSSFCell hc = hs.getRow(firstrownum + 1).getCell(k);
                        valueFirst[k] = parseDB(hc.getCellType(), hc);
                    }

                    if(!valueFirst[0].trim().equals("姓名")||!valueFirst[1].trim().equals("学号")||!valueFirst[2].trim().equals("班级")){
                        map.put("result",-5);
                        return map;
                    }

                    for (int j = firstrownum + 2; j <= lastrownum; j++) {
                        HSSFRow hr = hs.getRow(j);
                        int firstcolumnnum = hr.getFirstCellNum();
                        int lastcolumnnum = hr.getLastCellNum();
                        String[] value = new String[lastcolumnnum];
                        for (int k = firstcolumnnum; k < lastcolumnnum; k++) {
                            HSSFCell hc = hr.getCell(k);
                            value[k] = parseDB(hc.getCellType(), hc);
                        }

                        Student student = new Student();
                        student.setStudentname(value[0]);
                        student.setStudentcode(value[1]);

                        Login login = new Login();
                        login.setUsername(value[1]);
                        login.setPassword(passwordEncoder.encodePassword("66666666",null));
                        login.setRoleid(2);
                        student.setLogininfo(login);

                        Classs classs = new Classs();
                        classs.setClassname(value[2]);
                        classs.setCreateuid(Tools.obtainPrincipal().getId());
                        student.setClasses(classs);

                        if(!students.contains(student)&&student.getStudentcode()!=null&&student.getStudentcode()!=""){
                            students.add(student);
                            logins.add(login);
                            classses.add(classs);
                        }

                    }
                }
            }
            insertBatch(students, classses, logins, map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            System.out.println(JSON.toJSONString(map));
            return map;
        }
    }

    @Override
    public Map<String, Object> importExcel(Integer classid) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        StudentDto studentDto = new StudentDto();
        studentDto.setClassid(classid);
        List<Student> studentList = studentMapper.selectStudentList(studentDto);
        if(resultHandler(studentList)){
            HSSFWorkbook hw = new HSSFWorkbook();
            HSSFSheet sheet = hw.createSheet("学生选课统计表");
            HSSFRow row = sheet.createRow(0);
            HSSFCell cell = row.createCell(0);
            HSSFCellStyle hcs = cell.getCellStyle();
            hcs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            cell.setCellStyle(hcs);

            cell = row.createCell(0);
            cell.setCellValue("姓名");

            cell = row.createCell(1);
            cell.setCellValue("学号");

            cell = row.createCell(2);
            cell.setCellValue("班级");

            cell = row.createCell(3);
            cell.setCellValue("题目");

            cell = row.createCell(4);
            cell.setCellValue("学校导师");

            cell = row.createCell(5);
            cell.setCellValue("企业导师");

            for(int i=0;i<studentList.size();i++){
                row =sheet.createRow(i+1);
                Student student = studentList.get(i);

                cell = row.createCell(0);
                cell.setCellValue(student.getStudentname()==null?"无":student.getStudentname());

                cell = row.createCell(1);
                cell.setCellValue(student.getStudentcode()==null?"无":student.getStudentcode());

                cell = row.createCell(2);
                cell.setCellValue(student.getClasses()==null?"无":student.getClasses().getClassname());

                cell = row.createCell(3);
                cell.setCellValue(student.getTitle()==null?"无":student.getTitle().getTitlename());

                cell = row.createCell(4);
                cell.setCellValue(student.getTeacherId2()==null?"无":student.getTeacherId2());

                cell = row.createCell(5);
                cell.setCellValue(student.getTeacher1()==null?"无":student.getTeacher1().getTeachername());

                String path = request.getServletContext().getRealPath(
                        "/");
                String filePath = path+"/wordZip/"+new Date().getTime()+".xls";
                File file = new File(filePath);
                File dir = new File(path+"/wordZip/");
                if(!dir.exists()){
                    dir.mkdir();
                }
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(filePath);
                hw.write(fos);
                fos.flush();
                fos.close();
                map.put("result",1);
                map.put("file",file);
            }
        }else{
            map.put("result",0);
        }
        return map;
    }

    @Override
    public Map<String, Object> doAssignedSubject(Integer classid) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> roleResult = checkRole();
        if (!(boolean) roleResult.get("result")) {
            map.put("result", -3);  //-3代表权限错误
            return map;
        }
        List<Student> studentList = new ArrayList<>();
        try {
            studentList = studentMapper.selectUnSelectedStudentByClassId(classid);  //获取未选题学生列表
            List<Title> titleList = titleMapper.selectTitleIdByClassId(classid);  //获取可选题目
            if (titleList.size() == 0 || titleList.size() < studentList.size()) {
                map.put("result", -2);  //-2代表可分配题目数量不够
            } else {
                List<Title> list = new ArrayList<>();
                Random random = new Random();
                while (list.size() != studentList.size()) {    //获取随机题目列表
                    Integer i = random.nextInt(titleList.size());
                    if (i < titleList.size() && i >= 0 && !list.contains(titleList.get(i))) {
                        list.add(titleList.get(i));
                    }
                }
                for (int i = 0; i < list.size(); i++) {    //分配
                    studentList.get(i).setTitleid(list.get(i).getTitleid());
                    studentList.get(i).setState("3");
                    if ((boolean) roleResult.get("flag")) {
                        studentList.get(i).setTeacherId1(list.get(i).getCreateteacherid());
                    } else {
                        studentList.get(i).setTeacherId1((Integer) roleResult.get("teacherid"));
                    }
                }
                if (studentMapper.updateStudentBatch(studentList) == studentList.size()) {   //更新数据库
                    map.put("result", 1);  //1代表成功
                } else {
                    map.put("result", -1);  //-1代表分配失败
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (studentList.size() == 0) {
                map.put("result", -4);  //-3代表没有需要分配的学生
            } else {
                map.put("result", -1);  //-1代表分配失败
            }
        } finally {
            return map;
        }
    }

    @Override
    public Map<String, Object> doAssignedSubject(Integer classid, Integer studentid) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> roleResult = checkRole();
        if (!(Boolean) roleResult.get("result")) {
            map.put("result", -3);  //-3代表权限错误
            return map;
        }

        try {
            List<Title> titleList = titleMapper.selectTitleIdByClassId(classid);  //获取可选题目
            Student student = new Student();
            student.setStudentid(studentid);

            Random random = new Random();     //随机分配题目
            Integer i = random.nextInt(titleList.size());
            student.setTitleid(titleList.get(i).getTitleid());
            student.setState("3");
            if ((Boolean) roleResult.get("flag")) {
                student.setTeacherId1(titleList.get(i).getCreateteacherid());
            } else {
                student.setTeacherId1((Integer) roleResult.get("teacherid"));
            }


            if (studentMapper.updateByPrimaryKeySelective(student) == 1) {   //更细数据库
                map.put("result", 1);  //1代表成功
            } else {
                map.put("result", -1);  //-1代表分配失败
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", -1);  //-1代表分配失败
        } finally {
            return map;
        }
    }

    @Override
    public Map<String, Object> doAssignedSubject(Integer classid, Integer studentid, Integer titleid) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> roleResult = checkRole();
        if (!(Boolean) roleResult.get("result")) {
            map.put("result", -3);  //-3代表权限错误
            return map;
        }
        try {
            Student student = new Student();
            student.setTitleid(titleid);
            student.setStudentid(studentid);
            student.setState("3");
            if ((Boolean) roleResult.get("flag")) {
                student.setTeacherId1(titleMapper.selectByPrimaryKey(titleid).getCreateteacherid());
            } else {
                student.setTeacherId1((Integer) roleResult.get("teacherid"));
            }
            if (studentMapper.updateByPrimaryKeySelective(student) == 1) {   //更细数据库
                map.put("result", 1);  //1代表成功
            } else {
                map.put("result", -1);  //-1代表分配失败
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", -1);  //-1代表分配失败
        } finally {
            return map;
        }
    }

    @Override
    public List<Title> getUnSelectTitleByClassId(Integer classid) {
        return titleMapper.selectTitleIdByClassId(classid);
    }

    @Override
    @Transactional
    public int agreeDesignTitleByStudentId(Integer studentid) {
        int result = 0;
        try {
            if (studentMapper.agreeDesignTitle(studentid)==1){
                Student student = new Student();
                student = studentMapper.selectByPrimaryKey(studentid);
                student.setTitleid(student.getCreatetitleid());
                if (titleMapper.updateStudentDesignTitle(student.getCreatetitleid())==1){
                    studentMapper.updateByPrimaryKeySelective(student);
                    result = 1;
                }else {
                    result = 0;
                }
            }else {
                result = 0;
                throw new RuntimeException("同意学生换题错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("同意换题异常");
        }
        return result;
    }

    @Override
    public PageInfo<Student> getChangeStudentList(int page, int rows) {
        PageHelper.startPage(page, rows);
        List<Student> students = studentMapper.selectChangeStudentList(Tools.obtainPrincipal().getId());
        if (resultHandler(students)) {
            return new PageInfo<Student>(students);
        } else {
            return new PageInfo<Student>(Collections.emptyList());
        }
    }
    @Override
    public PageInfo<Student> studentDesignTitleList(int page, int rows) {
        PageHelper.startPage(page, rows);
        List<Student> students = studentMapper.selectStudentDesignList(Tools.obtainPrincipal().getId());

        if (resultHandler(students)) {
            return new PageInfo<Student>(students);
        } else {
            return new PageInfo<Student>(Collections.emptyList());
        }
    }
    @Override
    public Map<String, Object> doChangeState(Integer[] studentids, String state) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (studentMapper.updateStateBatch(studentids, state) == studentids.length) {
                map.put("result", 1);
            } else {
                map.put("result", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", 0);
        } finally {
            return map;
        }
    }

    @Override
    @Transactional
    public Map<String, Object> doAssignedSubject(String sids, String cids) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] stuents = null;
        String[] classes = null;
        try {
            stuents = sids.split(",");
            classes = cids.split(",");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", 0);
            return map;
        }
        for (int i = 0; i < stuents.length; i++) {
            int result = (int) doAssignedSubject(Integer.parseInt(classes[i]), Integer.parseInt(stuents[i])).get("result");
            if (result != 1) {
                throw new RuntimeException("批量换题错误");
            }
        }
        map.put("result", 1);
        return map;
    }

    @Override
    public Map<String, Object> getWordList(Integer classid) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            StudentDto studentDto = new StudentDto();
            studentDto.setClassid(classid);
            List<Student> students = studentMapper.selectStudentList(studentDto);
            List<File> fileList = new ArrayList<>();
            String path = request.getServletContext().getRealPath("/");
            for(Student student:students){
                if(student.getWordUrl()!=null){
                    File file = new File(path+student.getWordUrl());
                    fileList.add(file);
                }
            }
            if(fileList.size()==0){
                map.put("result",-1);   //-1代表该班学生均为完善开题报告
            }else{
                map.put("result",1);
                map.put("fileList",fileList);
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("result",0);
        }finally {
            return map;
        }
    }

    @Override
    public PageInfo<Student> getStudentByTeacherId(StudentDto studentDto) {
        PageHelper.startPage(studentDto.getPage(), studentDto.getRows());
        System.out.println(JSON.toJSONString(studentDto));
        List<Student> students = studentMapper.selectStudentByTeacherId(Tools.obtainPrincipal().getId());
        System.out.println(JSON.toJSONString(students));
        if (resultHandler(students)) {
            return new PageInfo<Student>(students);
        } else {
            return new PageInfo<Student>(Collections.emptyList());
        }
    }


    @Transactional
    private void insertBatch(List<Student> studentList, List<Classs> classsList, List<Login> logins, Map<String, Object> map) {
        boolean temp = false;
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < studentList.size(); i++) {
            if (studentMapper.selectByStudentCode(studentList.get(i).getStudentcode()) == null) {

            } else {
                temp = true;
                arrayList.add(studentList.get(i).getStudentcode());

            }
        }
        if (temp){
            map.put("batchResult", arrayList);
            map.put("result", -1);
            throw new RuntimeException("学号重复");
        }
        if (loginMapper.insertBatch(logins) == logins.size()) {
//                System.out.println(JSON.toJSONString(logins));
            if (iClassService.addBatch(classsList) == 1) {
                for (int i = 0; i < studentList.size(); i++) {
                        studentList.get(i).setStudentid(logins.get(i).getLoginid());
                        studentList.get(i).setClassid(classsList.get(i).getClassid());
                }
                if (studentMapper.insertBatch(studentList) == studentList.size()) {
                    System.out.println(JSON.toJSONString(studentList));
                    map.put("result", 1);
                } else {
//                        System.out.println(JSON.toJSONString(studentList));
                    throw new RuntimeException("学生表添加失败");
                }
            } else {
                map.put("result", -2);
                throw new RuntimeException("班级表添加失败");
            }
        } else {
            map.put("result", -2);
            throw new RuntimeException("登录表添加失败");
        }
    }

    private Map<String, Object> checkRole() {
        Integer roleid = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            roleid = Integer.parseInt(Tools.obtainPrincipal().getRoles());  //获取当前登录用户权限
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result", false);
            return map;
        }
        if (roleid == 0 || roleid == 1) {   //判断当前登录员是教师还是管理员，如果为教师，则学生企业导师Id为该教师
            map.put("flag", true);
            map.put("result", true);
        } else if (roleid == 3) {
            map.put("flag", false);
            map.put("result", true);
            map.put("teacherid", Tools.obtainPrincipal().getId());
        } else {
            map.put("result", false);
        }
        return map;
    }


    private boolean resultHandler(List<Student> students) {
        if (students == null || students.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    static public String parseDB(int celltype, HSSFCell hc) {
        if (celltype == HSSFCell.CELL_TYPE_STRING) {
            // System.out.println("s");
            return String.valueOf(hc.getStringCellValue());
        }
        if (celltype == HSSFCell.CELL_TYPE_NUMERIC) {
            //System.out.println("n");
            return String.valueOf(df.format(hc.getNumericCellValue()));
        }
        return "";
    }



}
