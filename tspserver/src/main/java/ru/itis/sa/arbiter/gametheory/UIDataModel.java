package ru.itis.sa.arbiter.gametheory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UIDataModel {
    private String ts="";
    private String name="";
    private String currency1="";
    private String currency2="";
    private String currency3="";
    private String currency4="";
    private String strategy="";
    private String strategy1="";
    private String strategy2="";
    private String strategy3="";
    private String strategy4="";
    private String itog="";
    private String premium="";
    private double itogd=-1000000;

    public UIDataModel(CurrencyBlockModel block) {
        ts = block.getTs();
        if (block.getData() != null) {
            CurrencyDataModel dm = block.getData();
            name = dm.getName();
            currency1 = dm.getCurrency1();
            currency2 = dm.getCurrency2();
            currency3 = dm.getCurrency3();
            currency4 = dm.getCurrency4();
            strategy = dm.getStrategy();
        }
    }
}
