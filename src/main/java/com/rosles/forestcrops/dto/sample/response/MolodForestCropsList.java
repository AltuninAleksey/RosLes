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
public class MolodForestCropsList {
    private int count;
    private List<MolodForestCropsItem> data;

    public MolodForestCropsList() {
        this.data = new ArrayList<>();
    }
}
