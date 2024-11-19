package ru.itis.sysanalysis.gametheory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PairsConverter {

    public static void main(String[] s) {
        try {
            List<String> pairCURRENCY_RUB = new ArrayList<>();
            List<String> pairUSD_CURRENCY = Files.readAllLines(Paths.get("itog_pair/USDTRY_04122023_08122023.csv"));
            List<String> pairUSD_RUB = Files.readAllLines(Paths.get("itog_pair/USDRUB_04122023_08122023.csv"));

            pairCURRENCY_RUB.add("<TICKER>;<PER>;<DATE>;<TIME>;<OPEN>;<HIGH>;<LOW>;<CLOSE>;<VOL>");

            for (int i = 1; i < Math.min(pairUSD_CURRENCY.size(), pairUSD_RUB.size()); i++) {
                String[] p_uc = pairUSD_CURRENCY.get(i).split(";");
                String[] p_ur = pairUSD_RUB.get(i).split(";");

                // for USD_CUR
                double open_ar = Double.parseDouble(p_ur[4]) / Double.parseDouble(p_uc[4]);
                double close_ar = Double.parseDouble(p_ur[7]) / Double.parseDouble(p_uc[7]);
/*
                // for CUR_USD
                double open_ar = Double.parseDouble(p_ur[4]) * Double.parseDouble(p_uc[4]);
                double close_ar = Double.parseDouble(p_ur[7]) * Double.parseDouble(p_uc[7]);
*/
                pairCURRENCY_RUB.add("TRYRUB;D;"+p_ur[2] +";0;" + String.format("%.7f",open_ar).replaceAll(",", ".")+";"+
                        ";"+
                        ";"+
                        String.format("%.7f",close_ar).replaceAll(",", ".")+";");

                Files.write(Paths.get("itog_pair/TRYRUB_04122023_08122023.csv"),pairCURRENCY_RUB);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
