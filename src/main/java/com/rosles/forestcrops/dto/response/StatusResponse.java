package com.rosles.forestcrops.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class StatusResponse {
    private Integer code;
    private String message;
}
