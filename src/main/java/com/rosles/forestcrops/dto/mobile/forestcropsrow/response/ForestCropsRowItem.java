package com.rosles.forestcrops.dto.mobile.forestcropsrow.response;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ForestCropsRowItem {

    private Long id;
    private Integer number;
    private Integer countLiving;
    private Integer countDead;
    private Long idBreed;
    private UUID uuid;
    private UUID uuidSample;

}
