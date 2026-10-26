package com.rosles.forestcrops.dto.mobile.forestcropsplants.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Setter
@Getter
public class ForestCropsPlantsList {

    private int count;
    private List<ForestCropsPlantsItem> data;

    public ForestCropsPlantsList() {
        this.count = 0;
        this.data = new ArrayList<>();
    }
}
