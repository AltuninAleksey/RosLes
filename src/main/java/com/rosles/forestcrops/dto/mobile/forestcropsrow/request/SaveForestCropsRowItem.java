package com.rosles.forestcrops.dto.mobile.forestcropsrow.request;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SaveForestCropsRowItem {
    private Integer number;
    private Integer countLiving;
    private Integer countDead;
    private Long idBreed;
    private UUID uuid;
    private UUID uuidSample;
}
