package com.com.msb.demo;

import org.junit.Test;

/**
 * @author 马士兵教育:chaopengfei
 * @date 2021/1/7
 */
public class InternTest {

	@Test
	public void testIntern(){

		String orderId = "orderId";
		String str3 = new String(orderId);
		String str4 = new String(orderId);

		System.out.println(str3 == str4); // false
		System.out.println(str3.intern() == str4.intern()); // true

		System.out.println(1);

	}
}
