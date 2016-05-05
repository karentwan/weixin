package com.weixin.test;

import org.junit.Test;
import com.weixin.util.OauthUtil;

public class GenerateUrl {
	
	@Test
	public void testUrlEncode() {
		String url = "http://weixin2.ngrok.natapp.cn/weixin2.0/instruOauth";
System.out.println("result:" + OauthUtil.urlEncodeUTF8(url));
		
	}
}
