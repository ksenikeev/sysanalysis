package ru.itis.sa.arbiter.neurobc.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Inequality {

    public static void main(String[] args) {
        // 32 byte rigth side 001111111...111

        int[] rigth = {0x00,0x0F,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,
                        0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,
                0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF};

        byte[] sourceSet = "The next step is to replace the derivatives".getBytes();
        int nonce = 0;
        int compareResult = 1;
        do {

            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");

                byte[] result = digest.digest(concat(sourceSet,intToByteArray(nonce)));


                for (int x : rigth) System.out.print(byteToBinaryString(x) + " ");
                System.out.println();

                for (byte x : result) System.out.print(byteToBinaryString(x & 0xFF) + " ");
                System.out.println();


                compareResult = compare(result, rigth);
                System.out.println((nonce++) + " " + compareResult);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } while (compareResult > 0);

    }

    /*  length a = length b = 32  */
    public static int compare(byte[] a, int[] b) {
        int r = 0;
        for (int i = 0; i < 32; ++i) {
            System.out.println((a[i] & 0xFF) + " - " + b[i]);
            r = (a[i] & 0xFF) - b[i];
            if (r != 0) return r;
        }
        return r;
    }

    public static String byteToBinaryString(int x) {
        String s = "";
        for (int i = 7; i > -1; --i) {
            s += (x & (1 << i)) >> i;
        }
        return s;
    }

    public static byte[] intToByteArray(int x) {
        byte[] r = new byte[4];
        r[0] = (byte) (x >> 24);
        r[1] = (byte) (x >> 16);
        r[2] = (byte) (x >> 8);
        r[3] = (byte) (x);
        return r;
    }

    public static byte[] concat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static byte[] concat2(byte[] ... a) {
        int length = 0;
        for (int i = 0; i < a.length; ++i) {
            length += a[i].length;
        }

        byte[] c = new byte[length];
        int position = 0;
        for (int i = 0; i < a.length; ++i) {
            System.arraycopy(a[i], 0, c, position, a[i].length);
            position += a[i].length;
        }
        return c;
    }

}
