package com.rosles.forestcrops.service;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.response.StatusResponse;
import com.rosles.forestcrops.dto.sample.request.SaveSampleRequest;
import com.rosles.forestcrops.dto.sample.response.SampleItemResponse;
import com.rosles.forestcrops.dto.sample.response.SampleListResponse;
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

    public SampleItemResponse createSample(SaveSampleRequest saveSampleRequest) throws ServiceException {
        return sampleRepository.createSample(saveSampleRequest);
    }

    public StatusResponse deleteSample(Integer id) throws ServiceException {
        return sampleRepository.deleteSample(id);
    }
}
