package com.rosles.forestcrops.dto.sample.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Setter
@Getter
public class SampleListResponse {
    private int count;
    private List<SampleItemResponse> data;

    public SampleListResponse() {
        this.data = new ArrayList<>();
    }
}
