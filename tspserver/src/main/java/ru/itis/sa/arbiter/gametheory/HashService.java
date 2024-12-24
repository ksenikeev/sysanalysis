package ru.itis.sa.arbiter.gametheory;

import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
import java.security.*;

@Component
public class HashService {

    public static final String DIGEST_ALGORITHM = "SHA-256";

    public byte[] getHash(CurrencyBlockModel block) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest digest = MessageDigest.getInstance(DIGEST_ALGORITHM);

        byte[] result = digest.digest(
                concat(concat(block.getPrevhash() != null? block.getPrevhash().getBytes() : null,
                        block.getData().toString().getBytes("UTF-8")),
                        block.getTs().getBytes()));
        return result;
    }

    public byte[] getHash(StockBlockModel block) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest digest = MessageDigest.getInstance(DIGEST_ALGORITHM);

        byte[] result = digest.digest(
                concat(concat(block.getPrevhash() != null? block.getPrevhash().getBytes() : null,
                                block.getData().toString().getBytes("UTF-8")),
                        block.getTs().getBytes()));
        return result;
    }

    public static byte[] concat(byte[] a, byte[] b) {
        if (a == null) return b;
        if (b == null) return a;
        int len_a = a.length;
        int len_b = b.length;
        byte[] C = new byte[len_a + len_b];
        System.arraycopy(a, 0, C, 0, len_a);
        System.arraycopy(b, 0, C, len_a, len_b);
        return C;
    }
}
