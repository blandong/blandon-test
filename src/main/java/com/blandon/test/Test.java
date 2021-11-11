package com.blandon.test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Test {
	public static void main(String[] args) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException {
		String encryptionKeyString =  "thisisa128bitkey";
	    String originalMessage = "This is a secret message";
	    byte[] encryptedMessageBytes = encryptMessage(originalMessage.getBytes(), encryptionKeyString.getBytes());
	    
	    String encodedEncryptedStringMessage = Base64.getEncoder().encodeToString(encryptedMessageBytes);
	    
	    System.out.println("Encoded encryptedMessage: "+encodedEncryptedStringMessage);
	    
	    //String decryptededMessage = new String(decryptMessage(Base64.getDecoder().decode(encodedEncryptedStringMessage), encryptionKeyString.getBytes()));
	    
	    //System.out.println("decryptededMessage: "+ decryptededMessage);
		
	}
	
	public static byte[] encryptMessage(byte[] message, byte[] keyBytes)
			  throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, 
			    BadPaddingException, IllegalBlockSizeException, NoSuchProviderException {
				//Security.addProvider(new BouncyCastleProvider());
				//Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding", "BC");
				Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding");
			    SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
			    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			    return cipher.doFinal(message);
			}
	
	public static byte[] decryptMessage(byte[] encryptedMessage, byte[] keyBytes) 
			  throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, 
			    BadPaddingException, IllegalBlockSizeException {
			 
			    Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
			    SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
			    cipher.init(Cipher.DECRYPT_MODE, secretKey);
			    return cipher.doFinal(encryptedMessage);
			}
}
