package com.example.plugin;
import com.alibaba.fastjson.JSONObject;
import com.example.conf.PathConf;
import com.example.pojo.*;
import com.example.pojo.User;
import net.mamoe.mirai.console.command.Command;
import net.mamoe.mirai.console.command.CommandManager;
import net.mamoe.mirai.console.extension.PluginComponentStorage;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.contact.*;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.action.Nudge;
import net.mamoe.mirai.message.data.*;

import net.mamoe.mirai.utils.ExternalResource;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Template extends JavaPlugin {
    public static Template INSTANCE = new Template();
    private Template() {
        super(new JvmPluginDescriptionBuilder(
                        "com.example.plugin.Test", // 需要遵循语法规定，不知道写什么的话就写主类名吧
                        "1.0.0" // 同样需要遵循语法规定
                )
                        .author("me")
                        .name("template")
                        .info("新版本测试")
                        .build()
        );
    }

    @Override
    public void onLoad(@NotNull PluginComponentStorage $this$onLoad) {

    }


    //创建一个对象
    Random df = new Random();
    EventChannel<Event> eventEventChannel = GlobalEventChannel.INSTANCE.parentScope(this);
    @Override
    public void onEnable() {

//        List<Command> allRegisteredCommands = CommandManager.INSTANCE.getAllRegisteredCommands();
//        System.out.println("当前指令集合");
//        for(int i = 0 ; i < allRegisteredCommands.size() ; i ++){
//            System.out.println(allRegisteredCommands.get(i).getPrimaryName() + "," + allRegisteredCommands.get(i).getDescription());
//        }
//        try {
//            //初始化插件校验PathConf内的目录是否存在
//            File file1 = new File(PathConf.normalimgPath);
//            if(!file1.exists()){
//                file1.mkdirs();
//                System.out.println(PathConf.normalimgPath+"创建成功");
//            }
//            file1 = new File(PathConf.HimgPath);
//            if(!file1.exists()){
//                file1.mkdirs();
//                System.out.println(PathConf.HimgPath+"创建成功");
//            }
//            file1 = new File(PathConf.uploadNormalimgPath);
//            if(!file1.exists()){
//                file1.mkdirs();
//                System.out.println(PathConf.uploadNormalimgPath+"创建成功");
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }





        //监听群内消息
        eventEventChannel.subscribeAlways(GroupMessageEvent.class , g -> {
           // g.getGroup().getSettings().getEntranceAnnouncement()
            String str = g.getMessage().get(1).toString();
          //  At atbot = (At) g.getMessage().get(1);//群里如果有人@Bot
            System.out.println(str);
            //只对!开头的命令做出反应,发送其它消息或空数据不响应
            try{
                if(str.equals("") || !str.substring(0,1).equals("!")){
                    if(str.equals("mute")){
                        try {//禁言方法，可以当某人发送敏感消息将其禁言
                            g.getSender().mute(60);
                        }catch (PermissionDeniedException p){
                            g.getSubject().sendMessage( "不可禁言管理/群主");                        }
                    }
                }else if(str.equals("!群帮助")){

                    String s = HttpHelp.sendGet("localhost:9000/group/helplist");
                    List<GroupHelp> groupHelps = JSONObject.parseArray(s, GroupHelp.class);
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0 ; i < groupHelps.size() ; i ++){
                        stringBuffer.append(groupHelps.get(i).getId()+"." + groupHelps.get(i).getTitles() + "\n");
                    }
                    //回复某个人的消息
                    MessageChain chain = new MessageChainBuilder() // 引用收到的消息并回复 "Hi!", 也可以添加图片等更多元素.
                            .append(new QuoteReply(g.getMessage()))
                            .append("群Bot帮助")
                            .append("\n")
                            .append(stringBuffer)
                            .build();
                    g.getSubject().sendMessage(chain);

                }else if (str.equals("!关机")){
                    long adminid = g.getSender().getId();
                    if(adminid == 2595931767l){
                        g.getSubject().sendMessage("正在关机...");
                        g.getBot().close();
                    }else{
                        g.getSubject().sendMessage("关机限开发者使用");
                    }

                }
                else if(str.equals("!色图帮助")){
                    //回复某个人的消息
                    MessageChain chain = new MessageChainBuilder() // 引用收到的消息并回复 "Hi!", 也可以添加图片等更多元素.
                            .append(new QuoteReply(g.getMessage()))
                            .append("随机色图1 - 碧蓝航线").append("\n")
                            .append("随机色图2 - 碧蓝档案").append("\n")
                            .append("随机色图3 - 原神").append("\n")
                            .append("随机色图4 - 明日方舟").append("\n")
                            .append("随机色图5 - 命运").append("\n")
                            .append("随机色图6 - 公主连结").append("\n")
                            .append("随机色图7 - 少女前线").append("\n")
                            .append("随机色图8 - 崩坏三").append("\n")
                            .build();
                    g.getSubject().sendMessage(chain);
                }else if(str.equals("!图片")){
                    String s = HttpHelp.GetImageString(1);//不是h图
                    BotImage nameList = JSONObject.parseObject(s, BotImage.class);
                    System.out.println("图片名称：" + nameList.getImagename());
                    String ImageName = PathConf.uploadNormalimgPath + nameList.getImagename();//从本地随机读取一张图片
                    File file = new File(ImageName);
                    ExternalResource externalResource = ExternalResource.create(file);
                    Image image = g.getSubject().uploadImage(externalResource);
                    At at = new At(g.getSender().getId());
                    //创建一个消息链
                    MessageChain chain = new MessageChainBuilder()
                            .append(at)
                            .append(new PlainText("图片来力,图片id:" + nameList.getId() ))
                            //.append("id") // 会被构造成 PlainText 再添加, 相当于上一行
                            // .append(AtAll.INSTANCE)
                            .append(image)
                            .build();
                    g.getSubject().sendMessage(chain);//发送图片
                    externalResource.close();//结束关流
                    file.delete();
                }else if(str.startsWith("!删除图片")){
                   int index = str.lastIndexOf("片");
                    str = str.substring(index + 1);
                    String status = HttpHelp.sendGet("localhost:9000/image/delImage?id="+ str.trim());
                    if (status == null){
                        g.getSubject().sendMessage("图片ID不存在");
                    }
                    g.getSubject().sendMessage("删除好力");
                }
                else if (str.equals("!禁言")){//禁言谁
                    //校验权限
                    String s = g.getSender().getPermission().toString();
                    //获取@信息
                    At at = (At) g.getMessage().get(2);
                    long adminid = g.getSender().getId();
                    if(s.equals("OWNER") || s.equals("ADMINISTRATOR") || adminid == 2595931767l){
                        try {
                            //获取要禁言的QQ和时间
                            Long id = at.getTarget();
                            String str2 = g.getMessage().get(3).toString().replaceAll(" ","");
                            Integer time = Integer.valueOf(str2);
                            //获取群成员实例
                            ContactList<NormalMember> members = g.getGroup().getMembers();
                            String BotRole = g.getGroup().getBotPermission().toString();
                            if( BotRole == "MEMBER"){
                                g.getSubject().sendMessage("Bot没有权限");
                                return;
                            }
                            //判断对方权限
                            if( members.get(id).getPermission().toString().equals("OWNER") || members.get(id).getPermission().toString().equals("ADMINISTRATOR")){
                                System.out.println(members.get(id).getPermission().toString());
                                g.getSubject().sendMessage("群主/管理不可禁言");
                            }
                            else {//禁言
                                System.out.println(members.get(id));
                                members.get(id).mute(time);
                            }
                            //未来可能用到的代码
//                            else{
//                                g.getSubject().sendMessage("群员账号错误");
//                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            g.getSubject().sendMessage("格式错误");
                        }

                    }else {
                        g.getSubject().sendMessage("非管理/群主不可使用禁言");
                    }

                }else if(str.startsWith("!解禁")){//取消禁言
                    String s = g.getSender().getPermission().toString();
                    System.out.println(s);
                    Long s1 = g.getSender().getId();
                    if(s.equals("OWNER") || s.equals("ADMINISTRATOR") || s1 == 2595931767l) {
                        try {
                            //获取@信息
                            At at = (At) g.getMessage().get(2);
                            //获取要取消禁言的QQ
                            Long id =at.getTarget();
                            //获取群成员实例
                            ContactList<NormalMember> members = g.getGroup().getMembers();
                            //判断bot是否有禁言权限
                            String BotRole = g.getGroup().getBotPermission().toString();
                            if( BotRole == "MEMBER"){
                                g.getSubject().sendMessage("Bot没有权限");
                                return;
                            }
                            if (members.get(id) != null){
                                System.out.println(members.get(id));
                                members.get(id).unmute();
                            }
                        }catch (Exception e){

                            g.getSubject().sendMessage("格式错误");
                        }

                    }else {
                        g.getSubject().sendMessage("非管理/群主不可使用解禁言");
                    }
                    //被拉入黑名单不可使用上传
                }else if(g.getMessage().get(1).toString().equals("!黑名单")){

                    String s = g.getSender().getPermission().toString();
                    System.out.println(s);
                    Long s1 = g.getSender().getId();
                    if(s.equals("OWNER") || s.equals("ADMINISTRATOR") || s1 == 2595931767l) {
                        try {
                            //获取@信息
                            At at =  (At) g.getMessage().get(2);
                            //拉入黑名单的id
                            Long id =at.getTarget();
                            Long groupid = g.getGroup().getId();
                            //获取群成员实例
                            //ContactList<NormalMember> members = g.getGroup().getMembers();
                            String status = HttpHelp.sendGet("localhost:9000/blacklist/sendblacklist?id="+ id +"&groupid="+groupid);
                            if(status.equals("true")){
                                g.getSubject().sendMessage("已将"+id+"拉入黑名单");
                            }else{
                                g.getSubject().sendMessage("对方已被拉入黑名单");
                            }
                        }catch (Exception e){
                            g.getSubject().sendMessage("格式错误");
                        }

                    }else {
                        g.getSubject().sendMessage("权限不足");
                    }
                }
//                else if(g.getMessage().get(1).toString().equals("!踢出")){
//                    //获取@信息
//                    At at = (At) g.getMessage().get(2);
//                    //判断bot是否有禁言权限
//                    String BotRole = g.getGroup().getBotPermission().toString();
//                    if( BotRole == "MEMBER"){
//                        g.getSubject().sendMessage("Bot没有权限");
//                        return;
//                    }
//                    //获取群成员实例
//                    ContactList<NormalMember> members = g.getGroup().getMembers();
//                    //判断对方权限
//                    if( members.get(at.getTarget()).getPermission().toString().equals("OWNER") || members.get(at.getTarget()).getPermission().toString().equals("ADMINISTRATOR")){
//                        g.getSubject().sendMessage("Bot不可踢出管理/群主");
//                    }
//                    else{
//                        g.getGroup().get(at.getTarget()).kick("已将"+ at.getTarget() + "移除群");
//                    }
//
//                }
                else if(g.getMessage().get(1).toString().equals("!上传"))
                {
                    try {
                        long id = g.getSender().getId();
                        Long groupid = g.getGroup().getId();
                        String status = HttpHelp.sendGet("localhost:9000/blacklist/isitablacklist?id=" + id  +"&groupid="+groupid);
                        if(status.equals("true")){
                            //回复某个人的消息
                            MessageChain chain = new MessageChainBuilder() // 引用收到的消息并回复 "Hi!", 也可以添加图片等更多元素.
                                    .append(new QuoteReply(g.getMessage()))
                                    .append("你被拉入黑名单，不可使用上传功能")
                                    .build();
                            g.getSubject().sendMessage(chain);
                            return;
                        }
                        Image image = (Image) g.getMessage().get(2);

                        UUID uuid=UUID.randomUUID();
                        String uuids = uuid.toString();//生成随机名字
                        int index = image.getImageId().lastIndexOf("}");
                        String gs = image.getImageId().substring(index+1);//个数
                        String newImageName = uuids + gs;
                        String ImageName = PathConf.uploadNormalimgPath + newImageName ;//生成图片名字
                        System.out.println(ImageName);
                        String s = Image.queryUrl(image);
                        URL url = null;

                        url = new URL(s);
                        DataInputStream dataInputStream = new DataInputStream(url.openStream());
                        FileOutputStream fileOutputStream = new FileOutputStream(new File(ImageName));
                        ByteArrayOutputStream output = new ByteArrayOutputStream();

                        byte[] buffer = new byte[2048];
                        int length;

                        while ((length = dataInputStream.read(buffer)) > 0) {
                            output.write(buffer, 0, length);
                        }
                        fileOutputStream.write(output.toByteArray());
                        dataInputStream.close();
                        fileOutputStream.close();
                        //回复某个人的消息
                        String s1 = HttpHelp.sendGet("localhost:9000/image/newImage?imageName=" + newImageName);
                        System.out.println(s1);
                        if(s1.equals("false")){
                            //回复某个人的消息
                            MessageChain chain = new MessageChainBuilder() // 引用收到的消息并回复 "Hi!", 也可以添加图片等更多元素.
                                    .append(new QuoteReply(g.getMessage()))
                                    .append("上传失败")
                                    .build();
                            g.getSubject().sendMessage(chain);
                            return;
                        }else{
                            //回复某个人的消息
                            MessageChain chain = new MessageChainBuilder() // 引用收到的消息并回复 "Hi!", 也可以添加图片等更多元素.
                                    .append(new QuoteReply(g.getMessage()))
                                    .append("上传成功")
                                    .build();
                            g.getSubject().sendMessage(chain);
                        }

                    }  catch (Exception e) {
                        e.printStackTrace();
                        g.getSubject().sendMessage("没有图片");
                    }
                }else if(g.getMessage().get(1).toString().equals("!随机图片")){
                    UUID uuid=UUID.randomUUID();
                    String uuids = uuid.toString();//生成随机名字
                    String newImageName = uuids;
                    String ImageName = PathConf.normalimgPath + newImageName + ".jpg" ;
                    URL url = null;
                    url = new URL("https://iw233.cn/api.php?sort=random");
                    // 打开连接
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setInstanceFollowRedirects(false);
                    String location = urlConnection.getHeaderField("Location");

                    url = new URL(location);
                    // 打开连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 设置请求方法为GET
                    connection.setRequestMethod("GET");
                    // 设置请求头
                    connection.setRequestProperty("Referer", "https://weibo.com/");

                    DataInputStream dataInputStream = new DataInputStream(url.openStream());
                    File file1 = new File(ImageName);
                    FileOutputStream fileOutputStream = new FileOutputStream(file1);
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    byte[] buffer = new byte[8192];
                    int length;
                    while ((length = dataInputStream.read(buffer)) > 0) {
                        output.write(buffer, 0, length);
                    }
                    fileOutputStream.write(output.toByteArray());
                    File file = new File(ImageName);
                    ExternalResource externalResource = ExternalResource.create(file);
                    Image image = g.getSubject().uploadImage(externalResource);
                    At at = new At(g.getSender().getId());
                    MessageChain chain = new MessageChainBuilder()
                            .append(at)
                            .append(new PlainText("图片来力"))
                            //.append("id") // 会被构造成 PlainText 再添加, 相当于上一行
                            // .append(AtAll.INSTANCE)
                            .append(image)
                            .build();
                    g.getSubject().sendMessage(chain);//发送图片
                    externalResource.close();//结束关流
                    dataInputStream.close();
                    fileOutputStream.close();
                    file.delete();
                    file1.delete();
                }
//                else if(g.getMessage().get(1).toString().equals("!随机色图")){
//                    UUID uuid=UUID.randomUUID();
//                    String uuids = uuid.toString();//生成随机名字
//                    String newImageName = uuids;
//                    //图片保存的地址
//                    String ImageName = PathConf.HimgPath + newImageName + ".jpg" ;
//                    URL url = null;
//                    try{
//                       // url = new URL("https://image.anosu.top/pixiv/direct?r18=1&keyword=");效率太低
//                        url = new URL("http://api.yujn.cn/api/sese.php");
//                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                        String newHttpUrl = connection.getHeaderField("Location");
//                        System.out.println("重定向后的新地址：" + newHttpUrl);
//                        url = new URL(newHttpUrl);//从重定向的接口拿数据
//
//                        DataInputStream dataInputStream = new DataInputStream(url.openStream());
//
//                        FileOutputStream fileOutputStream = new FileOutputStream(new File(ImageName));
//                        ByteArrayOutputStream output = new ByteArrayOutputStream();
//                        byte[] buffer = new byte[2048];
//                        int length;
//                        while ((length = dataInputStream.read(buffer)) > 0) {
//                            output.write(buffer, 0, length);
//                        }
//                        fileOutputStream.write(output.toByteArray());
//                        ExternalResource externalResource = ExternalResource.create(new File(ImageName));
//                        String newUrl = "localhost:9000/send/messgin?sendQQ=" + String.valueOf(g.getSender().getId()) + "@qq.com&imagePath=" + newImageName + ".jpg";
//                        HttpHelp.sendGetNoReturn(newUrl);
//                        g.getSubject().sendMessage("图片发送中");
//                        //Image image = g.getSubject().uploadImage(externalResource);
//                        //使用邮箱发送就不用这个了
//                        externalResource.close();//结束关流
//                        dataInputStream.close();
//                        fileOutputStream.close();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
                else if(g.getMessage().get(1).toString().startsWith("!搜歌")){
                    int index = g.getMessage().get(1).toString().indexOf("歌");
                    String musicname = g.getMessage().get(1).toString().substring(index + 1);
                    musicname = URLEncoder.encode(musicname, "utf-8");
                    String url = "localhost:9000/music/getmusic?name=" + musicname.trim();
                    //获取音乐集合
                    String s = HttpHelp.sendGet(url);
                    if (s == null || s.equals("")){
                        g.getSubject().sendMessage("没有查到数据呢，换个关键词试试吧");
                        return;
                    }


                    returns returns = JSONObject.parseObject(s, returns.class);
                    StringBuffer stringBuffer = new StringBuffer();
                    for(int i = 0 ; i<returns.getResult().getSongs().size() ; i++){
//                        StringBuffer stringBufferName = new StringBuffer();
//                        for(artists data : returns.getResult().getSongs().get(i).getArtists()){
//                            //作者名可能会有多个
//                            stringBufferName.append(data.getName());
//                        }
                        String collect = returns.getResult().getSongs().get(i).getArtists().stream().map(artists::getName).collect(Collectors.joining("/"));
                        stringBuffer.append("id:"+returns.getResult().getSongs().get(i).getId() + "\n"//id
                                + returns.getResult().getSongs().get(i).getName()+"---"+ collect + "\n"//歌名 + 作者
                                + returns.getResult().getSongs().get(i).getAlias()+"\n"+"\n");

                    }
                    g.getSubject().sendMessage("搜索到以下結果:"+"\n"+stringBuffer.toString());

                }else if(g.getMessage().get(1).toString().startsWith("!点歌")){
                    int index = g.getMessage().get(1).toString().indexOf("歌");
                    String id = g.getMessage().get(1).toString().substring(index + 1);

                    String url = "localhost:9000/music/sendmusic?id=" + id.trim();
                    String picrul = "localhost:9000/getUrl/picurl?id=" + id.trim();
                    String s = HttpHelp.sendGet(url);
                    //查询图片
                    String PicUrl = HttpHelp.sendGet(picrul);
                    if(s==null || s.equals("")){
                        g.getSubject().sendMessage("id不存在或id缓存过期");
                        return;
                    }
                    songs songs = JSONObject.parseObject(s, songs.class);
//                    StringBuffer stringBuffer = new StringBuffer();
//                    for(artists data : songs.getArtists()){
//                        //作者名可能会有多个
//                        stringBuffer.append(data.getName());
//                    }
                    String collect = songs.getArtists().stream().map(artists::getName).collect(Collectors.joining("/"));
                    MusicShare musicShare = new MusicShare(
                            MusicKind.NeteaseCloudMusic,
                            songs.getName(),//歌名
                            collect,
                            //点击跳转页面的地址
                            "https://y.music.163.com/m/song?id=" + songs.getId(),
                            //音乐封面地址
                            PicUrl,
                            //音频地址
                            "http://music.163.com/song/media/outer/url?id="+songs.getId()+"&&sc=wmv&tn=",
                            "[分享]"+songs.getName()
                    );
                    g.getSubject().sendMessage(musicShare);
                }
//                else if(g.getMessage().get(1).toString().equals("!签到")){
//                    RestTemplate restTemplate = new RestTemplate();
//                    User user = new User();
//                    user.setQqId(String.valueOf(g.getSender().getId()));
//                    user.setGroupId(g.getGroup().toString());
//                    String url = "http://localhost:9000/user/sign/";
//                    Map map = new HashMap();
//                    map.put("qqId",String.valueOf(g.getSender().getId()));
//                    map.put("groupId",g.getGroup().toString());
//                    HttpEntity httpEntity = new HttpEntity(map);
//                    ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
//                    //String messg = restTemplate.postForObject(url, user, String.class);
//                    System.out.println(exchange.getBody());
//                    g.getSubject().sendMessage(exchange.getBody());
//
//                }else if(g.getMessage().get(1).toString().equals("!我的金币")) {
//                    RestTemplate restTemplate = new RestTemplate();
//                    User user = new User();
//                    user.setQqId(String.valueOf(g.getSender().getId()));
//                    user.setGroupId(g.getGroup().toString());
//                    String url = "http://localhost:9000/user/getcoin/";
//                    Map map = new HashMap();
//                    map.put("qqId",String.valueOf(g.getSender().getId()));
//                    map.put("groupId",g.getGroup().toString());
//                    HttpEntity httpEntity = new HttpEntity(map);
//                    ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
//                    //构造at对象
//                    At at = new At(g.getSender().getId());
//                    MessageChain chain = new MessageChainBuilder()
//                            .append(at)
//                            .append(new PlainText(exchange.getBody()))
//                            .build();
//                    g.getSubject().sendMessage(chain);
//                }
                else if(str.startsWith("!问")){
                    System.out.println(str);
                    int index = g.getMessage().get(1).toString().indexOf("问");
                    //要问的内容
                    String content = g.getMessage().get(1).toString().substring(index + 1);
                    content = content.trim();
                    //清除空格
                    String s = HttpHelp.sendGet("localhost:9000/GPT/sendGPT?content=" +  URLEncoder.encode(content,"utf-8") +"&groupId="+g.getGroup().getId());
                    if("".equals(s) || null == s){
                        System.out.println("冷却");
                        //为空说明在冷却
                        //回复某个人的消息
                        MessageChain chain = new MessageChainBuilder() // 引用收到的消息并回复 "Hi!", 也可以添加图片等更多元素.
                                .append(new QuoteReply(g.getMessage()))
                                .append("访问过快，等待几秒钟吧")
                                .build();
                        //发送内容
                        g.getSubject().sendMessage(chain);
                        return;
                    }
                    GPTReturnData returnData = JSONObject.parseObject(s, GPTReturnData.class);
                    //回复某个人的消息
                    MessageChain chain = new MessageChainBuilder() // 引用收到的消息并回复 "Hi!", 也可以添加图片等更多元素.
                            .append(new QuoteReply(g.getMessage()))
                            .append(returnData.getText())
                            .build();
                    //发送内容
                    g.getSubject().sendMessage(chain);
                }
//                else if(g.getMessage().get(1).toString().startsWith("!风格设置")){
//                    //设置聊天风格
//                    int index = g.getMessage().get(1).toString().indexOf("置");
//                    String type = g.getMessage().get(1).toString().substring(index + 1);
//                    String s = HttpHelp.sendGet("localhost:9000/GPT/updataChatType?type=" + type);
//                    g.getSubject().sendMessage(s);
//                    return;
//                }
//                else if(g.getMessage().get(1).toString().startsWith("!风格查看")){
//                    String s = HttpHelp.sendGet("localhost:9000/GPT/getChatType");
//                    g.getSubject().sendMessage(s);
//                    return;
//                }
                else if(g.getMessage().get(1).toString().equals("!会话重置")){
                    System.out.println(HttpHelp.sendGet("localhost:9000/GPT/delsend?groupid="+String.valueOf(g.getGroup().getId())));
                }
                else{
                    g.getSubject().sendMessage("命令错误");
                }

            }catch (Exception e){
                e.printStackTrace();
                g.getSubject().sendMessage( "机器人寄了");
            }

        });
        //监听好友信息
        eventEventChannel.subscribeAlways(FriendMessageEvent.class , f -> {
            String str =  f.getMessage().get(1).toString();
            System.out.println(f.getMessage().get(1));
            if(str.equals("删除好友")){
                f.getFriend().delete();
            }else if(f.getMessage().get(1).toString().startsWith("随机色图")) {
                UUID uuid = UUID.randomUUID();
                String uuids = uuid.toString();//生成随机名字
                String newImageName = uuids;
                String ImageName = PathConf.HimgPath + newImageName + ".jpg";
                URL url = null;
                try {
                    String stType = "";
                    int index = f.getMessage().get(1).toString().indexOf("图");
                    System.out.println(f.getMessage().get(1).toString().substring(index + 1));
                    if (f.getMessage().get(1).toString().substring(index + 1).equals("1")) {
                        stType = "azurlane";
                    }
                    if (f.getMessage().get(1).toString().substring(index + 1).equals("2")) {
                        stType = "bluearchive";
                    }
                    if (f.getMessage().get(1).toString().substring(index + 1).equals("3")) {
                        stType = "genshinimpact";
                    }
                    if (f.getMessage().get(1).toString().substring(index + 1).equals("4")) {
                        stType = "arknights";
                    }
                    if (f.getMessage().get(1).toString().substring(index + 1).equals("5")) {
                        stType = "fate";
                    }
                    if (f.getMessage().get(1).toString().substring(index + 1).equals("6")) {
                        stType = "princess";
                    }
                    if (f.getMessage().get(1).toString().substring(index + 1).equals("7")) {
                        stType = "frontline";
                    }
                    if (f.getMessage().get(1).toString().substring(index + 1).equals("8")) {
                        stType = "honkai";
                    }
                    url = new URL("https://image.anosu.top/pixiv/direct?r18=1&keyword=" + stType);
                    DataInputStream dataInputStream = new DataInputStream(url.openStream());
                    File file = new File(ImageName);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = dataInputStream.read(buffer)) > 0) {
                        output.write(buffer, 0, length);
                    }
                    UrlToFile.getFileByUrl("https://image.anosu.top/pixiv/direct?r18=1&keyword=" + stType);

//                    File fileByUrl = UrlToFile.getFileByUrl("https://image.anosu.top/pixiv/direct?r18=1&keyword=honkai");
//                    System.out.println(fileByUrl);
//                    System.out.println(f.getMessage().get(1).toString().substring(index+1));

                    fileOutputStream.write(output.toByteArray());
                    ExternalResource externalResource = ExternalResource.create(new File(ImageName));

                    Image image = f.getSubject().uploadImage(externalResource);
                    f.getSubject().sendMessage(image);//发送图片
                    externalResource.close();//结束关流
                    dataInputStream.close();
                    fileOutputStream.close();
                    file.delete();
                } catch (Exception e) {
                    f.getSubject().sendMessage("请输入1-6数字");//发送图片
                    e.printStackTrace();
                }
            }
        });
        //监听有人加bot信息
        eventEventChannel.subscribeAlways(NewFriendRequestEvent.class , f -> {
            f.getMessage();
            System.out.println(f.getFromNick()+"加bot" + "信息:" + f.getMessage().toString());
            if(f.getMessage().equals("涩涩")){
                f.accept();
            }

        });
        //监听群临时会话
        eventEventChannel.subscribeAlways(GroupTempMessageEvent.class , f -> {
        });

        //有人加入群后
        eventEventChannel.subscribeAlways(MemberJoinEvent.class , f -> {
            NormalMember member = f.getMember();
            member.getGroup().sendMessage(  "新成员" + member.getNick() +"加入群,好耶");
            //member.sendMessage();该方法会在这个人加入群后私聊对他发送一条消息
        });
        //收到一条加群申请
        eventEventChannel.subscribeAlways(MemberJoinRequestEvent.class , f -> {
            String s = f.getMessage();
            //System.out.println(s);
            int index= s.lastIndexOf("：");
            s = s.substring(index+1);
            //System.out.println(s);
            if ("join".equals(s)){
                f.accept();//答案正确自动通过
            }

        });
        //有人退出群
        eventEventChannel.subscribeAlways(MemberLeaveEvent.class , x -> {
            Member member = x.getMember();
            x.getGroup().sendMessage(member.getId() + "离开本群力");
        });

