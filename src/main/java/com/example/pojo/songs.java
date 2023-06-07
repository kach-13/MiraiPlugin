package com.example.pojo;


import java.util.List;

public class songs {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public album getAlbum() {
        return album;
    }

    public void setAlbum(album album) {
        this.album = album;
    }

    private album album;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<artists> getArtists() {
        return artists;
    }

    public void setArtists(List<artists> artists) {
        this.artists = artists;
    }

    public Integer getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Integer publishTime) {
        this.publishTime = publishTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getCopyrightId() {
        return copyrightId;
    }

    public void setCopyrightId(Integer copyrightId) {
        this.copyrightId = copyrightId;
    }

    public Integer getStaus() {
        return staus;
    }

    public void setStaus(Integer staus) {
        this.staus = staus;
    }

    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }

    public Integer getRtype() {
        return rtype;
    }

    public void setRtype(Integer rtype) {
        this.rtype = rtype;
    }

    public Integer getMvid() {
        return mvid;
    }

    public void setMvid(Integer mvid) {
        this.mvid = mvid;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    private String name;
    private List<artists> artists;

    private Integer publishTime;
    private String duration;
    private Integer copyrightId;
    private Integer staus;
    private List<String> alias;
    private Integer rtype;
    private Integer mvid;
    private Integer fee;
    private String mark;

}
