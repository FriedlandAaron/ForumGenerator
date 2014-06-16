package utils;

import org.jasypt.digest.StandardStringDigester;


public class PasswordEncryptor {
	
	private static final StandardStringDigester digester = new StandardStringDigester();
	public static String encrypt(String password) {
		return digester.digest(password);
	}
	
	public static boolean isMatch(String input, String digest) {
		return digester.matches(input, digest);
	}
	

}
