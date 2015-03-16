package cn.sina.youxi.util;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import android.util.Base64;

/**
 * 类说明：           RSA加密工具类
 * 
 * @date 	2013-6-19
 * @version 1.0
 */
public class RSAUtil {

	private static byte[] RSAPubKeyEncrypt(byte[] input, BigInteger modulus,
			BigInteger exponent) {
		try {
			RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus,
					exponent);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			RSAPublicKey pubKey = (RSAPublicKey) factory
					.generatePublic(pubKeySpec);

			/**
			 * J2SE 里这样写
			 */
			// Cipher cipher = Cipher.getInstance("rsa");

			/**
			 * Android 里这样写
			 */
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] outbuf = cipher.doFinal(input);
			return outbuf;
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对字符串进行RSA加密
	 * @param input
	 * @return
	 */
	private static byte[] getRSA(String input) {
		byte[] bytes = null;
		try {
			BigInteger modulus = new BigInteger(
					"AF471C934675CDDA744F87D5B6B777173833DC34029C7989C604B2E7FD903036A2EC11A888D3634452925BEFC95AD7021D2996F0512F2A79F7FC77AC96D869404880CF46573F3311B4EE65C7A86EEE8E5C1355A234C8F87654524D55B6B254A5DBB6F0C01B80F978E606B70F605529DF7158CCF641F7E71A04AB6845411AFBF08814D5152CDF79F20713120068864FC6C8F91CD3197D28D06A8DC3FA2A34823780DCFE3A27E6E40A14B43E4A1465E0A65BD39AD2FCAD4126FF5D7CA54C5EA0CF47E4076DCCC7767055877D9A4063E526514626A96B75FFC8F0AB649C0C5B3AE5EA3AFF4A030157D4343A83A219223210C9FB67969C59143E27F3AD3EF76A164D",
					16);
			BigInteger exponent = new BigInteger("65537", 10);

			bytes = RSAPubKeyEncrypt(input.getBytes("utf-8"), modulus, exponent);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return bytes;
	}

	/**
	 * 对字符串RSA加密，并且Base64编码为新字符串
	 * @param input
	 * @return
	 */
	public static String getBase64RSA(String input) {
		byte[] binaryData = RSAUtil.getRSA(input);

		return binaryData != null ? new String(Base64.encode(binaryData,
				Base64.DEFAULT)) : "";
	}
}