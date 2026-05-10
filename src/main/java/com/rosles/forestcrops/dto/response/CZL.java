package com.rosles.forestcrops.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CZL {

    private Integer id;
    private String name_czl;
    private Integer id_main_subject_id;
    private Integer id_subject_id;

}
