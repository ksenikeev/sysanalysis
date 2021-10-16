package ru.itis.sa.tspserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import ru.itis.sa.tspserver.controller.TimeStampResp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

public class TestClient {

    /* хеш для подписи */
    public static final String digest = "1F4A121121123123189002BC";
    /* 16-ричное представление публичного ключа сервиса */
    public static String publicKey = null;
    /* алгоритм ключа сервиса */
    public static final String KEY_ALGORITHM = "RSA";
    /* алгоритм подписи, формируемой сервисом */
    public static final String SIGN_ALGORITHM = "SHA256withRSAandMGF1";


    public static void main(String[] args) {
        readPublicKey();
        readAndVerifyTimeStamp();
    }

    /*
        Запрос публичного ключа с сервиса
     */
    public static void readPublicKey() {
        try {
            URL url = new URL("http://188.93.211.195/public");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            int rcode = con.getResponseCode();

            if (rcode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                publicKey = reader.readLine();

                System.out.println(publicKey);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        Запрос подписи хеша с меткой времени
     */
    public static void readAndVerifyTimeStamp() {
        try {
            URL url = new URL("http://188.93.211.195/ts?digest=" + digest);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            int rcode = con.getResponseCode();

            if (rcode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String response = reader.readLine();

                ObjectMapper mapper = new ObjectMapper();
                TimeStampResp timeStampResp = mapper.readValue(response, TimeStampResp.class);

                String timeStamp = timeStampResp.getTimeStampToken().getTs();
                String signHexStr = timeStampResp.getTimeStampToken().getSignature();

                /* формируем объединенный массив данных, которые были подписаны (метка времени + хеш) */
                byte[] data = new byte[timeStamp.getBytes().length + Hex.decode(digest).length];
                System.arraycopy(timeStamp.getBytes(), 0, data, 0, timeStamp.getBytes().length);
                System.arraycopy(Hex.decode(digest), 0, data, timeStamp.getBytes().length, Hex.decode(digest).length);

                /* Верификация подписи signHexStr, наложенной сервисом на данные (метка времени + хеш)  */
                System.out.println(verify(publicKey, data, signHexStr));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean verify(String publicKeyHexStr, byte[] data, String signHexStr) {
        Security.addProvider(new BouncyCastleProvider());

        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHM, "BC");

            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Hex.decode(publicKeyHexStr));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
            signature.initVerify(pubKey);

            signature.update(data);

            return signature.verify(Hex.decode(signHexStr));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
