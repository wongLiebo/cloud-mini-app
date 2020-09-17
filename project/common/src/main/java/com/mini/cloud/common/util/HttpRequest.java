package com.mini.cloud.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpRequest {

    static Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    public static void main(String[] args) {
        //发送 GET 请求
        String s=HttpRequest.sendGet("http://v.qq.com/x/cover/kvehb7okfxqstmc.html?vid=e01957zem6o", "");
        System.out.println(s);

//        //发送 POST 请求
//        String sr=HttpRequest.sendPost("http://www.toutiao.com/stream/widget/local_weather/data/?city=%E4%B8%8A%E6%B5%B7", "");
//        JSONObject json = JSONObject.fromObject(sr);
//        System.out.println(json.get("data"));
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
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
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
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

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
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
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static byte[] sendPostForFile(String url, String param) {
       InputStream is = null;
       ByteArrayOutputStream os = null;
        PrintWriter out = null;
       byte[] buff = new byte[1024];
       int len = 0;

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
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
                    out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            is=conn.getInputStream();
            os = new ByteArrayOutputStream();
            while ((len=is.read(buff))!=-1){
                os.write(buff,0,len);
            }
            return os.toByteArray();

        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                os.close();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static String httpdoPostBodyHeader(String strUrl, String body, Map<String, String> headers, String encoding) {
        if (encoding == null) {  encoding = "UTF-8";  }
        StringBuilder builder = null;
        try {
            URL url = new URL(strUrl);
            HttpURLConnection httpURLConn = (HttpURLConnection) url.openConnection();
            // 设置编码方式
            httpURLConn.addRequestProperty("encoding", encoding);
            httpURLConn.setDoInput(true); // 使httpURLConn可以从网络获取数据
            httpURLConn.setDoOutput(true); // 使httpURLConn可以向互联网传输数据
            httpURLConn.setRequestMethod("POST"); // 设置POST请求方式
            if (headers != null) {
                Iterator<String> iteHeaders = headers.keySet().iterator();
                while (iteHeaders.hasNext()) {
                    String header = iteHeaders.next();
                    //设置请求头
                    httpURLConn.addRequestProperty(header, headers.get(header));
                }
            }
            if (body != null) {
                // 获取输出流
                OutputStream outStream = httpURLConn.getOutputStream();
                writeStrToOutputStream(body, outStream);
            }
            int responseCode = httpURLConn.getResponseCode();
            if(responseCode==200){
                // 获取输入流
                InputStream inStream = httpURLConn.getInputStream();
                builder = readSrtFromInputStream(inStream,encoding);
            }else{
                String responseMessage = httpURLConn.getResponseMessage();
                logger.info("responseCode: "+responseCode+", message: "+responseMessage);
                return responseCode+"";
            }
        } catch (MalformedURLException e) {
            logger.info("异常信息：" + e.getMessage());
        } catch (IOException e) {
            logger.info("异常信息：" + e.getMessage());
        }
        return builder.toString().trim();
    }

    private static void writeStrToOutputStream(String body, OutputStream outStream){
        OutputStreamWriter outWriter = null;
        BufferedWriter bufWriter = null;
        try {
            outWriter = new OutputStreamWriter(outStream);
            bufWriter = new BufferedWriter(outWriter);
            // 输出请求体
            bufWriter.write(body);
            bufWriter.flush();
            // 关闭输出流
        } catch (IOException e) {
            logger.info("异常信息：" + e.getMessage());
        }finally{
            try {
                bufWriter.close();
                outWriter.close();
                outStream.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
    /**
     * 从输入流中读取字符串
     */
    private static StringBuilder readSrtFromInputStream(InputStream inStream,String encoding) {
        StringBuilder builder = new StringBuilder();
        InputStreamReader inReader = null;
        BufferedReader bufReader = null;
        try {
            inReader = new InputStreamReader(inStream, encoding);
            bufReader = new BufferedReader(inReader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                builder.append(line + "\n");
            }
        } catch (Exception e) {
            logger.info("异常信息：" + e.getMessage());
        }finally{
            try {
                if(bufReader!=null) bufReader.close();
                if(inReader!=null) inReader.close();
                if(inStream!=null) inStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        return builder;
    }

}