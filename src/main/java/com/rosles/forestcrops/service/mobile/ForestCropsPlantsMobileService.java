package com.rosles.forestcrops.service.mobile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.mobile.forestcropsplants.request.SaveForestCropsPlantsItem;
import com.rosles.forestcrops.dto.mobile.forestcropsplants.response.ForestCropsPlantsList;
import com.rosles.forestcrops.dto.mobile.forestcropsrow.request.SaveForestCropsRowItem;
import com.rosles.forestcrops.dto.web.response.CheckCZL;
import com.rosles.forestcrops.repository.mobile.ForestCropsPlantsMobileRepository;
import com.rosles.forestcrops.repository.mobile.ForestCropsRowMobileRepository;
import com.rosles.forestcrops.service.HttpQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ForestCropsPlantsMobileService {

    @Autowired
    private final ForestCropsPlantsMobileRepository forestCropsPlantsMobileRepository;

    @Autowired
    private final HttpQueryService httpQueryService;

    @Value("${service-url.molodnial}")
    private String serviceUrlMolodnial;

    public ForestCropsPlantsMobileService(ForestCropsPlantsMobileRepository forestCropsPlantsMobileRepository, HttpQueryService httpQueryService) {
        this.forestCropsPlantsMobileRepository = forestCropsPlantsMobileRepository;
        this.httpQueryService = httpQueryService;
    }

    public ForestCropsPlantsList getForestCropsPlantsList(String token, Integer idProfile) throws ServiceException {
        if (token == null || token.isEmpty()) {
            log.error("Error: AuthenticationService.authentication. Token is empty.");

            throw new ServiceException("Token is empty.", HttpStatus.UNAUTHORIZED);
        }

        String url = serviceUrlMolodnial + "/get_czl_subject_data";

        ResponseEntity<String> response = null;

        try {
            response = httpQueryService.getQueryGetResponseEntity(url, null, token);
        } catch (JsonProcessingException e) {
            log.error("Error: AuthenticationService.authentication.", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ObjectMapper mapper = new ObjectMapper();
        CheckCZL checkCZL = new CheckCZL();

        try {
            checkCZL = mapper.readValue(response.getBody(), CheckCZL.class);
        } catch (JsonProcessingException e) {
            log.error("Error: ForestCropsRowMobileService.getForestCropsPlantsList.", e);
        }

        return forestCropsPlantsMobileRepository.getForestCropsPlantsList(idProfile);
    }

    public void updateForestCropsPlants(List<SaveForestCropsPlantsItem> saveForestCropsPlantsItems, Integer IdProfile) {

        for(SaveForestCropsPlantsItem saveForestCropsPlantsItem  : saveForestCropsPlantsItems) {
            forestCropsPlantsMobileRepository.updateForestCropsPlants(saveForestCropsPlantsItem);
        }

    }


}
