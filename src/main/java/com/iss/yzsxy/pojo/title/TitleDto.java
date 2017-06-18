package com.iss.yzsxy.pojo.title;

/**
 * Created by gaowenfeng on 2017/6/1.
 */
public class TitleDto {
    private int page;
    private int rows;
    private Integer teacherid;
    private Integer titleclassid;
    private String tname;

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

    public Integer getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(Integer teacherid) {
        this.teacherid = teacherid;
    }

    public Integer getTitleclassid() {
        return titleclassid;
    }

    public void setTitleclassid(Integer titleclassid) {
        this.titleclassid = titleclassid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }
}
