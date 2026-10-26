package com.rosles.forestcrops.controller.mobile;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.mobile.listregion.request.SaveListRegionListMobileRequest;
import com.rosles.forestcrops.dto.mobile.listregion.response.ListRegionListMobile;
import com.rosles.forestcrops.service.mobile.ListRegionMobileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/mobile/listregion")
@RequiredArgsConstructor
@Slf4j
public class ListRegionMobileController {

    @Autowired
    private final ListRegionMobileService listRegionMobileService;

    @GetMapping("/list")
    public ResponseEntity<ListRegionListMobile> list(
            @RequestParam("idProfile") Integer idProfile,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws ServiceException {
        try {

            String token = authorizationHeader.substring(7);

            ListRegionListMobile listRegionList = listRegionMobileService.getListRegion(token, idProfile);

            return new ResponseEntity<>(listRegionList, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error: ListRegionRepository.createListRegion", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/save")
    public ResponseEntity<String> updateListRegion(
            @RequestBody SaveListRegionListMobileRequest saveListRegionListRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws ServiceException {

        try {

            listRegionMobileService.updateListRegion(saveListRegionListRequest.getSaveListRegionRequest(),
                    saveListRegionListRequest.getIdProfile());

            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error: ListRegionRepository.createListRegion", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
