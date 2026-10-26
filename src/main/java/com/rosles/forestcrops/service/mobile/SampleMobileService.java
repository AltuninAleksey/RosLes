package com.rosles.forestcrops.service.mobile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.mobile.sample.request.SaveSampleItemMobile;
import com.rosles.forestcrops.dto.mobile.sample.response.SampleListMobile;
import com.rosles.forestcrops.dto.web.response.CheckCZL;
import com.rosles.forestcrops.repository.mobile.SampleMobileRepository;
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
public class SampleMobileService {

    @Autowired
    private final SampleMobileRepository sampleMobileRepository;

    @Autowired
    private final HttpQueryService httpQueryService;

    @Value("${service-url.molodnial}")
    private String serviceUrlMolodnial;

    public SampleMobileService(SampleMobileRepository sampleMobileRepository, HttpQueryService httpQueryService) {
        this.sampleMobileRepository = sampleMobileRepository;
        this.httpQueryService = httpQueryService;
    }

    public SampleListMobile getSampleList(String token, Integer idProfile) throws ServiceException {

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
            log.error("Error: ListRegionService.getListRegion.", e);
        }

        return sampleMobileRepository.getSampleList(idProfile);

    }

    public void updateSample(List<SaveSampleItemMobile> saveSampleListMobiles, Integer IdProfile)  {

        for(SaveSampleItemMobile saveSampleItemMobiles  : saveSampleListMobiles) {
            sampleMobileRepository.updateSample(saveSampleItemMobiles);
        }

    }

}
