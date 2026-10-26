package com.rosles.forestcrops.controller.mobile;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.mobile.forestcropsplants.request.SaveForestCropsPlantsItemRequest;
import com.rosles.forestcrops.dto.mobile.forestcropsplants.response.ForestCropsPlantsList;
import com.rosles.forestcrops.dto.mobile.forestcropsrow.request.SaveForestCropsRowItemRequest;
import com.rosles.forestcrops.dto.mobile.forestcropsrow.response.ForestCropsRowList;
import com.rosles.forestcrops.service.mobile.ForestCropsPlantsMobileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/mobile/forest-crops-plants")
@RequiredArgsConstructor
@Slf4j
public class ForestCropsPlantsMobileController {

    @Autowired
    private final ForestCropsPlantsMobileService forestCropsPlantsMobileService;

    @GetMapping("/list")
    public ResponseEntity<ForestCropsPlantsList> getForestCropsPlantsList(
            @RequestParam("idProfile") Integer idProfile,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws ServiceException {
        try {

            String token = authorizationHeader.substring(7);

            ForestCropsPlantsList forestCropsPlantsList = forestCropsPlantsMobileService
                    .getForestCropsPlantsList(token, idProfile);

            return new ResponseEntity<>(forestCropsPlantsList, HttpStatus.OK);

        }  catch (Exception e) {
            log.error("Error: ForestCropsRowMobileController.list", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/save")
    public ResponseEntity<String> updateForestCropsPlants(
            @RequestBody SaveForestCropsPlantsItemRequest saveForestCropsRowItemRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws ServiceException {

        try {

            forestCropsPlantsMobileService.updateForestCropsPlants(saveForestCropsRowItemRequest.getSaveForestCropsPlantsItems(),
                    saveForestCropsRowItemRequest.getIdProfile());

            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error: ForestCropsRowMobileController.updateForestCropsRow", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
