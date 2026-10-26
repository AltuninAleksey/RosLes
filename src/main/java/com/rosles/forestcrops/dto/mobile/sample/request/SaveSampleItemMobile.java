package com.rosles.forestcrops.dto.mobile.sample.request;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SaveSampleItemMobile {
    private String number;
    private Double length;
    private Double width;
    private UUID uuid;
    private UUID uuidListRegion;
}
