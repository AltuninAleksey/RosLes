package com.rosles.forestcrops.dto.mobile.sample.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
@Setter
@Getter
public class SaveSampleItemMobileRequest {
    Integer IdProfile;
    List<SaveSampleItemMobile> saveSampleItemMobile;
}
