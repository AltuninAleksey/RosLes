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
public class ForestCropsList {
    private int count;
    private List<ForestCropsItem> data;

    public ForestCropsList() {
        this.data = new ArrayList<>();
    }
}
