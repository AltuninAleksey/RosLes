package com.rosles.forestcrops.dto.mobile.forestcropsrow.response;

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
public class ForestCropsRowList {

    private int count;
    private List<ForestCropsRowItem> data;

    public ForestCropsRowList() {
        this.count = 0;
        this.data = new ArrayList<>();
    }
}
