package ru.itis.sa.arbiter.gametheory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyHandler {

    public static String[] currency = {"AUD","CAD","EUR","USD","SGD","JPY","DKK","CHF","GBP","NOK","NZD","HKD","CNY","HUF"}; //,"BYR"
    // пропорции валют в стратегиях
    double[][] strategy1 = new double[][]{{0.1,0.15,0.15,0.6}, {0.3,0.3,0.3,0.1},{0.6,0.1,0.1,0.2},{0.35,0.35,0.15,0.15}};
    double[][] strategy2 = new double[][]{{0.2,0.15,0.25,0.4}, {0.3,0.25,0.15,0.3},{0.5,0.15,0.25,0.1},{0.35,0.25,0.2,0.2}};
    static double budget  = 1000000;
    public static Map<String, Double[][]> currecnyData = new HashMap<>();

    static {
        readData();
    }

    public static void readData() {
        for (String cur : currency) {
            try {
                List<String> pair = Files.readAllLines(Paths.get("itog_pair/" + cur + "RUB.csv"));
                Double[][] m = new Double[5][2];
                for (int i = 1; i < 6; i++) {
                    String[] sc = pair.get(i).split(";");
                    m[i-1][0] = Double.parseDouble(sc[2]);
                    m[i-1][1] = Double.parseDouble(sc[3]);
                }
                currecnyData.put(cur, m);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<UIDataModel> getUIDataModelList(List<CurrencyBlockModel> chain, double[][] strategy) {
        List<UIDataModel> res = new ArrayList<>();

        for (CurrencyBlockModel block : chain) {
            UIDataModel dm = new UIDataModel(block);
            res.add(dm);
            if (isEmpty(block.getData().getCurrency1()) ||
                    isEmpty(block.getData().getCurrency2()) ||
                    isEmpty(block.getData().getCurrency3()) ||
                    isEmpty(block.getData().getCurrency4()) ||
                    isEmpty(block.getData().getStrategy())) continue;

            String currency1 = block.getData().getCurrency1().trim().toUpperCase();
            String currency2 = block.getData().getCurrency2().trim().toUpperCase();
            String currency3 = block.getData().getCurrency3().trim().toUpperCase();
            String currency4 = block.getData().getCurrency4().trim().toUpperCase();

            if (!isValidCurrency(currency1) || !isValidCurrency(currency2) || !isValidCurrency(currency3) ||
                    !isValidCurrency(currency4)) continue;

            int selectedStrategy = -1;
            try {
                selectedStrategy = Integer.parseInt(block.getData().getStrategy().trim().replace(" ","").substring(1,2));
            } catch (Exception e) {}

            if (selectedStrategy == -1) continue;
            if (selectedStrategy == 0) selectedStrategy = 1;

            Double[][]m1 = currecnyData.get(currency1);
            Double[][]m2 = currecnyData.get(currency2);
            Double[][]m3 = currecnyData.get(currency3);
            Double[][]m4 = currecnyData.get(currency4);

            double profit[] = new double[4];

            double[][] wc = new double[4][5];
            int riskStrategy = -1;
            double riskD = 0;

            for (int k = 0; k < 4; k++) { // цикл по стратегиям
                profit[k] = budget * (
                        strategy[k][0]*((1/m1[0][0])*m1[4][1] - 1) +
                                strategy[k][1]*((1/m2[0][0])*m2[4][1] - 1) +
                                strategy[k][2]*((1/m3[0][0])*m3[4][1] - 1) +
                                strategy[k][3]*((1/m4[0][0])*m4[4][1] - 1)
                );
                // для каждой стратегии сформируем массив взвешенных котировок по дням
                double x_ = 0;
                double d = 0;
                for (int j = 0; j < 5; j++) {
                    wc[k][j] = strategy[k][0]*m1[j][1] + strategy[k][1]*m1[j][1] +
                            strategy[k][2]*m1[j][1] + strategy[k][3]*m1[j][1];
                    x_ += wc[k][j];
                }
                x_ /=5;
                for (int j = 0; j < 5; j++) {
                    d += (wc[k][j] - x_) * (wc[k][j] - x_);
                }
                d /=5;
                if (d > riskD) { riskD = d; riskStrategy = k+1;}
            }


            dm.setStrategy1(String.format("<span class='%s %s'>%.2f</span>",riskStrategy==1 ? "strisk":"stnorm",selectedStrategy==1 ? "stsel":"",profit[0]));
            dm.setStrategy2(String.format("<span class='%s %s'>%.2f</span>",riskStrategy==2 ? "strisk":"stnorm",selectedStrategy==2 ? "stsel":"",profit[1]));
            dm.setStrategy3(String.format("<span class='%s %s'>%.2f</span>",riskStrategy==3 ? "strisk":"stnorm",selectedStrategy==3 ? "stsel":"",profit[2]));
            dm.setStrategy4(String.format("<span class='%s %s'>%.2f</span>",riskStrategy==4 ? "strisk":"stnorm",selectedStrategy==4 ? "stsel":"",profit[3]));
            if (selectedStrategy > 0 && selectedStrategy < 5) {
                dm.setItog(String.format("<span class='%s'>%.2f</span>", riskStrategy == selectedStrategy ? "strisk" : "stnorm", profit[selectedStrategy - 1]));
                dm.setItogd(profit[selectedStrategy - 1]);
            }
        }

        return res;
    }

    public static void main(String[] a) {
        for (String cur : currency) {
            System.out.println(cur);
            Double[][] m = currecnyData.get(cur);
            double d = 0;
            double x_ = 0;
            for (int i = 0; i < 5; i++) {
                x_ += m[i][1];
                System.out.println(m[i][0] + "; " + m[i][1]);
            }
            x_ /= 5;
            for (int i = 0; i < 5; i++) {
                d += (m[i][1] - x_) * (m[i][1] - x_);
            }
            d /= 5;
            System.out.println(d);

        }
        System.out.println("s2 " + "s2".substring(1,2));

        CurrencyBlockChain.readBlockChain();

        CurrencyHandler handler = new CurrencyHandler();
        List<UIDataModel> lst = handler.getUIDataModelList(CurrencyBlockChain.chain, handler.strategy1);
        lst.forEach(dm -> {
            System.out.println(dm);
        });
    }

    private boolean isEmpty(String s) {
        return s==null || s.trim().length() == 0;
    }

    private boolean isValidCurrency(String s) {
        for (String c : currency) {
            if (c.equals(s)) return true;
        }
        return false;
    }
}

