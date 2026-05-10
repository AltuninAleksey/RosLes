package com.rosles.forestcrops.dto.sample.request;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SaveSampleRequest {
    private Integer id;
    private Integer idListRegion;
    private Double length;
    private Double width;
}
