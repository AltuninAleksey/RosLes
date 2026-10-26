package com.rosles.forestcrops.dto.web.sample.request;

import com.rosles.forestcrops.dto.web.sample.response.MolodForestCropsItem;
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
