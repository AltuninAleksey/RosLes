package com.rosles.forestcrops.dto.listregion.response;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ListRegionItem {
    private Long id;
    private LocalDate date;
    private String number;
    private String dacha;
    private String nameQuarter;
    private Double sampleRegion;
    private String soilLot;
    private Long idDistrictForestly;
    private Long idForestly;
    private Long idSubject;
}
