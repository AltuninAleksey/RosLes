package com.rosles.forestcrops.dto.mobile.listregion.response;

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
public class ListRegionListMobile {
    private int count;
    private List<ListRegionItemMobile> data;

    public ListRegionListMobile() {
        this.data = new ArrayList<>();
    }
}
