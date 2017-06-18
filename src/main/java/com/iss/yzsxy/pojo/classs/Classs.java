package com.iss.yzsxy.pojo.classs;

import com.iss.yzsxy.pojo.user.Admin;

public class Classs {
    private Integer classid;

    private String classname;

    private Integer createuid;

    private Admin admin;

    private Integer isdel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classs classs = (Classs) o;

        return classname != null ? classname.equals(classs.classname) : classs.classname == null;
    }

    @Override
    public int hashCode() {
        return classname != null ? classname.hashCode() : 0;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Integer getClassid() {
        return classid;
    }

    public void setClassid(Integer classid) {
        this.classid = classid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname == null ? null : classname.trim();
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