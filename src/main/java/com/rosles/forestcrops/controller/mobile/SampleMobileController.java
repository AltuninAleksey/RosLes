package com.rosles.forestcrops.controller.mobile;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.mobile.sample.request.SaveSampleItemMobileRequest;
import com.rosles.forestcrops.dto.mobile.sample.response.SampleListMobile;
import com.rosles.forestcrops.service.mobile.SampleMobileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/mobile/sample")
@RequiredArgsConstructor
@Slf4j
public class SampleMobileController {


    @Autowired
    private final SampleMobileService sampleMobileService;

    @GetMapping("/list")
    public ResponseEntity<SampleListMobile> list(
            @RequestParam("idProfile") Integer idProfile,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws ServiceException {
        try {

            String token = authorizationHeader.substring(7);

            SampleListMobile sampleList = sampleMobileService.getSampleList(token, idProfile);

            return new ResponseEntity<>(sampleList, HttpStatus.OK);

        }  catch (Exception e) {
            log.error("Error: SampleMobileController.list", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/save")
    public ResponseEntity<String> updateSample(
            @RequestBody SaveSampleItemMobileRequest saveSampleItemMobileRequest,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) throws ServiceException {

        try {

            sampleMobileService.updateSample(saveSampleItemMobileRequest.getSaveSampleItemMobile(),
                    saveSampleItemMobileRequest.getIdProfile());

            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error: SampleMobileController.updateSample", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
