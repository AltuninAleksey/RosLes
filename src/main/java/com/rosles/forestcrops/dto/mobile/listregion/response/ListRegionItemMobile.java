package com.rosles.forestcrops.dto.mobile.listregion.response;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ListRegionItemMobile {
    private Long id;
    private LocalDate date;
    private String number;
    private String dacha;
    private Long idDacha;
    private String nameQuarter;
    private Double sampleRegion;
    private String soilLot;
    private Long idDistrictForestly;
    private Long idForestly;
    private Long idSubject;
    private UUID uuid;
}
