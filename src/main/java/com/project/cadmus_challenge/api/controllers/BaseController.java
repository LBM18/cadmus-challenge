package com.project.cadmus_challenge.api.controllers;

import com.project.cadmus_challenge.api.responseobjs.ListResultDto;
import com.project.cadmus_challenge.api.responseobjs.PageResultDto;
import com.project.cadmus_challenge.api.responseobjs.SingleResultDto;
import com.project.cadmus_challenge.core.bases.UnexpectedUseCaseException;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public abstract class BaseController {
    private static final String SUCCESS_MESSAGE = "Returned successfully: ";
    private static final String USECASE_MESSAGE = "Usecase exception: ";
    private static final String ERROR_MESSAGE = "Unexpected error: ";

    protected static <T> ResponseEntity<SingleResultDto<T>> createSuccessResponse(T data, HttpStatus status, Logger logger) {
        var response = new SingleResultDto<>(data);
        logger.info(SUCCESS_MESSAGE + response.getData());
        return new ResponseEntity<>(response, status);
    }

    protected static <T> ResponseEntity<SingleResultDto<T>> createWarningResponse(UnexpectedUseCaseException ex, Logger logger) {
        var response = new SingleResultDto<T>(ex);
        logger.warn(USECASE_MESSAGE + response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    protected static <T> ResponseEntity<SingleResultDto<T>> createErrorResponse(Exception ex, Logger logger) {
        var response = new SingleResultDto<T>(ex);
        logger.error(ERROR_MESSAGE + response.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected static <T> ResponseEntity<ListResultDto<T>> createListSuccessResponse(List<T> dataList, Logger logger) {
        var response = new ListResultDto<>(dataList);
        logger.info(SUCCESS_MESSAGE + response.getData());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    protected static <T> ResponseEntity<ListResultDto<T>> createListWarningResponse(UnexpectedUseCaseException ex, Logger logger) {
        var response = new ListResultDto<T>(ex);
        logger.warn(USECASE_MESSAGE + response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    protected static <T> ResponseEntity<ListResultDto<T>> createListErrorResponse(Exception ex, Logger logger) {
        var response = new ListResultDto<T>(ex);
        logger.error(ERROR_MESSAGE + response.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected static <T> ResponseEntity<PageResultDto<T>> createPageSuccessResponse(Page<T> dataPage, Logger logger) {
        var response = new PageResultDto<>(dataPage);
        logger.info(SUCCESS_MESSAGE + response.getData());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    protected static <T> ResponseEntity<PageResultDto<T>> createPageErrorResponse(Exception ex, Logger logger) {
        var response = new PageResultDto<T>(ex);
        logger.error(ERROR_MESSAGE + response.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
