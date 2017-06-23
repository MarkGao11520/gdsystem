package com.iss.yzsxy.service.impl.user;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iss.yzsxy.dao.teacher.TeacherMapper;
import com.iss.yzsxy.dao.title.TitleMapper;
import com.iss.yzsxy.dao.user.AdminMapper;
import com.iss.yzsxy.dao.user.LoginMapper;
import com.iss.yzsxy.pojo.teacher.Teacher;
import com.iss.yzsxy.pojo.title.Title;
import com.iss.yzsxy.pojo.title.TitleClass;
import com.iss.yzsxy.pojo.user.Admin;
import com.iss.yzsxy.pojo.user.AdminDto;
import com.iss.yzsxy.pojo.user.Login;
import com.iss.yzsxy.service.teacher.ITeacherService;
import com.iss.yzsxy.service.title.ITitleClassService;
import com.iss.yzsxy.service.user.IAdminService;
import com.iss.yzsxy.tools.ChineseToEnglish;
import com.iss.yzsxy.tools.Tools;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.log.LogInputStream;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by gzf on 2017/6/3.
 */
@Service
public class AdminServiceImpl implements IAdminService{
    static DecimalFormat df = new DecimalFormat("0");
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    LoginMapper loginMapper;
    @Autowired
    TitleMapper titleMapper;
    @Autowired
    ITeacherService teacherService;
    @Autowired
    ITitleClassService titleClassService;
    @Autowired
    Md5PasswordEncoder passwordEncoder;
    /**
     * 查询管理员列表
     * @param adminDto
     * @return
     */
    @Override
    public PageInfo<Admin> selectAdminList(AdminDto adminDto) {
        PageHelper.startPage(adminDto.getPage(),adminDto.getRows());
        if (adminDto.getAname()!=null&&!adminDto.getAname().equals("")){
            adminDto.setAname("%"+adminDto.getAname()+"%");
        }else {
            adminDto.setAname(null);
        }
        List<Admin> admins = adminMapper.selectAdminList(adminDto);
        System.out.println(admins);
        if (resultHandler(admins)){
            return new PageInfo<Admin>(admins);
        }else {
            return new PageInfo<Admin>(Collections.emptyList());
        }
    }

    /**
     * 刪除管理員列表
     * @param adminids
     * @return
     */
    @Override
    public int deleteByPrimaryKeyBatch(Integer[] adminids) {
        int result = 0;
        if (Tools.obtainPrincipal().getRoles().equals("0")){
            try {
                if (adminMapper.deleteByPrimaryKeyBatch(adminids)==adminids.length){
                    result = 1;//刪除成功
                }else {
                    result = 0;//刪除失敗
                }
            }catch (Exception e){
                e.printStackTrace();
                result = 0;
            }
        }else {
            result = -1;//您沒有權限刪除
        }
        return result;
    }

