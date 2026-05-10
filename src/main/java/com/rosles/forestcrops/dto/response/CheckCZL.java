package com.rosles.forestcrops.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Setter
@Getter
public class CheckCZL {

    private List<Subject> subject;
    private List<CZL> czl;

    public CheckCZL() {
        this.subject = new ArrayList<>();
        this.czl = new ArrayList<>();
    }

}
