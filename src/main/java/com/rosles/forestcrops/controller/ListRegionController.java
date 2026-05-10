package com.rosles.forestcrops.controller;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.listregion.request.SaveListRegionRequest;
import com.rosles.forestcrops.dto.listregion.response.ListRegionItem;
import com.rosles.forestcrops.dto.listregion.response.ListRegionList;
import com.rosles.forestcrops.dto.response.StatusResponse;
import com.rosles.forestcrops.service.ListRegionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/listregion")
@RequiredArgsConstructor
@Slf4j
public class ListRegionController {

    @Autowired
    private final ListRegionService listRegionService;

    @GetMapping("/list")
    public ResponseEntity<ListRegionList> list(
            @RequestParam(value = "idSubject", required = false) Integer idSubjectFilet,
            @RequestParam(value = "idDistrictForestly", required = false) Integer idDistrictForestlyFilet,
            @RequestParam(value = "idForestly", required = false) Integer idForestlyFilet,
            @RequestParam(value = "soilLot", required = false) String soilLotFilter,
            @RequestParam(value = "nameQuarter", required = false) String nameQuarterFilter,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer size,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        String token = authorizationHeader.substring(7);

        if(offset == null) {
            offset = 0;
        }

        if(size == null) {
            size = 10;
        }

        ListRegionList listRegionList = listRegionService.getListRegion(
                idSubjectFilet, idDistrictForestlyFilet,
                idForestlyFilet, soilLotFilter, nameQuarterFilter,
                offset, size, token);

        return new ResponseEntity<>(listRegionList, HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<ListRegionItem> getInfo(
            @RequestParam(value = "id") Integer id
    ) throws ServiceException {

        try {
            ListRegionItem listRegionItem = listRegionService.getInfo(id);

            return new ResponseEntity<>(listRegionItem, HttpStatus.OK);
        } catch (ServiceException e) {
            throw e;
        }
            catch (Exception e) {
            log.error("Error: ListRegionRepository.getInfo", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ListRegionItem> createListRegion(
            @RequestBody SaveListRegionRequest saveListRegionRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
            ) throws ServiceException {

        try {

            String token = authorizationHeader.substring(7);

            ListRegionItem listRegionItem = listRegionService.createListRegion(saveListRegionRequest, token);

            return new ResponseEntity<>(listRegionItem, HttpStatus.OK);
        } catch (ServiceException e) {
            throw e;
        }
            catch (Exception e) {
            log.error("Error: ListRegionRepository.createListRegion", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ListRegionItem> updateListRegion(
            @RequestBody SaveListRegionRequest saveListRegionRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
            ) throws ServiceException {

        try {

            ListRegionItem listRegionItem = listRegionService.updateListRegion(saveListRegionRequest);

            return new ResponseEntity<>(listRegionItem, HttpStatus.OK);
        } catch (ServiceException e) {
            throw e;
        }
            catch (Exception e) {
            log.error("Error: ListRegionRepository.createListRegion", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<StatusResponse> deleteListRegion(
            @RequestParam(value = "id") Integer id
    ) throws ServiceException {

        try {
            return new ResponseEntity<>(listRegionService.deleteListRegion(id), HttpStatus.OK);
        } catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            log.error("Error: ListRegionRepository.deleteListRegion", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
