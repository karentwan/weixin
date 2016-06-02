package com.weixin.extendsTest;

import org.junit.Test;

public class Main {

	@Test
	public void test() {
		Base b = new Derive();
System.out.println("name:" + b.name);
		b.setName("test");
		System.out.println("b.name:" + b.name);
		Derive d = (Derive)b;
		System.out.println("d.name:" + d.name);
	}
	
}
