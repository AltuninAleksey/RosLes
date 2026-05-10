package com.rosles.forestcrops.dto.sample.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MolodForestCropsItem {
    private Integer id;
    private Integer to0_5;
    private Integer from0_6To1_5;
    private Integer from1_5;
    private Long idBreed;
}
