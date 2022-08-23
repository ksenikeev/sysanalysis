package ru.itis.sysan.practic0.bc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataModel {
    private String name = "";
    private String group = "";
    private String objects = "";
    private String morphism = "";
    private String composition = "";
}
