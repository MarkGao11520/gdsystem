package com.iss.yzsxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GdsystemApplicationTests {

	@Test
	public void contextLoads() {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(false);
		String pwd = md5.encodePassword("123456", null);
		String pwd1 = md5.encodePassword("123456", null);
		System.out.println("MD5: " + pwd + " len=" + pwd.length());
		System.out.println("MD5: " + pwd1 + " len=" + pwd.length());
	}

}
