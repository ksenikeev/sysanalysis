package ru.itis.sa.arbiter.gametheory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDataModel {
    private String name="";
    private String currency1="";
    private String currency2="";
    private String currency3="";
    private String currency4="";
    private String strategy="";
    private String x11="0";
    private String x12="0";
    private String x13="0";
    private String x14="0";
    private String x21="0";
    private String x22="0";
    private String x23="0";
    private String x24="0";
    private String x31="0";
    private String x32="0";
    private String x33="0";
    private String x34="0";
    private String x41="0";
    private String x42="0";
    private String x43="0";
    private String x44="0";
    private String p11="0";
    private String p12="0";
    private String p13="0";
    private String p14="0";
    private String p21="0";
    private String p22="0";
    private String p23="0";
    private String p24="0";
    private String p31="0";
    private String p32="0";
    private String p33="0";
    private String p34="0";
    private String p41="0";
    private String p42="0";
    private String p43="0";
    private String p44="0";
    private String publickey;
    private String result;

    public CurrencyDataModel(String name, String currency1, String currency2, String currency3, String currency4, String strategy) {
        this.name = name;
        this.currency1 = currency1;
        this.currency2 = currency2;
        this.currency3 = currency3;
        this.currency4 = currency4;
        this.strategy = strategy;
    }

    // return as normalized JSON object
    public String toString() {
        return new StringBuilder().append("{")
                .append("\"name\":\"").append(name).append("\",")
                .append("\"currency1\":\"").append(currency1).append("\",")
                .append("\"currency2\":\"").append(currency2).append("\",")
                .append("\"currency3\":\"").append(currency3).append("\",")
                .append("\"currency4\":\"").append(currency4).append("\",")
                .append("\"x11\":\"").append(x11).append("\",")
                .append("\"x12\":\"").append(x12).append("\",")
                .append("\"x13\":\"").append(x13).append("\",")
                .append("\"x14\":\"").append(x14).append("\",")
                .append("\"x21\":\"").append(x21).append("\",")
                .append("\"x22\":\"").append(x22).append("\",")
                .append("\"x23\":\"").append(x23).append("\",")
                .append("\"x24\":\"").append(x24).append("\",")
                .append("\"x31\":\"").append(x31).append("\",")
                .append("\"x32\":\"").append(x32).append("\",")
                .append("\"x33\":\"").append(x33).append("\",")
                .append("\"x34\":\"").append(x34).append("\",")
                .append("\"x41\":\"").append(x41).append("\",")
                .append("\"x42\":\"").append(x42).append("\",")
                .append("\"x43\":\"").append(x43).append("\",")
                .append("\"x44\":\"").append(x44).append("\",")
                .append("\"p11\":\"").append(p11).append("\",")
                .append("\"p12\":\"").append(p12).append("\",")
                .append("\"p13\":\"").append(p13).append("\",")
                .append("\"p14\":\"").append(p14).append("\",")
                .append("\"p21\":\"").append(p21).append("\",")
                .append("\"p22\":\"").append(p22).append("\",")
                .append("\"p23\":\"").append(p23).append("\",")
                .append("\"p24\":\"").append(p24).append("\",")
                .append("\"p31\":\"").append(p31).append("\",")
                .append("\"p32\":\"").append(p32).append("\",")
                .append("\"p33\":\"").append(p33).append("\",")
                .append("\"p34\":\"").append(p34).append("\",")
                .append("\"p41\":\"").append(p41).append("\",")
                .append("\"p42\":\"").append(p42).append("\",")
                .append("\"p43\":\"").append(p43).append("\",")
                .append("\"p44\":\"").append(p44).append("\",")
                .append("\"publickey\":\"").append(publickey).append("\"}")
                .append("\"result\":\"").append(result).append("\"}")
                .toString();
    }
}
