package com.rosles.forestcrops.dto.sample.response;

import com.rosles.forestcrops.dto.listregion.response.ListRegionItem;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SampleInfoResponse {
    private SampleItemResponse sample;
    private ListRegionItem listRegion;
}
