package com.weixin.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * 微信多媒体的工具类
 * @author wan
 */
public class MediaUtil {
	/*
	 * 上传媒体文件到微信服务器需要请求的地址
	 */
	public static String MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	/*
	 * 上传图片到微信服务器获取一个url地址
	 */
	public static String PIC_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	/**
	 * 上传永久文件
	 */
	public static String FOREVER_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";
	/**
	 * 上传多媒体数据到微信服务器
	 * @param accessToken 从微信获取到的access_token
	 * @param mediaUrl 来自网络上面的媒体文件地址
	 */
	public static String uploadMedia(String accessToken, String type, String mediaFileUrl) {
		String mediaStr = MEDIA_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		return uploadUtil(accessToken, type, mediaFileUrl, mediaStr);
	}
	/**
	 * 通过传过来的contentType判断是哪一种类型
	 * @param contentType 获取来自连接的contentType
	 * @return
	 */
	public static String judgeType(String contentType) {
		String fileExt = "";
		if("image/jpeg".equals(contentType)) {
			fileExt = ".jpg";
		} else if("audio/mpeg".equals(contentType)) {
			fileExt = ".mp3";
		} else if("audio/amr".equals(contentType)) {
			fileExt = ".amr";
		} else if("video/mp4".equals(contentType)) {
			fileExt = ".mp4";
		} else if("video/mpeg4".equals(contentType)) {
			fileExt = ".mp4";
		}
		return fileExt;
	}
	/**
	 * 上传图片到微信服务器
	 * @param accessToken access_token
	 * @param mediaFileUrl 需要上传的文件url
	 * @return 一个json数据包，里面封装了url
	 */
	public static String uploadPic(String accessToken, String mediaFileUrl) {
		String mediaStr = PIC_URL.replace("ACCESS_TOKEN", accessToken);
		return uploadUtil(accessToken, "image", mediaFileUrl, mediaStr);
		
	}
	/**
	 * 辅助方法
	 */
	private static String uploadUtil(String accessToken, String type, String mediaFileUrl, String mediaStr) {
		StringBuffer resultStr = null;
		//拼接url地址
		URL mediaUrl;
		try {
			String boundary = "----WebKitFormBoundaryOYXo8heIv9pgpGjT";
			URL url = new URL(mediaStr);
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			//让输入输出流开启
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			//使用post方式请求的时候必须关闭缓存
			urlConn.setUseCaches(false);
			//设置请求头的Content-Type属性
			urlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);
			urlConn.setRequestMethod("POST");
			//获取输出流，使用输出流拼接请求体
			OutputStream out = urlConn.getOutputStream();
			
			//读取文件的数据,构建一个GET请求，然后读取指定地址中的数据
			mediaUrl = new URL(mediaFileUrl);
			HttpURLConnection mediaConn = (HttpURLConnection)mediaUrl.openConnection();
			//设置请求方式
			mediaConn.setRequestMethod("GET");
			//设置可以打开输入流
			mediaConn.setDoInput(true);
			//获取传输的数据类型
			String contentType = mediaConn.getHeaderField("Content-Type");
			//将获取大到的类型转换成扩展名
			String fileExt = judgeType(contentType);
			//获取输入流，从mediaURL里面读取数据
			InputStream in = mediaConn.getInputStream();
			BufferedInputStream bufferedIn = new BufferedInputStream(in);
			//数据读取到这个数组里面
			byte[] bytes = new byte[1024];
			int size = 0;
			//使用outputStream流输出信息到请求体当中去
			out.write(("--"+boundary+"\r\n").getBytes());
			out.write(("Content-Disposition: form-data; name=\"media\";\r\n"
					+ "filename=\""+(new Date().getTime())+fileExt+"\"\r\n"
							+ "Content-Type: "+contentType+"\r\n\r\n").getBytes());
			while( (size = bufferedIn.read(bytes)) != -1) {
				//如果调用的是out.write(bytes);那么写进去的将会是整个数组里面的内容
				//相当于out.write(bytes, 0, bytes.length);
				out.write(bytes, 0, size);
			}
			//切记，这里的换行符不能少，否则将会报41005错误
			out.write(("\r\n--"+boundary+"--\r\n").getBytes());
			
			bufferedIn.close();
			in.close();
			mediaConn.disconnect();
			
			InputStream resultIn = urlConn.getInputStream();
			InputStreamReader reader = new InputStreamReader(resultIn);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String tempStr = null;
			resultStr = new StringBuffer(); 
			while((tempStr = bufferedReader.readLine()) != null) {
				resultStr.append(tempStr);
			}
			bufferedReader.close();
			reader.close();
			resultIn.close();
			urlConn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resultStr.toString();
	}
	/**
	 * 上传永久图片素材到微信服务器
	 * @param accessToken
	 * @param mediaStr
	 * @return
	 */
	public static String uploadForeverPic(String accessToken, String mediaStr) {
		String picUrl = FOREVER_URL.replace("ACCESS_TOKEN", accessToken);
		return uploadUtil(accessToken, "image", mediaStr, picUrl);
	}
	
	public static String uploadThumb(String accessToken, String mediaStr) {
		String picUrl = MEDIA_URL.replace("ACCESS_TOKEN", accessToken);
		return uploadUtil(accessToken,  "thumb", mediaStr, picUrl);
	}
	
	/**
	 * 从文件上传多媒体数据到微信服务器
	 * @param accessToken
	 * @return
	 */
	public static String uploadPermanentMedia(String accessToken, String type,
            String filename, String mediaFileUrl) {
		
		String result = null;
		
		try {
            // 拼装请求地址
            String uploadMediaUrl = "http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=##ACCESS_TOKEN##&type=##type##";
            uploadMediaUrl = uploadMediaUrl
                            .replace("##ACCESS_TOKEN##", accessToken).replace("##type##", type);

            URL url = new URL(uploadMediaUrl);

            File file = new File(mediaFileUrl);

            if (!file.exists() || !file.isFile()) {
                    System.out.println("上传的文件不存在");
            }

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式

            con.setDoInput(true);

            con.setDoOutput(true);

            con.setUseCaches(false); // post方式不能使用缓存

            // 设置请求头信息

            con.setRequestProperty("Connection", "Keep-Alive");

            con.setRequestProperty("Charset", "UTF-8");

            // 设置边界

            String BOUNDARY = "----------" + System.currentTimeMillis();

            con.setRequestProperty("Content-Type",
                            "multipart/form-data; boundary="

                            + BOUNDARY);

            // 请求正文信息

            // 第一部分：

            StringBuilder sb = new StringBuilder();

            sb.append("--"); // 必须多两道线

            sb.append(BOUNDARY);

            sb.append("\r\n");

            sb.append("Content-Disposition: form-data;name=\"media\";filename=\""

                            + filename + "\" \r\n");

            sb.append("Content-Type:application/octet-stream\r\n\r\n");

            byte[] head = sb.toString().getBytes("utf-8");

            // 获得输出流

            OutputStream out = new DataOutputStream(con.getOutputStream());

            // 输出表头
           
            out.write(head);

            // 文件正文部分
            // 把文件已流文件的方式 推入到url中
            DataInputStream in = new DataInputStream(new FileInputStream(file));

            int bytes = 0;

            byte[] bufferOut = new byte[1024];

            while ((bytes = in.read(bufferOut)) != -1) {

                    out.write(bufferOut, 0, bytes);

            }

            in.close();

            // 结尾部分

            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线

            out.write(foot);

            out.flush();

            out.close();

            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = null;
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con
            .getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
		} catch( Exception e) {
			e.printStackTrace();
		}
        return result;
	
	
	}
	
	
	
}
