package com.rosles.forestcrops.dto.sample.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SampleItemResponse {
    private Integer id;
    private String number;
    private Integer idListRegion;
    private Double length;
    private Double width;
    private Double square;
}
