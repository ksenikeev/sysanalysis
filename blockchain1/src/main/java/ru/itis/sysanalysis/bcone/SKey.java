package ru.itis.sysanalysis.bcone;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.security.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SKey {

    public static void main(String[] args) {

        System.out.println(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SX").format(new Date()));

//        Security.addProvider(new BouncyCastleProvider());
//
//        KeyPairGenerator rsa = null;
//        try (Writer publicKeyWriter = new FileWriter(new File("publik.key"));
//             Writer privateKeyWriter = new FileWriter(new File("private.key"))) {
//            //rsa = KeyPairGenerator.getInstance("RSA", new BouncyCastleProvider());
//            rsa = KeyPairGenerator.getInstance(Utils.KEY_ALGORITHM, "BC");
//            rsa.initialize(1024,new SecureRandom());
//            KeyPair keyPair = rsa.generateKeyPair();
//
//            PrivateKey privateKey = keyPair.getPrivate();
//            PublicKey publicKey = keyPair.getPublic();
//
//            privateKeyWriter.write(new String(Hex.encode(privateKey.getEncoded())));
//            publicKeyWriter.write(new String(Hex.encode(publicKey.getEncoded())));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
