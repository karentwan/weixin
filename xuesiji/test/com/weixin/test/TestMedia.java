package com.weixin.test;

import org.junit.Test;

import com.weixin.util.MediaUtil;
import com.weixin.util.MenuUtil;
import com.weixin.util.OauthUtil;

public class TestMedia {
	
	public static String NEWS_URL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";

//	@Test
//	public void testMedia() {
//		String str = MediaUtil.uploadMedia("UagCUSVSdgJsNWUuXCnRAUl0bkCM-bTS3cKbh2TBDvkMDr8pUYg6DTU3-NKbUbUoUXb-Fny2BxxNivBBAWrUXjkr_1Ced5-VTPed_QDnbGeOToSINa1Bb8ut0iq0_p1lDSVcAHAWLO", "image", "http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
//		System.out.println("str:" + str);
//	}
//	@Test
//	public void testMediaUp() {
//		String str = MediaUtil.uploadForeverPic("SlW__0bC8ZSqVyUrO9ObKQr5V11jD8IkW0hnLZa3_DmcC4KbbhC6EdQfSlDRu4O4k22TZCSi40q3j_7o5_VMRh8NTwY2ldHxzOtfHuAics5VTKkZ0-L2ians4HVXBtUuKOOeACASWW", "http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
//		System.out.println("str:" + str);
//	}
//	@Test
//	public void testNews() {
//		String str = NEWS_URL.replace("ACCESS_TOKEN", "YUFqLxxNTpjLZNULMOxhf1eQDPZuvxjhw1JOSpiBHvuvGwkY1eAICRMJId4YlJH02ZjhiQ63tp9IyFCHa0kFYLjiSuiDtM1MjpUU81ow_AwOvuPPr1M-7Pu9leSAi6DDDDWbAIAIIB");
//		String json = "{\"articles\":[\"title\":\"test\",\"thumb_media_id\":\"bfIIg_RUICSpHEfM0HFWbZ8uUPnsiC30ewVCpPgnS9Y\",\"author\":\"wan\",\"digest\":\"test\", \"show_cover_pic\":\"1\",\"content\":\"testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest\",\"content_source_url\":\"http://www.baidu.com\""+"]"+"}";
//		System.out.println("json:" + json);
//		String result = MenuUtil.httpRequest(str, "POST", json).toString();
//		System.out.println("result:" + result);
//	}
	@Test
	public void testThumb() {
		String str = MediaUtil.uploadPermanentMedia("JKiRKWSkPugzVXFh6CJMm-G_vWNrL9OKNZhro9Un3LHnJJOJuCiJ7F8PFz9dF0CDJHCcFgcgOYSjsZgalpWmEDaZ0DObWcSjxrgMbe9TMgG7OV3v5KKbXvwOuIeoJ8jJHIWiAHAKLZ", "thumb", "file.jpg", "F:\\picture\\Ð¡ÐÂ1.jpg");
		System.out.println("thumb:" + str);
	}
	
//	@Test
//	public void testUrlEncode() {
//		String url = "http://070411.com/weixin/oauthServlet";
//System.out.println("result:" + OauthUtil.urlEncodeUTF8(url));
//		
//	}
	
//	@Test
//	public void testUpload() {
//		String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";
//		url.replace("ACCESS_TOKEN", "JKiRKWSkPugzVXFh6CJMm-G_vWNrL9OKNZhro9Un3LHnJJOJuCiJ7F8PFz9dF0CDJHCcFgcgOYSjsZgalpWmEDaZ0DObWcSjxrgMbe9TMgG7OV3v5KKbXvwOuIeoJ8jJHIWiAHAKLZ");
//		
//	}
	
}
