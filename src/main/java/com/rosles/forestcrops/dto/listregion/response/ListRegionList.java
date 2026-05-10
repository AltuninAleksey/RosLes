package com.rosles.forestcrops.dto.listregion.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Setter
@Getter
public class ListRegionList {
    private int count;
    private List<ListRegionItem> data;

    public ListRegionList() {
        this.data = new ArrayList<>();
    }
}
