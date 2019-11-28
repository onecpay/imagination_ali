package com.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * 支持SHA-1/MD5消息摘要的工具类.
 *
 * <p>返回ByteSource，可进一步被编码为Hex, Base64或UrlSafeBase64
 *
 * @author admin
 */
public class Digests {

  private static final String SHA1 = "SHA-1";
  private static final String MD5 = "MD5";

  private static SecureRandom random = new SecureRandom();

  /** 对输入字符串进行sha1散列. */
  public static byte[] sha1(byte[] input) {
    return digest(input, SHA1, null, 1);
  }

  public static byte[] sha1(byte[] input, byte[] salt) {
    return digest(input, SHA1, salt, 1);
  }

  public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
    return digest(input, SHA1, salt, iterations);
  }

  /** 对字符串进行散列, 支持md5与sha1算法. */
  private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
    try {
      MessageDigest digest = MessageDigest.getInstance(algorithm);

      if (salt != null) {
        digest.update(salt);
      }

      byte[] result = digest.digest(input);

      for (int i = 1; i < iterations; i++) {
        digest.reset();
        result = digest.digest(result);
      }
      return result;
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }

  /** 对文件进行md5散列. */
  public static byte[] md5(InputStream input) throws IOException {
    return digest(input, MD5);
  }

  /**
   * 字符串MD5操作
   *
   * @param s
   * @return
   */
  public static String md5(String s) {
    if (s == null) {
      return null;
    }
    return DigestUtils.md5Hex(s);
  }

  /** 对文件进行sha1散列. */
  public static byte[] sha1(InputStream input) throws IOException {
    return digest(input, SHA1);
  }

  private static byte[] digest(InputStream input, String algorithm) throws IOException {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
      int bufferLength = 8 * 1024;
      byte[] buffer = new byte[bufferLength];
      int read = input.read(buffer, 0, bufferLength);

      while (read > -1) {
        messageDigest.update(buffer, 0, read);
        read = input.read(buffer, 0, bufferLength);
      }

      return messageDigest.digest();
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }
}
