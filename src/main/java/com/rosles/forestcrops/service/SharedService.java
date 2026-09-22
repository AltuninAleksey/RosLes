package com.rosles.forestcrops.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.shared.response.BreedItemList;
import com.rosles.forestcrops.dto.shared.response.ItemList;
import com.rosles.forestcrops.repository.SharedRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SharedService {

    @Autowired
    private final SharedRepository sharedRepository;

    @Autowired
    private final HttpQueryService httpQueryService;

    @Value("${service-url.molodnial}")
    private String serviceUrlMolodnial;

    public SharedService(SharedRepository sharedRepository, HttpQueryService httpQueryService) {
        this.sharedRepository = sharedRepository;
        this.httpQueryService = httpQueryService;
    }

    public ItemList getAllDacha(String token) throws ServiceException{


        String url = serviceUrlMolodnial + "/aboutuser";

        ResponseEntity<String> response = null;

        try {
            response = httpQueryService.getQueryGetResponseEntity(url, null, token);
        } catch (JsonProcessingException e) {
            log.error("Error: SharedService.authentication.", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ItemList itemList = sharedRepository.getAllDacha();

        return itemList;
    }

    public ItemList getDacha(Integer idDistrictForestly, String token) throws ServiceException{


        String url = serviceUrlMolodnial + "/aboutuser";

        ResponseEntity<String> response = null;

        try {
            response = httpQueryService.getQueryGetResponseEntity(url, null, token);
        } catch (JsonProcessingException e) {
            log.error("Error: SharedService.authentication.", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ItemList itemList = sharedRepository.getDacha(idDistrictForestly);

        return itemList;
    }

    public BreedItemList getBreed(String token) throws ServiceException{


        String url = serviceUrlMolodnial + "/aboutuser";

        ResponseEntity<String> response = null;

        try {
            response = httpQueryService.getQueryGetResponseEntity(url, null, token);
        } catch (JsonProcessingException e) {
            log.error("Error: SharedService.authentication.", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        BreedItemList itemList = sharedRepository.getBreed();

        return itemList;
    }

}
