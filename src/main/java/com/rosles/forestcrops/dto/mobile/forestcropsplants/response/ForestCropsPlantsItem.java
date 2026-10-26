package com.rosles.forestcrops.dto.mobile.forestcropsplants.response;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ForestCropsPlantsItem {

    private Long id;
    private Integer number;
    private Double diameter;
    private Double height;
    private Long idBreed;
    private UUID uuid;
    private UUID uuidSample;

}
