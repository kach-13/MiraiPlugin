package com.example.pojo;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlToFile {
    //url转file
    public static File getFileByUrl(String fileUrl) {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        BufferedOutputStream stream = null;
        InputStream inputStream = null;
        File file = null;
        try {
            URL imageUrl = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inputStream = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            file = File.createTempFile("file", fileUrl.substring(fileUrl.lastIndexOf("."), fileUrl.length()));
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fileOutputStream);
            stream.write(outStream.toByteArray());
        } catch (Exception e) {
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (stream != null) {
                    stream.close();
                }
                outStream.close();
            } catch (Exception e) {
            }
        }
        return file;
    }
}
