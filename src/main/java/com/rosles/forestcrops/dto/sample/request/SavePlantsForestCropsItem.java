package com.rosles.forestcrops.dto.sample.request;

import com.rosles.forestcrops.dto.sample.response.PlantsForestCropsItem;
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
