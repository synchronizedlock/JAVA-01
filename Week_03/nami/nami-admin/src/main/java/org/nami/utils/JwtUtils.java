package org.nami.utils;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import org.nami.dto.Payload;
import org.springframework.util.Base64Utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * JwtUtils
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public final class JwtUtils {
    private JwtUtils() {
        throw new IllegalStateException("Error creating instance of utility class.");
    }

    /**
     * 加密算法
     */
    public static final String HEADER_ALG = "HS256";

    public static final String HEADER_TYP = "JWT";

    /**
     * 加密串
     */
    public static final String SECRET = "d5ec0a02";

    public static String generateToken(Payload payLoad) throws Exception {
        Gson gson = new Gson();
        Header header = new Header(HEADER_ALG, HEADER_TYP);
        String encodedHeader = Base64Utils.encodeToUrlSafeString(gson.toJson(header).getBytes(
                Charsets.UTF_8));
        String encodePayLoad = Base64Utils.encodeToUrlSafeString(gson.toJson(payLoad).getBytes(
                Charsets.UTF_8));

        String signature = HMACSHA256(encodedHeader + "." + encodePayLoad, SECRET);
        return encodedHeader + "." + encodePayLoad + "." + signature;
    }

    public static boolean checkSignature(String token) {
        String[] array = token.split("\\.");
        if (array.length != 3) {
            throw new IllegalArgumentException("token error");
        }

        try {
            String signature = HMACSHA256(array[0] + "." + array[1], SECRET);
            return signature.equals(array[2]);
        } catch (Exception e) {
        }
        return false;
    }

    public static Payload getPayload(String token) {
        String[] array = token.split("\\.");
        if (array.length != 3) {
            throw new IllegalArgumentException("token error");
        }

        String payLoad = new String(Base64Utils.decodeFromUrlSafeString(array[1]), Charsets.UTF_8);
        Gson gson = new Gson();
        return gson.fromJson(payLoad, Payload.class);
    }


    public static String HMACSHA256(String data, String key) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }


    private static class Header {
        private String alg;
        private String typ;

        public Header(String alg, String typ) {
            this.alg = alg;
            this.typ = typ;
        }

        public String getAlg() {
            return alg;
        }

        public void setAlg(String alg) {
            this.alg = alg;
        }

        public String getTyp() {
            return typ;
        }

        public void setTyp(String typ) {
            this.typ = typ;
        }
    }
}
