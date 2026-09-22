package com.rosles.forestcrops.dto.shared.response;

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
public class ItemList {
    private int count;
    private List<Item> data;

    public ItemList() {
        this.data = new ArrayList<>();
    }
}
