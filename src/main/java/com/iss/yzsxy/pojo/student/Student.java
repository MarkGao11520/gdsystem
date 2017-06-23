package com.iss.yzsxy.pojo.student;

import com.iss.yzsxy.pojo.classs.Classs;
import com.iss.yzsxy.pojo.teacher.Teacher;
import com.iss.yzsxy.pojo.title.Title;
import com.iss.yzsxy.pojo.title.TitleClass;
import com.iss.yzsxy.pojo.user.Login;

public class Student {
    private Integer studentid;

    private Login logininfo;

    private String studentcode;

    private String studentname;

    private String studentpic;

    private Integer classid;

    private Classs classes;

    private Integer sex;

    private Integer age;

    private String qq;

    private String wechat;

    private Integer titleid;

    private Title title;

    private String state;

    private String content;
    private String teacherId2Phone;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return studentcode != null ? studentcode.equals(student.studentcode) : student.studentcode == null;
    }


    public String getTeacherId2Phone() {
        return teacherId2Phone;
    }

    public void setTeacherId2Phone(String teacherId2Phone) {
        this.teacherId2Phone = teacherId2Phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String openreport;

    private Integer createuid;

    private Integer createtitleid;

    private Title designtitlename;

    public Title getDesigntitlename() {
        return designtitlename;
    }

    public void setDesigntitlename(Title designtitlename) {
        this.designtitlename = designtitlename;
    }

    public Integer getCreatetitleid() {
        return createtitleid;
    }

    public void setCreatetitleid(Integer createtitleid) {
        this.createtitleid = createtitleid;
    }

    private String departments;

    private String grade;

    private String major;

    private String phone;

    private Integer isdel;

    private Integer teacherId1;

    private Teacher teacher1;

    private String teacherId2;

    private String wordUrl;

    public String getWordUrl() {
        return wordUrl;
    }

    public void setWordUrl(String wordUrl) {
        this.wordUrl = wordUrl;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Teacher getTeacher1() {
        return teacher1;
    }

    public void setTeacher1(Teacher teacher1) {
        this.teacher1 = teacher1;
    }

    public Integer getTeacherId1() {
        return teacherId1;
    }

    public void setTeacherId1(Integer teacherId1) {
        this.teacherId1 = teacherId1;
    }

    public String getTeacherId2() {
        return teacherId2;
    }

    public void setTeacherId2(String teacherId2) {
        this.teacherId2 = teacherId2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Login getLogininfo() {
        return logininfo;
    }

    public void setLogininfo(Login logininfo) {
        this.logininfo = logininfo;
    }

    public Classs getClasses() {
        return classes;
    }

    public void setClasses(Classs classes) {
        this.classes = classes;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getDepartments() {
        return departments;
    }

    public void setDepartments(String departments) {
        this.departments = departments;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStudentpic() {
        return studentpic;
    }

    public void setStudentpic(String studentpic) {
        this.studentpic = studentpic;
    }

    public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    public String getStudentcode() {
        return studentcode;
    }

    public void setStudentcode(String studentcode) {
        this.studentcode = studentcode == null ? null : studentcode.trim();
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname == null ? null : studentname.trim();
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getTitleid() {
        return titleid;
    }

    public void setTitleid(Integer titleid) {
        this.titleid = titleid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getOpenreport() {
        return openreport;
    }

    public void setOpenreport(String openreport) {
        this.openreport = openreport == null ? null : openreport.trim();
    }

    public Integer getCreateuid() {
        return createuid;
    }

    public void setCreateuid(Integer createuid) {
        this.createuid = createuid;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }
}