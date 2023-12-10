package ru.itis.sysanalysis.bcone;

import org.bouncycastle.util.encoders.Hex;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SignedBlockChain {

    private static final int BC_LENGTH = 10;
    private static List<BlockInfo> blockchain = new ArrayList<>();
    private static KeyPair keyPair;

    public static void main(String[] args) {

        try {
            keyPair = Utils.loadKeys();
            makeBlockChain();

            print();

            System.out.println("verification result: " + verification());

            damage();

            print();

            System.out.println("verification result: " + verification());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void makeBlockChain() {
        byte[] prevHash = null;

        for (int i = 0;i < BC_LENGTH; i++) {
            BlockInfo blockInfo = new BlockInfo(i);
            blockInfo.setData("Данные блока " + i);
            blockInfo.setPrevHash(prevHash);

            try {
                prevHash = Utils.getHash(blockInfo);

                blockInfo.setSign(Utils.generateRSAPSSSignature(keyPair.getPrivate(), prevHash));
            } catch (Exception e) {
                e.printStackTrace();
            }


            blockchain.add(blockInfo);
        }
    }

    private static void print() throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
        for (int i = 0;i < BC_LENGTH; i++) {
            BlockInfo bi = blockchain.get(i);
            System.out.println("===================== " + bi.getBlockNum() + " =============================");
            System.out.println("prev hash: " + (bi.getPrevHash() != null ? new String(Hex.encode(bi.getPrevHash())): ""));
            System.out.println(bi.getData());
            System.out.println("digest: " + new String(Hex.encode(Utils.getHash(bi))));
            System.out.println("signature: " + new String(Hex.encode(bi.getSign())));
            System.out.println();
        }
    }

    private static boolean verification() throws GeneralSecurityException, UnsupportedEncodingException {

        byte[] prevHash = Utils.getHash(blockchain.get(0));
        for (int i = 1;i < BC_LENGTH; i++) {
            if (!Arrays.equals(prevHash, blockchain.get(i).getPrevHash())) {
                return false;
            }

            prevHash = Utils.getHash(blockchain.get(i));

            if (!Utils.verifyRSAPSSSignature(keyPair.getPublic(), prevHash, blockchain.get(i).getSign())) {
                return false;
            }
        }

        return true;
    }

    private static void damage() {
        blockchain.get(3).setData("{\"data\":\"damaged data\"}");
    }
}
