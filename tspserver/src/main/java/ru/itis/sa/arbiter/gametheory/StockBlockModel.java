package ru.itis.sa.arbiter.gametheory;

import lombok.Data;

@Data
public class StockBlockModel {
    private String prevhash;
    private StockDataModel data;
    private String signature;
    private String info;
    private String ts;
    private String arbitersignature;

    // return as normalized JSON object
    public String toString() {
        return new StringBuilder().append("{")
                .append("\"prevhash\":\"").append(prevhash).append("\",")
                .append("\"data\":").append(data.toString()).append(",")
                .append("\"signature\":\"").append(signature).append("\",")
                .append("\"info\":\"").append(info).append("\",")
                .append("\"ts\":\"").append(ts).append("\",")
                .append("\"arbitersignature\":\"").append(arbitersignature).append("\",")
                .append("\"info\":\"").append(info).append("\"}")
                .toString();
    }
}
