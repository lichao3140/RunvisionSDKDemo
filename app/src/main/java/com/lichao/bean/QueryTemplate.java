package com.lichao.bean;

public class QueryTemplate {

    private String name;

    private String workNo;

    public QueryTemplate(String name, String workNo) {
        this.name = name;
        this.workNo = workNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

}
