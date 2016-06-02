package com.weixin.test;

import org.junit.Test;
import com.weixin.util.OauthUtil;

public class GenerateUrl {
	
	@Test
	public void testUrlEncode() {
		String url = "";
System.out.println("result:" + OauthUtil.urlEncodeUTF8(url));
		
	}
}
