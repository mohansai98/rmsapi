package com.md5.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.md5.model.ReqParameters;

@RestController
public class HomeController {

	@PostMapping("/encodeUrl")
	public void getHello(@RequestBody ReqParameters params) {
		System.out.println(params);
		int hoaCnt = 1;
		int amtCnt = 1;
		String requestString = "return_url=" + params.getReturnUrl() + " | txn_amount=" + params.getTxnAmount()
				+ " | grn=" + params.getGrn() + " | remitter_name=" + params.getRemitterName() + " | remmiter_address="
				+ params.getRemmiterAddress() + " | pan=" + params.getPan() + " | pay_mode=" + params.getPayMode()
				+ " | ";

		for (String hoa : params.getHoa()) {
			requestString += "hoa" + hoaCnt + "=" + hoa + " | ";
			hoaCnt++;
		}
		for (BigDecimal amount : params.getAmount()) {
			if (amtCnt == params.getAmount().size()) {
				requestString += "amount" + amtCnt + "=" + amount;
			} else {
				requestString += "amount" + amtCnt + "=" + amount + " | ";
			}
			amtCnt++;
		}

		//String checkSum = checkSumMD5(requestString);
		System.out.println("String : " + requestString);
		String checkSum = sha256Hash(requestString);
		System.out.println("checksum " + checkSum + " Size " + checkSum.length());

		String newRequestString = params.getReturnUrl() + " | txn_amount=" + params.getTxnAmount() + " | grn="
				+ params.getGrn() + " | checkSum=" + checkSum;

		String encData = encryptUrlWithHash(newRequestString, params.getBankCode());
		System.out.println("encoded data " + encData);

		String decData = decryptUrlWithHash(encData);
		System.out.println("decrypted data " + decData);

	}

	public String sha256Hash(String input) {
		byte[] passHash = null;
		StringBuilder hexString = null;
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			byte[] passBytes = input.getBytes();
			passHash = sha256.digest(passBytes);
			BigInteger number = new BigInteger(1, passHash);
			hexString = new StringBuilder(number.toString(16));
			while (hexString.length() < 32) {
				hexString.insert(0, '0');
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return hexString.toString();
	}

	public String checkSumMD5(String input) {
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public String encryptUrlWithHash(String url, String bankCode) {
		String secKey = "1234567890abcdef";
		byte[] key;
		String res = "";
		try {
			key = secKey.getBytes("UTF-8");
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			byte[] iv = key;
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			res = encrypt(url, secretKey, ivSpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	private String encrypt(String plainText, SecretKeySpec secretKey, IvParameterSpec ivSpec) {
		byte[] encryptedByte = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] plainTextByte = plainText.getBytes("UTF-8");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
			encryptedByte = cipher.doFinal(plainTextByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(Base64.encodeBase64(encryptedByte));
	}

	public String decryptUrlWithHash(String encData) {
		Resource resource = new ClassPathResource("uat.key");
		
		//String secKey = "1234567890abcdef";
		byte[] key;
		String res = "";
		try {
			System.out.println("resource : "+new InputStreamReader(resource.getInputStream()));
			String secKey = new BufferedReader(new InputStreamReader(resource.getInputStream())).readLine();
			System.out.println("secKey "+secKey);
			key = secKey.getBytes("UTF-8");
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			byte[] iv = key;
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			res = decrypt(encData, secretKey, ivSpec);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	private String decrypt(String encData, SecretKeySpec secretKey, IvParameterSpec ivSpec) {
		byte[] decryptedByte = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
			decryptedByte = cipher.doFinal(Base64.decodeBase64(encData));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(decryptedByte);
	}

}
