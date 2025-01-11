package com.project.cadmus_challenge.api.controllers;

import com.project.cadmus_challenge.api.bases.BaseController;
import com.project.cadmus_challenge.api.responseobjs.PageResultDto;
import com.project.cadmus_challenge.api.responseobjs.SingleResultDto;
import com.project.cadmus_challenge.application.dtos.AlbumInputDto;
import com.project.cadmus_challenge.application.dtos.AlbumOutputDto;
import com.project.cadmus_challenge.application.services.IAlbumService;
import com.project.cadmus_challenge.core.bases.UnexpectedUseCaseException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/album")
@Tag(name = "Album controller API", description = "CRUD operations for the album.")
public class AlbumController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumController.class);

    @Autowired
    private IAlbumService _service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new album", description = "Create a single new album using the requested body.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Album created successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Unexpected usecase exception."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<SingleResultDto<AlbumOutputDto>> create(
            @RequestBody @Valid AlbumInputDto dto
    ) {
        try {
            var result = _service.create(dto);
            return createSuccessResponse(result, HttpStatus.CREATED, LOGGER);
        } catch (UnexpectedUseCaseException ex) {
            return createWarningResponse(ex, LOGGER);
        } catch (Exception ex) {
            return createErrorResponse(ex, LOGGER);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update album", description = "Update a single pre-existing album by its unique identifier using the requested body.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Album updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Unexpected usecase exception."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<SingleResultDto<AlbumOutputDto>> update(
            @PathVariable("id") Long id, @RequestBody @Valid AlbumInputDto dto
    ) {
        try {
            var result = _service.update(id, dto);
            return createSuccessResponse(result, HttpStatus.ACCEPTED, LOGGER);
        } catch (UnexpectedUseCaseException ex) {
            return createWarningResponse(ex, LOGGER);
        } catch (Exception ex) {
            return createErrorResponse(ex, LOGGER);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Delete album", description = "Delete a single album by its unique identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Album deleted successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Unexpected usecase exception."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<SingleResultDto<AlbumOutputDto>> delete(
            @PathVariable("id") Long id
    ) {
        try {
            _service.delete(id);
            return createSuccessResponse(null, HttpStatus.ACCEPTED, LOGGER);
        } catch (UnexpectedUseCaseException ex) {
            return createWarningResponse(ex, LOGGER);
        } catch (Exception ex) {
            return createErrorResponse(ex, LOGGER);
        }
    }

    @GetMapping("/get-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get album by ID", description = "Retrieve a single album by its unique identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Album retrieved successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Unexpected usecase exception."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<SingleResultDto<AlbumOutputDto>> getById(
            @PathVariable("id") Long id
    ) {
        try {
            var result = _service.findById(id);
            return createSuccessResponse(result, HttpStatus.OK, LOGGER);
        } catch (UnexpectedUseCaseException ex) {
            return createWarningResponse(ex, LOGGER);
        } catch (Exception ex) {
            return createErrorResponse(ex, LOGGER);
        }
    }

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all albums with pagination", description = "Retrieve all albums in database with pagination.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Albums retrieved successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Unexpected usecase exception."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<PageResultDto<AlbumOutputDto>> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        try {
            var result = _service.findAll(page, size);
            return createPageSuccessResponse(result, LOGGER);
        } catch (Exception ex) {
            return createPageErrorResponse(ex, LOGGER);
        }
    }
}
