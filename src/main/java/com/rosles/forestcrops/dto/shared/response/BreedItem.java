package com.rosles.forestcrops.dto.shared.response;

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
