package com.example.pojo;


import java.util.List;

public class album {
    private Integer id;
   private String name ;
   private artist artist;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public artist getArtist() {
        return artist;
    }

    public void setArtist(artist artist) {
        this.artist = artist;
    }

    public Integer getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Integer publishTime) {
        this.publishTime = publishTime;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Integer getCopyrightId() {
        return copyrightId;
    }

    public void setCopyrightId(Integer copyrightId) {
        this.copyrightId = copyrightId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    private Integer publishTime;
   private int size;
   private Integer copyrightId;
   private int status;
   private Integer picId;
   private Integer mark;
   public  String alia;

    public String getAlia() {
        return alia;
    }

    public void setAlia(String alia) {
        this.alia = alia;
    }
}
