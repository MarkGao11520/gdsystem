package com.iss.yzsxy.pojo.title;

import com.iss.yzsxy.pojo.teacher.Teacher;

public class Title {
    private Integer titleid;

    private String titlename;

    private String titlecontent;

    private Integer titleclassid;

    private TitleClass titleClass;

    private int selectCount;

    private Teacher teacher;

    private Integer createteacherid;

    private Long createTime;

    private String create;

    private String titleCode;

    private Integer isdel;

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getTitleCode() {
        return titleCode;
    }

    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Title title = (Title) o;

        return titleid != null ? titleid.equals(title.titleid) : title.titleid == null;
    }


    public int getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    public TitleClass getTitleClass() {
        return titleClass;
    }

    public void setTitleClass(TitleClass titleClass) {
        this.titleClass = titleClass;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Integer getTitleid() {
        return titleid;
    }

    public void setTitleid(Integer titleid) {
        this.titleid = titleid;
    }

    public String getTitlename() {
        return titlename;
    }

    public void setTitlename(String titlename) {
        this.titlename = titlename == null ? null : titlename.trim();
    }

    public String getTitlecontent() {
        return titlecontent;
    }

    public void setTitlecontent(String titlecontent) {
        this.titlecontent = titlecontent == null ? null : titlecontent.trim();
    }

    public Integer getTitleclassid() {
        return titleclassid;
    }

    public void setTitleclassid(Integer titleclassid) {
        this.titleclassid = titleclassid;
    }

    public Integer getCreateteacherid() {
        return createteacherid;
    }

    public void setCreateteacherid(Integer createteacherid) {
        this.createteacherid = createteacherid;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }
}