package com.rosles.forestcrops.config;

import com.rosles.forestcrops.config.exception.ServiceException;
import com.rosles.forestcrops.dto.response.StatusResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<StatusResponse> handleException(ServiceException e) {
        return new ResponseEntity<>(new StatusResponse(1, e.getMessage()), e.getHttpStatus());
    }

}
