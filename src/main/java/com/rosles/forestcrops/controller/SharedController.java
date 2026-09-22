package com.rosles.forestcrops.controller;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.shared.response.BreedItemList;
import com.rosles.forestcrops.dto.shared.response.ItemList;
import com.rosles.forestcrops.service.SharedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/shared")
@RequiredArgsConstructor
@Slf4j
public class SharedController {

    @Autowired
    private final SharedService sharedService;

    @GetMapping("/get-all-dacha")
    public ResponseEntity<ItemList> getAllDacha(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws ServiceException {
        try {

            String token = authorizationHeader.substring(7);

            ItemList itemList = sharedService.getAllDacha(token);

            return new ResponseEntity<>(itemList, HttpStatus.OK);

        } catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            log.error("Error: SharedController.getAllDacha", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-dacha")
    public ResponseEntity<ItemList> getDacha(
            @RequestParam(value = "idDistrictForestly", required = false) Integer idDistrictForestly,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws ServiceException {
        try {

            String token = authorizationHeader.substring(7);

            ItemList itemList = sharedService.getDacha(idDistrictForestly, token);

            return new ResponseEntity<>(itemList, HttpStatus.OK);

        } catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            log.error("Error: SharedController.getDacha", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-breed")
    public ResponseEntity<BreedItemList> getBreed(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws ServiceException {
        try {

            String token = authorizationHeader.substring(7);

            BreedItemList itemList = sharedService.getBreed(token);

            return new ResponseEntity<>(itemList, HttpStatus.OK);

        } catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            log.error("Error: SharedController.getBreed", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
