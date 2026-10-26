package com.rosles.forestcrops.dto.mobile.sample.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SampleItemMobile {

    private Long id;
    private String number;
    private Double length;
    private Double width;
    private UUID uuid;
    private UUID uuidListRegion;

}
