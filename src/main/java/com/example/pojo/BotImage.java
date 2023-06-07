package com.example.pojo;

public class BotImage {
    private Integer id;

    private String imagename;

    private String status;

    public BotImage(Integer id, String imagename, String status) {
        this.id = id;
        this.imagename = imagename;
        this.status = status;
    }

    public BotImage() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename == null ? null : imagename.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}