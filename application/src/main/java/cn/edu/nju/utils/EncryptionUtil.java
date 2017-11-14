package cn.edu.nju.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {

    public static String sha256(String str) {
        String encyptStr = str;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(Charset.forName("UTF-8")));
            encyptStr = bytes2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encyptStr;
    }

    public static String md5(InputStream stream) {
        byte buffer[] = new byte[1024];

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            int len;
            while ((len = stream.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            return new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            Logger.getLogger(EncryptionUtil.class).error(e);
            return "";
        }
    }

    public static String base64Encode(String str) {
        return new String(Base64.encodeBase64(str.getBytes()));
    }

    public static String base64Decode(String str) {
        return new String(Base64.decodeBase64(str));
    }

    private static String bytes2Hex(byte[] bytes) {
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            String str = Integer.toHexString(b & 0xFF);
            if (str.length() == 1) {
                builder.append("0");
            }
            builder.append(str);
        }
        return builder.toString();
    }
}