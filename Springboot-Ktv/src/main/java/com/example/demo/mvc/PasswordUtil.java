package com.example.demo.mvc;

import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

	public static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}
	
	public static String hashPassword(String password, String salt) {
		String saltedPassword = password + salt;
		return WebKeyUtil.md5(saltedPassword);
	}
	
	public static void main(String[] args) {
		String[] passwords = {"password4", "password5", "password6", "password7"};
        for (String password : passwords) {
            String salt = generateSalt();
            String hashedPassword = hashPassword(password, salt);
            System.out.println("Password: " + password);
            System.out.println("Salt: " + salt);
            System.out.println("Hashed Password: " + hashedPassword);
            System.out.println();
        }
    }
}
