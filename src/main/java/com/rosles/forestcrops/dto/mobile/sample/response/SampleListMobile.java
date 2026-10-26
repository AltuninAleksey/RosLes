package com.rosles.forestcrops.dto.mobile.sample.response;

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
public class SampleListMobile {
    private int count;
    private List<SampleItemMobile> data;

    public SampleListMobile() {
        this.count = 0;
        this.data = new ArrayList<>();
    }
}
