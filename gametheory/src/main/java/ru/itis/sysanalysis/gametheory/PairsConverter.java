package ru.itis.sysanalysis.gametheory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PairsConverter {

    public static void main(String[] s) {
        try {
            List<String> pairAUD_RUB = new ArrayList<>();
            List<String> pairEUR_AUD = Files.readAllLines(Paths.get("pairs/EURAUD_211101_211119.csv"));
            List<String> pairEUR_RUB = Files.readAllLines(Paths.get("pairs/EURRUB_211101_211119.csv"));

            pairAUD_RUB.add("<TICKER>;<PER>;<DATE>;<TIME>;<OPEN>;<HIGH>;<LOW>;<CLOSE>;<VOL>");

            for (int i = 1; i < 18; i++) {
                String[] p_ea = pairEUR_AUD.get(i).split(";");
                String[] p_er = pairEUR_RUB.get(i).split(";");

                double open_ar = Double.parseDouble(p_er[4]) / Double.parseDouble(p_ea[4]);
                double close_ar = Double.parseDouble(p_er[7]) / Double.parseDouble(p_ea[7]);
                pairAUD_RUB.add("AUDRUB;D;"+p_er[2] +";0;" + String.format("%.7f",open_ar).replaceAll(",", ".")+";"+
                        ";"+
                        ";"+
                        String.format("%.7f",close_ar).replaceAll(",", ".")+";");

                Files.write(Paths.get("AUDRUB.csv"),pairAUD_RUB);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
