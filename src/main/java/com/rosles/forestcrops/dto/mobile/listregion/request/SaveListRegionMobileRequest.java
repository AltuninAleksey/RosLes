package com.rosles.forestcrops.dto.mobile.listregion.request;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SaveListRegionMobileRequest {
    private LocalDate date;
    private String dacha;
    private String nameQuarter;
    private Double sampleRegion;
    private String soilLot;
    private Long idDistrictForestly;
    private Long idDacha;
    private UUID uuid;
}
