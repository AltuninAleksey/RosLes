package com.rosles.forestcrops.service.mobile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.mobile.listregion.request.SaveListRegionMobileRequest;
import com.rosles.forestcrops.dto.mobile.listregion.response.ListRegionListMobile;
import com.rosles.forestcrops.dto.web.response.CheckCZL;
import com.rosles.forestcrops.repository.mobile.ListRegionMobileRepository;
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
public class ListRegionMobileService {

    @Autowired
    private final HttpQueryService httpQueryService;

    @Autowired
    private final ListRegionMobileRepository listRegionMobileRepository;

    @Value("${service-url.molodnial}")
    private String serviceUrlMolodnial;

    public ListRegionMobileService(HttpQueryService httpQueryService, ListRegionMobileRepository listRegionMobileRepository) {
        this.httpQueryService = httpQueryService;
        this.listRegionMobileRepository = listRegionMobileRepository;
    }

    public ListRegionListMobile getListRegion(String token, Integer idProfile) throws ServiceException {

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

        if (!checkCZL.getSubject().isEmpty()) {
            ListRegionListMobile listRegionList = listRegionMobileRepository.getListRegion(
                    idProfile);

            return listRegionList;
        } else {
            return new ListRegionListMobile();
        }
    }

    public void updateListRegion(List<SaveListRegionMobileRequest> saveListRegionListRequest, Integer IdProfile)  {

        for(SaveListRegionMobileRequest saveListRegionRequest : saveListRegionListRequest) {
            listRegionMobileRepository.updateListRegion(saveListRegionRequest, IdProfile);
        }

    }

}
