package com.rosles.forestcrops.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.listregion.request.SaveListRegionRequest;
import com.rosles.forestcrops.dto.listregion.response.ListRegionItem;
import com.rosles.forestcrops.dto.listregion.response.ListRegionList;
import com.rosles.forestcrops.dto.response.CheckCZL;
import com.rosles.forestcrops.dto.response.StatusResponse;
import com.rosles.forestcrops.dto.response.UserDataResponse;
import com.rosles.forestcrops.repository.ListRegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Service
@Slf4j
public class ListRegionService {

    @Autowired
    private final HttpQueryService httpQueryService;

    @Autowired
    private final ListRegionRepository listRegionRepository;

    @Value("${service-url.molodnial}")
    private String serviceUrlMolodnial;

    public ListRegionService(HttpQueryService httpQueryService, ListRegionRepository listRegionRepository) {
        this.httpQueryService = httpQueryService;
        this.listRegionRepository = listRegionRepository;
    }

    public ListRegionList getListRegion(
            Integer idSubjectFilet, Integer idDistrictForestlyFilet,
            Integer idForestlyFilet, String soilLotFilter, String nameQuarterFilter,
            Integer offset, Integer size, String token) {

        if (token == null || token.isEmpty()) {
            return new ListRegionList();
        }

        String url = serviceUrlMolodnial + "/get_czl_subject_data";

        ResponseEntity<String> response = null;

        try {
            response = httpQueryService.getQueryGetResponseEntity(url, null, token);
        } catch (JsonProcessingException e) {
            log.error("Error: AuthenticationService.authentication.", e);

            return new ListRegionList();
        }

        ObjectMapper mapper = new ObjectMapper();
        CheckCZL checkCZL = new CheckCZL();

        try {
            checkCZL = mapper.readValue(response.getBody(), CheckCZL.class);
        } catch (JsonProcessingException e) {
            log.error("Error: ListRegionService.getListRegion.", e);
        }

        if (!checkCZL.getSubject().isEmpty()) {
            ListRegionList listRegionList = listRegionRepository.getListRegion(
                    idSubjectFilet, idDistrictForestlyFilet,
                    idForestlyFilet, soilLotFilter, nameQuarterFilter,
                    checkCZL.getSubject().get(0).getId(), offset, size);

            return listRegionList;
        } else {
            return new ListRegionList();
        }
    }

    public ListRegionItem getInfo(Integer id) throws ServiceException {
        return listRegionRepository.getInfo(id);
    }

    public ListRegionItem createListRegion(SaveListRegionRequest saveListRegionRequest,
                                           String token) throws ServiceException {

        String url = serviceUrlMolodnial + "/aboutuser";

        ResponseEntity<String> response = null;

        try {
            response = httpQueryService.getQueryGetResponseEntity(url, null, token);
        } catch (JsonProcessingException e) {
            log.error("Error: AuthenticationService.authentication.", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ObjectMapper mapper = new ObjectMapper();

        UserDataResponse userData = new UserDataResponse();

        try {
            userData = mapper.readValue(response.getBody(), UserDataResponse.class);
        } catch (JsonProcessingException e) {
            log.error("Error: ListRegionService.getListRegion.", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return listRegionRepository.createListRegion(saveListRegionRequest, userData.getData().getId());
    }

    public ListRegionItem updateListRegion(SaveListRegionRequest saveListRegionRequest) throws ServiceException {
        return listRegionRepository.updateListRegion(saveListRegionRequest);
    }

    public StatusResponse deleteListRegion(Integer id) throws ServiceException {
        return listRegionRepository.deleteListRegion(id);
    }

}
