package com.example.pojo;

import com.alibaba.fastjson.JSONObject;
import java.util.*;
public class ChatGPT {

    public static String sendGPT(String content) {
        System.out.println(content);
        Data data = new Data();
        data.setModel("gpt-3.5-turbo-0301");
        List<msgData> list  = new ArrayList<>();
        msgData msgData = new msgData();
        msgData.setContent(content);
        msgData.setRole("user");
        list.add(msgData);

        data.setMessages(list);
        String s = JSONObject.toJSONString(data);
        System.out.println(s);
        Map <String,Object> head = new HashMap<>();
        head.put("authorization","Bearer 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");
        //String s1 = HttpUtil.sendPostJson("http://api.chat-gpt.bet/v1/chat/completions", s, head);
        String s1 = HttpHelp.wlwHttpURLConnection("http://api.chat-gpt.bet/v1/chat/completions", s);
        System.out.println(s1);
        returnData returnData = JSONObject.parseObject(s1, returnData.class);
        return returnData.getChoices().get(0).getMessage().getContent();
    }
}
//请求参数
class Data{
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<msgData> getMessages() {
        return messages;
    }

    public void setMessages(List<msgData> messages) {
        this.messages = messages;
    }

    private String model;
    List<msgData> messages;
}
class msgData{
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String role;
    private String content;
}
//返回数据
class datas{
    public msgData getMessage() {
        return message;
    }

    public void setMessage(msgData message) {
        this.message = message;
    }

    private msgData message;
}
class returnData{
    public List<datas> getChoices() {
        return choices;
    }

    public void setChoices(List<datas> choices) {
        this.choices = choices;
    }

    private List<datas> choices;
}

