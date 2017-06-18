package com.iss.yzsxy.pojo.teacher;

/**
 * Created by gzf on 2017/5/25.
 */
public class TeacherPojo {
    private int page;
    private int rows;
    private String tname;
    private String tcode;
    public String getTcode() {
        return tcode;
    }

    public void setTcode(String tcode) {
        this.tcode = tcode;
    }
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }


}