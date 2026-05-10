package com.rosles.forestcrops.dto.sample.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PlantsForestCropsItem {
    private Integer id;
    private Double diameter;
    private Double height;
    private Long idBreed;
}
