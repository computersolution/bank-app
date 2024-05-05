package com.maybank.bankapp.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncryptionUtils {

	public static byte[] encrypt(byte[] data) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, keyGenerator.generateKey());
		return cipher.doFinal(data);
	}

	public static byte[] decrypt(byte[] encryptedData, SecretKey secretKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(encryptedData);
	}

	public static byte[] generateAccountNumber() {
		SecureRandom random = new SecureRandom();
		long randomNumber = Math.abs(random.nextLong() % 1_000_000_000L) + 1_000_000_000L;
		return String.valueOf(randomNumber).getBytes();
	}

}
