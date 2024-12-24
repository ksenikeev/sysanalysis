package ru.itis.sa.arbiter.gametheory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockHandler {

    public static String[] stock = {"AUD", "CAD", "EUR", "USD", "SGD", "JPY", "DKK", "CHF", "GBP", "NOK", "CNY",
                                                      "HUF","TRY", "INR", "IDR"};

    static {
        readData();
    }

    public static void readData() {
        System.out.println("read stock data");
        StockBlockChain.readBlockChain();
    }

    public static void main(String[] a) {

        StockHandler handler = new StockHandler();
    }

    private boolean isEmpty(String s) {
        return s==null || s.trim().length() == 0;
    }
}

