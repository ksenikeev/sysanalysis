package ru.itis.sysanalysis.tsp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestClient {
    public static void main(String[] args) {
        readPublicKey();
        readTs();
    }

    public static void readPublicKey() {
        try {
            URL url = new URL("http://188.93.211.195/public");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            int rcode = con.getResponseCode();

            if (rcode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String publicKey = reader.readLine();

                System.out.println(publicKey);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readTs() {
        try {
            URL url = new URL("http://188.93.211.195/ts?digest=1F4A121121123123189002BC");

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            int rcode = con.getResponseCode();

            if (rcode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String ts = reader.readLine();


                System.out.println(ts);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
