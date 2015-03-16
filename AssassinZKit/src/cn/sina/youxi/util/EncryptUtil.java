package cn.sina.youxi.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 类说明： Encrypt工具类
 * 
 * @author Cundong
 * @date 2013-3-25
 * @version 1.0
 */
public class EncryptUtil {

	static {
		System.loadLibrary("Encrypt");
	}

	public static native String getDESKey();

	public static native String getSSOM();

	// 采用DES的CBC模式进行加密，补齐方式为PKCS5Padding
	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	/**
	 * des加密
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public static byte[] encode(String key, byte[] data) {
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(key.substring(0, 8)
					.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String encrypt(String strSrc, String encName) {
		// parameter strSrc is a string will be encrypted,
		// parameter encName is the algorithm name will be used.
		// encName dafault to "MD5"
		MessageDigest md = null;
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		try {
			if (encName == null || encName.equals("")) {
				encName = "MD5";
			}

			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");
			return null;
		}

		return strDes;
	}

	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	/**
	 * 获取字符串SHA1值
	 * 
	 * @param input
	 * @return
	 */
	public static String getSHA1(String input) {

		return encrypt(input, "SHA-1");
	}
}