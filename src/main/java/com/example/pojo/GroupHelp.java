package com.example.pojo;

public class GroupHelp {
    private Integer id;

    private String titles;

    public GroupHelp(Integer id, String titles) {
        this.id = id;
        this.titles = titles;
    }

    public GroupHelp() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles == null ? null : titles.trim();
    }
}