package com.rosles.forestcrops.controller;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.response.StatusResponse;
import com.rosles.forestcrops.dto.sample.request.SaveSampleRequest;
import com.rosles.forestcrops.dto.sample.response.SampleItemResponse;
import com.rosles.forestcrops.dto.sample.response.SampleListResponse;
import com.rosles.forestcrops.service.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/sample")
@RequiredArgsConstructor
@Slf4j
public class SampleController {

    @Autowired
    private final SampleService sampleService;


    @GetMapping("/list")
    public ResponseEntity<SampleListResponse> list(
            @RequestParam(value = "idListRegion") Integer idListRegion,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer size
    ) throws ServiceException {

        try {
            if(offset == null) {
                offset = 0;
            }

            if(size == null) {
                size = 10;
            }

            SampleListResponse sampleListResponse = sampleService.getSampleList(idListRegion, offset, size);

            return new ResponseEntity<>(sampleListResponse, HttpStatus.OK);

        } catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            log.error("Error: SampleController.list", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/create")
    public ResponseEntity<SampleItemResponse> createSample(
            @RequestBody SaveSampleRequest saveSampleRequest
    ) throws ServiceException {

        try {

            SampleItemResponse sampleItemResponse = sampleService.createSample(saveSampleRequest);

            return new ResponseEntity<>(sampleItemResponse, HttpStatus.OK);
        } catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            log.error("Error: SamplerController.createSample", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<StatusResponse> deleteSample(
            @RequestParam(value = "id") Integer id
    ) throws ServiceException {

        try {
            return new ResponseEntity<>(sampleService.deleteSample(id), HttpStatus.OK);
        } catch (ServiceException e) {
            throw e;
        }
        catch (Exception e) {
            log.error("Error: ListRegionRepository.deleteSample", e);

            throw new ServiceException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
