package com.rosles.forestcrops.dto.sample.response;

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
public class PlantsForestCropsList {
    private int count;
    private List<PlantsForestCropsItem> data;

    public PlantsForestCropsList() {
        this.data = new ArrayList<>();
    }
}
