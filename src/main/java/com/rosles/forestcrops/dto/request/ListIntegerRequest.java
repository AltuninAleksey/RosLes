package com.rosles.forestcrops.dto.request;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ListIntegerRequest {
    private List<Integer> values;
}
