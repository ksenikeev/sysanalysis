package ru.itis.sa.arbiter.gametheory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class TestSender {
    public static void main(String[] args) throws IOException {
        // {"name":"Еникеев Камиль Шамилевич,-","currency1":"UZS","currency2":"SGD","currency3":"NOK","currency4":"HUF","strategy":"s3"}
        String value = URLEncoder.encode("{\"name\":\"Еникеев Камиль Шамилевич,-\",\"currency1\":\"USD\",\"currency2\":\"SGD\",\"currency3\":\"NOK\",\"currency4\":\"HUF\",\"strategy\":\"s3\"}", "UTF-8");
        URL url = new URL("http://localhost:8080/currency/add2?value=" + value);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int rc = connection.getResponseCode();
        System.out.println(new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n")));
    }
}
