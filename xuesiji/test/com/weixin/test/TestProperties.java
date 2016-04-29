package com.weixin.test;

import org.junit.Test;

import com.weixin.util.WeixinUtil;

public class TestProperties {

	@Test
	public void testProperties() {
		System.out.println("driver:" + WeixinUtil.prop.getProperty("driver"));
		System.out.println("url:" + WeixinUtil.prop.getProperty("url"));
		System.out.println("user:" + WeixinUtil.prop.getProperty("user"));
		System.out.println("psd:" + WeixinUtil.prop.getProperty("psd"));
	}
	
	
}
