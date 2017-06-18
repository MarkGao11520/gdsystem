package com.iss.yzsxy.pojo.title;

import com.iss.yzsxy.pojo.user.Admin;

public class TitleClass {
    private Integer titleclassid;

    private String titleclassname;

    private Integer createuid;

    private Integer isdel;

    private Admin admin;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Integer getTitleclassid() {
        return titleclassid;
    }

    public void setTitleclassid(Integer titleclassid) {
        this.titleclassid = titleclassid;
    }

    public String getTitleclassname() {
        return titleclassname;
    }

    public void setTitleclassname(String titleclassname) {
        this.titleclassname = titleclassname == null ? null : titleclassname.trim();
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