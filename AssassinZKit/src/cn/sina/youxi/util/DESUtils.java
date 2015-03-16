package cn.sina.youxi.util;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESUtils {

	private static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

	public static byte[] encode(String key, byte[] data) {
		try {
			String ivKey = key.substring(0, 8);

			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);

			byte[] bytes = cipher.doFinal(data);

			StringBuffer sb = new StringBuffer();
			for (int n = 0; n < bytes.length; n++) {
				String stmp = (java.lang.Integer.toHexString(bytes[n] & 0XFF));

				if (stmp.length() == 1) {
					sb.append("0" + stmp);
				} else {
					sb.append(stmp);
				}
			}

			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encodeString(String key, byte[] data) {
		try {
			String ivKey = key.substring(0, 8);

			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);

			byte[] bytes = cipher.doFinal(data);

			StringBuffer sb = new StringBuffer();
			for (int n = 0; n < bytes.length; n++) {
				String stmp = (Integer.toHexString(bytes[n] & 0XFF));

				if (stmp.length() == 1) {
					sb.append("0" + stmp);
				} else {
					sb.append(stmp);
				}
			}

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static byte[] decode(String key, byte[] data) {
		try {
			String ivKey = key.substring(0, 8);

			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);

			return cipher.doFinal(data);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String decodeString(String key, String txt) {
		try {
			String ivKey = key.substring(0, 8);

			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(ivKey.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);

			byte[] btxts = new byte[txt.length() / 2];
			for (int i = 0, count = txt.length(); i < count; i += 2) {
				btxts[i / 2] = (byte) Integer.parseInt(txt.substring(i, i + 2),
						16);
			}
			return (new String(cipher.doFinal(btxts)));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}