package com.example.pojo;

import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpHelp {


    //获取图片数据
    public static String GetImageString(Integer status){
        String s = "";
        s = sendGet("localhost:9000/send/getSend/"+status);//缓存服务器
        if(s.equals("")){
            System.out.println("走服务器");
            s = sendGet("localhost:9000/image/getImage/"+status);//访问外部图片服务器
        }
        return s;
    }

    public static String sendGetRequest(String urlString, Map<String, String> headers) throws IOException {
        // 创建URL对象
        URL url = new URL(urlString);
        // 打开连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // 设置请求头
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }

        // 设置请求方式为GET
        conn.setRequestMethod("GET");

        // 从输入流获取响应内容
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer response = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // 关闭连接
        conn.disconnect();

        // 返回响应内容
        return response.toString();
    }
    //访问
    public static String sendGet(String url) {
        String result = "";//返回的数据
        BufferedReader in = null;
        try {
            String urlNameString = "http://"+ url ;
            System.out.println(urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {

            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    //访问无返回值的请求
    public static void sendGetNoReturn(String url) {
        String result = "";//返回的数据
        BufferedReader in = null;
        try {
            String urlNameString = "http://"+ url ;
            System.out.println(urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url   请求URL
     * @param param 请求体字符串
     * @return 响应结果
     */
    public static String sendPost(String url, String param, Map<String, String> requestheader) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = null;
            conn = realUrl.openConnection();
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(1000);
            if (requestheader != null) {
                // 自定义请求属性
                for (String key : requestheader.keySet())
                    conn.setRequestProperty(key, requestheader.get(key));
            } else {
                // 设置通用的请求属性
                conn.setRequestProperty("Content-Type","application/json");
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("user-agent",
                        "User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
                //可设置header内的如token、密钥等功能，当然可以将此作为接口正式参数进行传递
                //conn.addRequestProperty("appkey","123465789");
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            //out = new PrintWriter(conn.getOutputStream());
            out = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("authorization","Bearer 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");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String wlwHttpURLConnection(String url, String data) {
        StringBuffer sb = new StringBuffer();
        try {
            URL realUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);

            //请求方式为post
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);//自动处理重定向
            connection.setRequestProperty("Charsert", "UTF-8"); //设置请求编码
            // 增加请求头 start
            connection.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            connection.setRequestProperty("authorization","Bearer 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");

            // 增加请求头 end
            connection.connect();

            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());// 创建输入输出流,用于往连接里面输出携带的参数
            String query = data;
            System.out.println(query);
            query = new String(query.getBytes("iso8859-1"),"UTF-8");
            System.out.println(query);
            dataout.write(query.getBytes("UTF-8"));
            dataout.flush();
            dataout.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "UTF-8");
                sb.append(lines);
            }
            reader.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return sb.toString();
    }

}
