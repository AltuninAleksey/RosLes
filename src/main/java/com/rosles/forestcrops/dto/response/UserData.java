package com.rosles.forestcrops.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserData {
    private Integer id;
    private String id_user;

    @JsonProperty("FIO")
    private String fio;

    private Integer id_subject_rf;
    private String name_subject_rf;
    private String phoneNumber;
    private String email;
}
