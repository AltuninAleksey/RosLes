package com.rosles.forestcrops.dto.web.sample.request;

import com.rosles.forestcrops.dto.web.sample.response.PlantsForestCropsItem;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SavePlantsForestCropsItem {
    private Integer idSample;
    private List<PlantsForestCropsItem> values;
}
