package com.weixin.test;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.weixin.pojo.Button;
import com.weixin.pojo.ClickButton;
import com.weixin.pojo.ComplexButton;
import com.weixin.pojo.Menu;
import com.weixin.pojo.ViewButton;
import com.weixin.util.MenuUtil;

public class PropertiseTest {

//	N6OAWvH_dNhF1yAlE5BqIqM_3OKp80Ii2DXhP-6I5o7MDnpyWDnYLqRlqKnuItIPxCJYzIncAqRjAbbn80V4N3smu86ulTIeRElJFrws6so5xgGytRXLWLVuXamEeC6rSSRjAEAYYX	
//	@Test
//	public void testPropertise() throws FileNotFoundException, IOException {
//		String appId = WeixinUtil.prop.getProperty("appId");
//		String secret = WeixinUtil.prop.getProperty("secret");
//		String token = WeixinUtil.prop.getProperty("token");
//		System.out.println("appId:"+appId+"\nsecret:"+secret+"\ntoken:" + token);
//		
//	}
//	@Test
//	public void testGetAccessToken() {
//		AccessToken at = MenuUtil.getAcsessToken(WeixinUtil.prop.getProperty("appId"), WeixinUtil.prop.getProperty("secret"));
//		System.out.println("acess_token:" + at.getToken());
//		try {
//			FileOutputStream out = new FileOutputStream(new File("E:\\office文档\\access_token.txt"));
//			out.write(at.getToken().getBytes());
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
	
//	@Test
//	public void testMenu() {
//		ComplexButton c1 = new ComplexButton();
//		c1.setName("查询神器");
//		ClickButton c11 = new ClickButton();
//		c11.setName("查用电");
//		c11.setType("click");
//		c11.setKey("11");
//		ClickButton c12 = new ClickButton();
//		c12.setName("成绩及课表");
//		c12.setType("click");
//		c12.setKey("12");
//		ViewButton c13 = new ViewButton();
//		c13.setName("四六级查分");
//		c13.setType("view");
//		c13.setUrl("http://cet.redrock-team.com/");
//		ClickButton c14 = new ClickButton();
//		c14.setName("微社区");
//		c14.setType("click");
//		c14.setKey("14");
//		c1.setSub_button(new Button[]{c11, c12, c13, c14});
//		ComplexButton c2 = new ComplexButton();
//		c2.setName("有话想说");
//		ViewButton c21 = new ViewButton();
//		c21.setName("学思微博");
//		c21.setType("view");
//		c21.setUrl("http://m.weibo.cn/p/1005055880888843/home?jumpfrom=weibocom");
//		ClickButton c22 = new ClickButton();
//		c22.setName("我要投稿");
//		c22.setType("click");
//		c22.setKey("22");
//		ViewButton c23 = new ViewButton();
//		c23.setName("我要表白");
//		c23.setType("view");
//		c23.setUrl("http://www.baidu.com");
//		ClickButton c24 = new ClickButton();
//		c24.setName("学思FM");
//		c24.setType("click");
//		c24.setKey("24");
//		ViewButton c25 = new ViewButton();
//		c25.setName("往期回顾");
//		c25.setType("view");
//		c25.setUrl("http://www.baidu.com");
//		c2.setSub_button(new Button[]{c21, c22, c23, c24, c25});
//		ComplexButton c3 = new ComplexButton();
//		c3.setName("教官队");
//		ViewButton c31 = new ViewButton();
//		c31.setName("风雨教官路");
//		c31.setType("view");
//		c31.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwNjgzMDY5MA==&mid=417337919&idx=1&sn=3b416b757b5468f2b1355fa512278b54&scene=18#wechat_redirect");
//		ViewButton c32 = new ViewButton();
//		c32.setName("晨读材料");
//		c32.setType("view");
//		c32.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwNjgzMDY5MA==&mid=416738278&idx=1&sn=0c8ad93c4250dd3df250ff2fe6b8b195&scene=18#wechat_redirect");
//		ClickButton c33 = new ClickButton();
//		c33.setName("微聚焦");
//		c33.setType("click");
//		c33.setKey("33");
//		ViewButton c34 = new ViewButton();
//		c34.setName("我们");
//		c34.setType("view");
//		c34.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwNjgzMDY5MA==&mid=416737979&idx=1&sn=518ee3b71ff183f2edd4fb0828c2aab8&scene=18#wechat_redirect");
//		ClickButton c35 = new ClickButton();
//		c35.setName("view");
//		c35.setType("click");
//		c35.setKey("35");
//		c3.setSub_button(new Button[]{c31, c32, c33, c34, c35});
//		Menu menu = new Menu();
//		menu.setButton(new Button[]{c1, c2, c3});
//		String str = JSONObject.fromObject(menu).toString();
//System.out.println("str:" + str);
//		int result = MenuUtil.createMenu("p_e1qc19mAV47hXebPnEa9wuJtULgnENZ2yWeeOKciLDRE39mKIb_lZ1HG0DS4Y4tiJenx7Eupdb0U901bOXn2Ox6BvUVonKeHOs3IR8apExzgS8vzJGXJ1cBjitNm3lXHPdAGALVD", str);
//		System.out.println("result:" + result);
//	}
//	@Test
//	public void testIsCell() throws IllegalArgumentException, IllegalAccessException {
//		Test2 t2 = new Test2();
//		t2.setName("wan");
//		Test1 t1 = new Test1();
//		t1.setTest(t2);
//		t1.setName("wer");
//		t1.setAge("21");
//		System.out.println("count:" + isCell(t1, 0));
//	}
//	
//	
//	private int isCell(Object obj, int count) throws IllegalArgumentException, IllegalAccessException {
//		Class clazz = obj.getClass();
//		if(clazz == Integer.class || clazz == String.class) {
//			return count;
//		}
//		Field[] fields = clazz.getDeclaredFields();
//		int t1 = count, t2 = 0;
//		for(int i = 0; i < fields.length; i++) {
//			fields[i].setAccessible(true);
//			Object o = fields[i].get(obj);
//			Class clazz1 = o.getClass();
//			if(clazz1 == Integer.class || clazz1 == String.class) {
//				continue;
//			}
//			t2 = isCell(o, count + 1);
//			if( t1 < t2) {
//				t1 = t2;
//			}
//		}
//		return t1;
//	}

	
}
 