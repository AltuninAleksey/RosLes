package com.rosles.forestcrops.dto.web.sample.request;

import com.rosles.forestcrops.dto.web.sample.response.ForestCropsItem;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SaveForestCropsItem {
    private Integer idSample;
    private List<ForestCropsItem> values;
}
