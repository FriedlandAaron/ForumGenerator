package Tests.UnitTest.utils;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.PasswordEncryptor;


public class PasswordEncryptorTest extends TestCase  {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() {
		String password1 = "badpassword";

		String password2 = "A8rnm32SDfjkl3";
		
//		System.out.println(password1);
//		System.out.println(password2);
		
		String ep1 = PasswordEncryptor.encrypt(password1);
		
		String ep2 = PasswordEncryptor.encrypt(password2);
		
//		System.out.println(ep1);
//		System.out.println(ep2);
		
//		assertTrue(p1.equals(ep1));
		assertTrue(PasswordEncryptor.isMatch(password2, ep2));
		assertFalse(PasswordEncryptor.isMatch(password1, ep2));
		assertFalse(PasswordEncryptor.isMatch(password2, ep1));
	}

}
