package am.basic.jdbcStart.util.encoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Encoder {

    private static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    public static String encode(String toEncode) {
        return new String(md.digest(toEncode.getBytes(StandardCharsets.UTF_8)));
    }


    public static boolean matches(String clean, String encoded) {
        return encode(clean).equals(encoded);
    }
}