    @Override
    public int updateByPrimaryKeySelective(Admin admin) {
        int result = 0;
        if (Tools.obtainPrincipal().getRoles().equals("0")){
            try {
                if (adminMapper.updateByPrimaryKeySelective(admin)==1){
                    result = 1;//编辑成功
                }else {
                    result = 0;//编辑失敗
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



    @Override
    @Transactional
    public int addAdmin(Admin admin) {
        Login login = new Login();
        int result = 0;
        if (Tools.obtainPrincipal().getRoles().equals("0")){
            login.setUsername(ChineseToEnglish.getPingYin(admin.getRealname()));
            System.out.println(loginMapper.findByUname(login.getUsername()));
            if (loginMapper.findByUname(login.getUsername())!=null){
                result = -1;//用戶名存在不能添加
            }else {
                try {
                    login.setPassword(passwordEncoder.encodePassword("123456",null));
                    login.setRoleid(1);
                    if (loginMapper.insertSelective(login) == 1){
                        admin.setAdminid(login.getLoginid());
                        if (adminMapper.insertSelective(admin)==1){
                            result = 1;//login中添加成功並且admin中添加成功
                        }else {
                            throw new RuntimeException("Login表添加成功，admin表添加失敗");
                        }
                    }else {
                        result = 0;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    throw new RuntimeException("login表添加異常");
                }

            }
        }else {
            result = -2;
        }
    return result;
    }

    @Override
    public Map<String, Object> importExcel(MultipartFile importExcel) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            ArrayList<Title> titles = new ArrayList<Title>();
            ArrayList<Teacher> teachers = new ArrayList<Teacher>();
            ArrayList<TitleClass> titleClasses = new ArrayList<TitleClass>();

            InputStream is = importExcel.getInputStream();
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

                    System.out.println(JSON.toJSONString(valueFirst));
                    if (!valueFirst[0].trim().equals("题目名称") || !valueFirst[1].trim().equals("题目类型")|| !valueFirst[2].trim().equals("题目内容") || !valueFirst[3].trim().equals("教师")) {
                        map.put("result", -5);
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
                        Title title = new Title();
                        title.setTitlename(value[0]);
                        title.setTitlecontent(value[2]);

                        TitleClass titleClass = new TitleClass();
                        titleClass.setTitleclassname(value[1]);
                        titleClass.setCreateuid(Tools.obtainPrincipal().getId());
                        title.setTitleClass(titleClass);

                        Teacher teacher = new Teacher();
                        teacher.setTeachername(value[3]);
                        teacher.setCreateuid(Tools.obtainPrincipal().getId());
                        title.setTeacher(teacher);
                        title.setCreateTime(new Date().getTime());

                        if(!titles.contains(title)&&title.getTitlename()!=null&&title.getTitlename()!=""){
                            titles.add(title);
                            titleClasses.add(titleClass);
                            teachers.add(teacher);
                        }

                    }
                }
            }
            insertBatch(titles,titleClasses,teachers,map);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return map;

        }
    }

    @Override
    public int resetAdminPassword(Integer[] loginids) {
        String password;
        int result = 0;
        if (Tools.obtainPrincipal().getRoles().equals("0")){
            try {
                password = passwordEncoder.encodePassword("123456",null) ;
                if (loginMapper.resetAdminPassword(loginids,password)==loginids.length){
                    result = 1;
                }else {
                    result = 0;
                }
            }catch (Exception e){
                e.printStackTrace();
                result = 0;
            }
        }else {
            result = -1;
        }
        return result;
    }


    @Transactional
    private void insertBatch(List<Title> titleList, List<TitleClass> titleClassList,List<Teacher> teacherList, Map<String,Object> map){
        boolean temp =false;
        List<String> stringList = new ArrayList<String>();
        for (int i = 0; i < titleList.size(); i++){
            if (titleMapper.selectByTitleName(titleList.get(i).getTitlename()) == null){
            }else {
                temp = true;
                stringList.add(titleList.get(i).getTitlename());
            }

        }
        if(temp){
            map.put("batchResult",stringList);
            map.put("result",-1);
            throw new RuntimeException("题目重复");
        }


            if (teacherService.addBatch(teacherList)==1){
                if (titleClassService.addBatch(titleClassList)==1){
                    for (int i = 0; i < titleList.size();i++){

                        titleList.get(i).setCreateteacherid(teacherList.get(i).getTeacherid());
                        titleList.get(i).setTitleclassid(titleClassList.get(i).getTitleclassid());

                    }
                    if (titleMapper.insertBatch(titleList) == titleList.size()){
                        for(Title title:titleList){
                            if(title.getTitleid()!=null) {
                                Calendar calendar = Calendar.getInstance();
                                String code = title.getTitleid().toString().length() == 4 ? title.getTitleid().toString() : title.getTitleid().toString().length() == 3 ? "0" + title.getTitleid() : title.getTitleid().toString().length() == 2 ? "00" + title.getTitleid() : "000" + title.getTitleid();
                                StringBuffer sb = new StringBuffer();
                                sb.append("ISS").append(calendar.getWeekYear()).append(code);
                                title.setTitleCode(sb.toString());
                                titleMapper.updateByPrimaryKeySelective(title);
                            }
                        }
                        map.put("result",1);
                    }else {
                        map.put("result",-2);
                        throw new RuntimeException("题目表添加失败");
                    }
                }else {
                    map.put("result",-2);
                    throw new RuntimeException("题目类型添加失败");
                }
            }else {
                map.put("result",-2);
                throw new RuntimeException("教师表添加失败");
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
    private boolean resultHandler(List<Admin> admins) {
        if (admins == null || admins.size() == 0){
            return false;
        }else {
            return true;
        }
    }

}
