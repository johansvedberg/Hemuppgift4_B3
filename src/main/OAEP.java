package main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

public class OAEP {

	private String mgfSeed;
	private int maskLen;
	private MessageDigest digest;

	public OAEP(String mgfSeed, int maskLen) {

		digest = null;

		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}

		int hashCount = (maskLen + digest.getDigestLength() - 1) / digest.getDigestLength();

		byte[] seed = toByteArray(mgfSeed);
		System.out.println(javax.xml.bind.DatatypeConverter.printHexBinary(seed));

		byte[] T = new byte[0];

		for (int i = 0; i < hashCount; i++) {
			System.out.println(javax.xml.bind.DatatypeConverter.printHexBinary(T));
			T = concatenate(T, SHA1(seed, i));

		}

		byte[] output = new byte[maskLen];
		System.arraycopy(T, 0, output, 0, output.length);
		System.out.println(toHex(output));

	}

	private byte[] SHA1(byte[] mask, int i) {

		digest.update(mask);
		digest.update(new byte[3]);
		digest.update((byte) i);
		byte[] digestBytes = digest.digest();

		return digestBytes;

	}

	private byte[] concatenate(byte[] a, byte[] b) {
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	private String toHex(byte[] array) {
		return DatatypeConverter.printHexBinary(array);
	}

	private byte[] toByteArray(String s) {
		return DatatypeConverter.parseHexBinary(s);
	}

}