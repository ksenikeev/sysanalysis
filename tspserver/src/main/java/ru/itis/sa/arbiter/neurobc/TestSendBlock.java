package ru.itis.sa.arbiter.neurobc;

import org.bouncycastle.util.encoders.Hex;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Formatter;

public class TestSendBlock {

    public static void main(String[] args) {
        String KEY_ALGORITHM = "RSA";
        String SIGN_ALGORITHM = "SHA256withRSA";
        String publicKey16 = "30819f300d06092a864886f70d010101050003818d0030818902818100a811365d2f3642952751029edf87c8fa2aeb6e0feafcf800190a7dd2cf750c63262f6abd8ef52b251c0e10291d5e2f7e6682de1aae1d64d4f9b242050f898744ca300a44c4d8fc8af0e7a1c7fd9b606d7bde304b29bec01fbef554df6ba1b7b1ec355e1ff68bd37f3d40fb27d1aa233fe3dd6b63f7241e734739851ce8c590f70203010001";
        String privateKey16 = "30820277020100300d06092a864886f70d0101010500048202613082025d02010002818100a811365d2f3642952751029edf87c8fa2aeb6e0feafcf800190a7dd2cf750c63262f6abd8ef52b251c0e10291d5e2f7e6682de1aae1d64d4f9b242050f898744ca300a44c4d8fc8af0e7a1c7fd9b606d7bde304b29bec01fbef554df6ba1b7b1ec355e1ff68bd37f3d40fb27d1aa233fe3dd6b63f7241e734739851ce8c590f7020301000102818019a116533b81e0c1720f6e80dfc18b2a6b251fd1999a1e0f95c1fdd20a04e570b14005dd0651e8d46de4cf1e15f668f3b39a3d588038c7499b124df30b9d835d007173897de36ed60b741be71d9c0c92539bc5d8d8c81c217e35768afa4f5deb208adc074646747b64bddf401d24fc27d989073b811145e3f26453e50dff9391024100e8ba466033c207db1c886a385e0933bb592bd279f436dcdd809516e6c9ff2a3bce740668c9ec306f536694e79f47aac1854e59eed67ae9b04a4387ceff2fd8cb024100b8dfa8debdbd3b732a3b108b0669985f8c064bdedf219b98ddbaf3d43c57cd13caea5d247e3099bdf6d553e9182d76fc8f002a7ee7ac27277ee0010f5cc95f0502405c90f2bb02fb330ab8cf9d882c06f6f796a1328765568904e546e7a4064622b3450bab1cf4eba7dc2fcd067f1640782f9ee92a263053152e146bc5d1d478607f024100a5cd310e52ec9b9cb58b3352fbf5480aa8f21ca94d443edfe23c00e19f1cbf77b8cc67ac8adfdb825b1507a44d62b90bd8d435488b4e01ef920223f13b2dfe61024100cf1d242fb3c7bf46f6c222756122db17326894108af890d645cc787f38798b4c0e8500625bf66b548855768583ef53c21be5f8d46d9076552fe32f75c3461e53";
        int[] rigth = {0x00,0x0F,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,
                0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,
                0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF,0xFF};

        try {
            DataModel dm = new DataModel(
                    "0.43020346429", "0.42343777794", "0.365098073773",
                    "0.421516861353294",
                    "0.087085277804529", "0.140615372467", "0.179633405876",
                    "0.029902198076425", "0.044807001531", "0.008843585623",
                    "0.303404410068071", "0.300280874923", "0.306850540370","0.39670127653",publicKey16);

            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Hex.decode(privateKey16));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey priKey = keyFactory.generatePrivate(keySpec);

            Signature signature = Signature.getInstance(SIGN_ALGORITHM);

            signature.initSign(priKey);

            signature.update(dm.toString().getBytes("UTF-8"));

            String sign = new String(Hex.encode(signature.sign()));

            BlockModel block = new BlockModel();
            block.setPrevhash("7baac47e2b78d2afa89a60608df00ccd55f9747740aa9bba805addfaaf520bc5");
            block.setData(dm);
            block.setSignature(sign);
            block.setNonce(0);

            int compareResult = 1;
            do {

                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");

                    byte[] result = digest.digest(
                            concat(Hex.decode(block.getPrevhash()),
                                    block.getData().toString().getBytes(),
                                    Hex.decode(block.getSignature()),
                                    intToByteArray(block.getNonce())));

                    compareResult = compare(result, rigth);
                    if (compareResult < 0)
                        break;
                    else
                        block.setNonce(block.getNonce() + 1);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            } while (compareResult > 0);

            System.out.println(block.getNonce());

            HttpURLConnection connection =
//                    (HttpURLConnection) new URL("http://localhost:8095/nbc/newblock").openConnection();
                        (HttpURLConnection) new URL("http://itislabs.ru/nbc/newblock").openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-Type","application/json;charset=UTF-8");
            connection.setDoOutput(true);
            connection.getOutputStream().write(block.toString().getBytes("UTF-8"));
            int r = connection.getResponseCode();

            String resp = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

            System.out.println(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

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

    public static byte[] concat(byte[] ... a) {
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
