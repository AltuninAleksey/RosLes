package com.rosles.forestcrops.dto.mobile.forestcropsrow.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@Setter
@Getter
public class SaveForestCropsRowItemRequest {
    Integer IdProfile;
    List<SaveForestCropsRowItem> saveForestCropsRowItem;
}
