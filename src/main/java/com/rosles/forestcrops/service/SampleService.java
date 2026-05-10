package com.rosles.forestcrops.service;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.request.ListIntegerRequest;
import com.rosles.forestcrops.dto.response.StatusResponse;
import com.rosles.forestcrops.dto.sample.request.SaveForestCropsItem;
import com.rosles.forestcrops.dto.sample.request.SavePlantsForestCropsItem;
import com.rosles.forestcrops.dto.sample.request.SaveSampleRequest;
import com.rosles.forestcrops.dto.sample.response.*;
import com.rosles.forestcrops.repository.SampleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@Slf4j
public class SampleService {

    @Autowired
    private final SampleRepository sampleRepository;

    public SampleService(SampleRepository sampleRepository) {
        this.sampleRepository = sampleRepository;
    }

    public SampleListResponse getSampleList(Integer idListRegion, Integer offset,
                                            Integer size) throws ServiceException {
        return sampleRepository.getSampleList(idListRegion, offset, size);
    }

    public SampleInfoResponse getSampleInfo(Integer id) throws ServiceException {
        return sampleRepository.getSampleInfo(id);
    }

    public SampleItemResponse createSample(SaveSampleRequest saveSampleRequest) throws ServiceException {
        return sampleRepository.createSample(saveSampleRequest);
    }

    public SampleItemResponse updateSample(SaveSampleRequest saveSampleRequest) throws ServiceException {
        return sampleRepository.updateSample(saveSampleRequest);
    }

    public StatusResponse deleteSample(Integer id) throws ServiceException {
        return sampleRepository.deleteSample(id);
    }

    public ForestCropsList getForestCropsList(Integer idSample, Integer offset,
                                                              Integer size) throws ServiceException {

        return sampleRepository.getForestCropsList(idSample, offset, size);
    }

    public StatusResponse deleteForestCrops(ListIntegerRequest listIntegerRequest) throws ServiceException {
        return sampleRepository.deleteForestCrops(listIntegerRequest);
    }

    public StatusResponse createForestCrops(SaveForestCropsItem saveForestCropsItem) throws ServiceException {
        return sampleRepository.createForestCrops(saveForestCropsItem);
    }

    public StatusResponse updateForestCrops(SaveForestCropsItem saveForestCropsItem) throws ServiceException {
        return sampleRepository.updateForestCrops(saveForestCropsItem);
    }

    public PlantsForestCropsList getPlantsForestCropsList( Integer idSample, Integer offset, Integer size) throws ServiceException {
        return sampleRepository.getPlantsForestCropsList(idSample, offset, size);
    }

    public StatusResponse deletePlansForestCrops(ListIntegerRequest listIntegerRequest) throws ServiceException {
        return sampleRepository.deletePlansForestCrops(listIntegerRequest);
    }

    public StatusResponse createPlantsForestCrops(SavePlantsForestCropsItem savePlantsForestCropsItem) throws ServiceException {
        return sampleRepository.createPlantsForestCrops(savePlantsForestCropsItem);
    }
    public StatusResponse updatePlantsForestCrops(SavePlantsForestCropsItem savePlantsForestCropsItem) throws ServiceException {
        return sampleRepository.updatePlantsForestCrops(savePlantsForestCropsItem);
    }


}
