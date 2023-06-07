package com.example.pojo;



import java.util.List;

public class WyyReturn {//网易云接口调用的结果

    private List<songs> songs;


    public List<songs> getSongs() {
        return songs;
    }

    public void setSongs(List<songs> songs) {
        this.songs = songs;
    }

    public Integer getSongCount() {
        return songCount;
    }

    public void setSongCount(Integer songCount) {
        this.songCount = songCount;
    }


    private Integer songCount;
}
