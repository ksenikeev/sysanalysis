package ru.itis.sysan.practic0.bc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class BlockModel {
    private String prevhash;
    private DataModel data;
    private String ts;
}
