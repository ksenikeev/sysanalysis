package ru.itis.sysan.practic0.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itis.sysan.practic0.bc.BlockModel;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UIDataModel {
    private String name = "";
    private String group = "";
    private String objects = "";
    private String morphism = "";
    private String composition = "";
    private String id = "";
    private String ts = "";

    public UIDataModel(BlockModel block) {
        name = block.getData().getName();
        group = block.getData().getGroup();
        objects = block.getData().getObjects();
        morphism = block.getData().getMorphism();
        composition = block.getData().getComposition();
        id = block.getData().getId();
        ts = block.getTs();
    }
}
