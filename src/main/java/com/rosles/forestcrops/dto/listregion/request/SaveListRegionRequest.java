package com.rosles.forestcrops.dto.listregion.request;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SaveListRegionRequest {
    private Long id;
    private LocalDate date;
    private String dacha;
    private String nameQuarter;
    private Double sampleRegion;
    private String soilLot;
    private Long idDistrictForestly;
}
