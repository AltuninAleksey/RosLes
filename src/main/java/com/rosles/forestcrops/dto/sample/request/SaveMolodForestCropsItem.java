package com.rosles.forestcrops.dto.sample.request;

import com.rosles.forestcrops.dto.sample.response.MolodForestCropsItem;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SaveMolodForestCropsItem {

    private Integer idSample;
    private List<MolodForestCropsItem> values;
}
