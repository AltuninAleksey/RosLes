package com.rosles.forestcrops.dto.mobile.listregion.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@Setter
@Getter
public class SaveListRegionListMobileRequest {
    Integer IdProfile;
    List<SaveListRegionMobileRequest> saveListRegionRequest;
}
