package com.rosles.forestcrops.dto.sample.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ForestCropsItem {
    private Integer id;
    private Integer countDead;
    private Integer countLiving;
    private Long idBreed;
}
