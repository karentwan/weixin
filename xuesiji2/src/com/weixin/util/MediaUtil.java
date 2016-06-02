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
 * ΢�Ŷ�ý��Ĺ�����
 * @author wan
 */
public class MediaUtil {
	/*
	 * �ϴ�ý���ļ���΢�ŷ�������Ҫ����ĵ�ַ
	 */
	public static String MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	/*
	 * �ϴ�ͼƬ��΢�ŷ�������ȡһ��url��ַ
	 */
	public static String PIC_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	/**
	 * �ϴ������ļ�
	 */
	public static String FOREVER_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";
	/**
	 * �ϴ���ý�����ݵ�΢�ŷ�����
	 * @param accessToken ��΢�Ż�ȡ����access_token
	 * @param mediaUrl �������������ý���ļ���ַ
	 */
	public static String uploadMedia(String accessToken, String type, String mediaFileUrl) {
		String mediaStr = MEDIA_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		return uploadUtil(accessToken, type, mediaFileUrl, mediaStr);
	}
	/**
	 * ͨ����������contentType�ж�����һ������
	 * @param contentType ��ȡ�������ӵ�contentType
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
	 * �ϴ�ͼƬ��΢�ŷ�����
	 * @param accessToken access_token
	 * @param mediaFileUrl ��Ҫ�ϴ����ļ�url
	 * @return һ��json���ݰ��������װ��url
	 */
	public static String uploadPic(String accessToken, String mediaFileUrl) {
		String mediaStr = PIC_URL.replace("ACCESS_TOKEN", accessToken);
		return uploadUtil(accessToken, "image", mediaFileUrl, mediaStr);
		
	}
	/**
	 * ��������
	 */
	private static String uploadUtil(String accessToken, String type, String mediaFileUrl, String mediaStr) {
		StringBuffer resultStr = null;
		//ƴ��url��ַ
		URL mediaUrl;
		try {
			String boundary = "----WebKitFormBoundaryOYXo8heIv9pgpGjT";
			URL url = new URL(mediaStr);
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			//���������������
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			//ʹ��post��ʽ�����ʱ�����رջ���
			urlConn.setUseCaches(false);
			//��������ͷ��Content-Type����
			urlConn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);
			urlConn.setRequestMethod("POST");
			//��ȡ�������ʹ�������ƴ��������
			OutputStream out = urlConn.getOutputStream();
			
			//��ȡ�ļ�������,����һ��GET����Ȼ���ȡָ����ַ�е�����
			mediaUrl = new URL(mediaFileUrl);
			HttpURLConnection mediaConn = (HttpURLConnection)mediaUrl.openConnection();
			//��������ʽ
			mediaConn.setRequestMethod("GET");
			//���ÿ��Դ�������
			mediaConn.setDoInput(true);
			//��ȡ�������������
			String contentType = mediaConn.getHeaderField("Content-Type");
			//����ȡ�󵽵�����ת������չ��
			String fileExt = judgeType(contentType);
			//��ȡ����������mediaURL�����ȡ����
			InputStream in = mediaConn.getInputStream();
			BufferedInputStream bufferedIn = new BufferedInputStream(in);
			//���ݶ�ȡ�������������
			byte[] bytes = new byte[1024];
			int size = 0;
			//ʹ��outputStream�������Ϣ�������嵱��ȥ
			out.write(("--"+boundary+"\r\n").getBytes());
			out.write(("Content-Disposition: form-data; name=\"media\";\r\n"
					+ "filename=\""+(new Date().getTime())+fileExt+"\"\r\n"
							+ "Content-Type: "+contentType+"\r\n\r\n").getBytes());
			while( (size = bufferedIn.read(bytes)) != -1) {
				//������õ���out.write(bytes);��ôд��ȥ�Ľ����������������������
				//�൱��out.write(bytes, 0, bytes.length);
				out.write(bytes, 0, size);
			}
			//�мǣ�����Ļ��з������٣����򽫻ᱨ41005����
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
	 * �ϴ�����ͼƬ�زĵ�΢�ŷ�����
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
	 * ���ļ��ϴ���ý�����ݵ�΢�ŷ�����
	 * @param accessToken
	 * @return
	 */
	public static String uploadPermanentMedia(String accessToken, String type,
            String filename, String mediaFileUrl) {
		
		String result = null;
		
		try {
            // ƴװ�����ַ
            String uploadMediaUrl = "http://api.weixin.qq.com/cgi-bin/material/add_material?access_token=##ACCESS_TOKEN##&type=##type##";
            uploadMediaUrl = uploadMediaUrl
                            .replace("##ACCESS_TOKEN##", accessToken).replace("##type##", type);

            URL url = new URL(uploadMediaUrl);

            File file = new File(mediaFileUrl);

            if (!file.exists() || !file.isFile()) {
                    System.out.println("�ϴ����ļ�������");
            }

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST"); // ��Post��ʽ�ύ����Ĭ��get��ʽ

            con.setDoInput(true);

            con.setDoOutput(true);

            con.setUseCaches(false); // post��ʽ����ʹ�û���

            // ��������ͷ��Ϣ

            con.setRequestProperty("Connection", "Keep-Alive");

            con.setRequestProperty("Charset", "UTF-8");

            // ���ñ߽�

            String BOUNDARY = "----------" + System.currentTimeMillis();

            con.setRequestProperty("Content-Type",
                            "multipart/form-data; boundary="

                            + BOUNDARY);

            // ����������Ϣ

            // ��һ���֣�

            StringBuilder sb = new StringBuilder();

            sb.append("--"); // �����������

            sb.append(BOUNDARY);

            sb.append("\r\n");

            sb.append("Content-Disposition: form-data;name=\"media\";filename=\""

                            + filename + "\" \r\n");

            sb.append("Content-Type:application/octet-stream\r\n\r\n");

            byte[] head = sb.toString().getBytes("utf-8");

            // ��������

            OutputStream out = new DataOutputStream(con.getOutputStream());

            // �����ͷ
           
            out.write(head);

            // �ļ����Ĳ���
            // ���ļ������ļ��ķ�ʽ ���뵽url��
            DataInputStream in = new DataInputStream(new FileInputStream(file));

            int bytes = 0;

            byte[] bufferOut = new byte[1024];

            while ((bytes = in.read(bufferOut)) != -1) {

                    out.write(bufferOut, 0, bytes);

            }

            in.close();

            // ��β����

            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// ����������ݷָ���

            out.write(foot);

            out.flush();

            out.close();

            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = null;
            // ����BufferedReader����������ȡURL����Ӧ
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
