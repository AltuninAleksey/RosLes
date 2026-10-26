package com.rosles.forestcrops.dto.web.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BreedItem {
    private Long id;
    private String name;
    private String shortName;
}
