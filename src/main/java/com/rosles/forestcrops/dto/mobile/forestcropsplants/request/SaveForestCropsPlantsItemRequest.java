package com.rosles.forestcrops.dto.mobile.forestcropsplants.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@Setter
@Getter
public class SaveForestCropsPlantsItemRequest {
    Integer IdProfile;
    List<SaveForestCropsPlantsItem> saveForestCropsPlantsItems;
}
