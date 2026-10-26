package com.rosles.forestcrops.dto.web.sample.response;

import com.rosles.forestcrops.dto.web.listregion.response.ListRegionItem;
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