//        eventEventChannel.subscribeAlways(MemberCardChangeEvent.class , x -> {
//            x.broadCastLock
//        });



        //有人戳一戳bot
        eventEventChannel.subscribeAlways(NudgeEvent.class , x -> {
            UserOrBot target = x.getTarget();
            Long s =  x.getBot().getId();
            System.out.println(s);
            if(target.getId() == x.getBot().getId()){
//                String ImageName = "/home/bot/UserImage/20221217191020.jpg";//从本地随机读取一张图片
//                ExternalResource externalResource = ExternalResource.create(new File(ImageName));
//                Image image = x.getSubject().uploadImage(externalResource);
//                MessageChain chain = new MessageChainBuilder()
//                        .append(new PlainText("憋戳了呜呜呜"))
//                        .append(image)
//                        .build();
//                x.getSubject().sendMessage(chain);//发送图片
//                try {
//                    externalResource.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                /**
                 * 获取 爬 图片
                 * */
                //https://api.andeer.top/API/img_crawl.php?qq=114514
                File file = GroupText("https://api.andeer.top/API/img_crawl.php?qq=" + x.getFrom().getId());
                ExternalResource externalResource = ExternalResource.create(file);
                try {
                Image image = x.getSubject().uploadImage(externalResource);
                MessageChain chain = new MessageChainBuilder()
                        .append(new PlainText("nnd戳我是吧,我也戳你!"))
                        .append(image)
                        .build();
                x.getSubject().sendMessage(chain);//发送图片
                file.delete();
                externalResource.close();
                //构造戳一戳
                    Nudge nudge = x.getFrom().nudge();
                    Contact subject = x.getSubject();
                    System.out.println(subject.getId());
                    nudge.sendTo(subject);
                } catch (IOException e) {
                    file.delete();
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

        });
    }
    //根据URL获取图片对象
    public File GroupText(String URL) {
        System.out.println(URL);
        URL url = null;
        try {
            url = new URL(URL);
            // System.out.println(URL);
            URLConnection connection = url.openConnection();
            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            long l = System.currentTimeMillis();
            String FileName = String.valueOf(l);//视频封面存服务器的名字
            File file = new File(PathConf.normalimgPath + FileName + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[2048];
            int numRead;
            while ((numRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, numRead);
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            //System.out.println("图片ok");
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}