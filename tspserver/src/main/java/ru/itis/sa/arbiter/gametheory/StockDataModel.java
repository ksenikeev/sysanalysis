package ru.itis.sa.arbiter.gametheory;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockDataModel {
    private String name="";
    private String stock1="";
    private String stock2="";
    private String stock3="";
    private String stock4="";

    public StockDataModel(String name, String stock1, String stock2, String stock3, String stock4) {
        this.name = name;
        this.stock1 = stock1;
        this.stock2 = stock2;
        this.stock3 = stock3;
        this.stock4 = stock4;
    }

    // return as normalized JSON object
    public String toString() {
        return new StringBuilder().append("{")
                .append("\"name\":\"").append(name).append("\",")
                .append("\"stock1\":\"").append(stock1).append("\",")
                .append("\"stock2\":\"").append(stock2).append("\",")
                .append("\"stock3\":\"").append(stock3).append("\",")
                .append("\"stock4\":\"").append(stock4).append("\"}")
                .toString();
    }
}
