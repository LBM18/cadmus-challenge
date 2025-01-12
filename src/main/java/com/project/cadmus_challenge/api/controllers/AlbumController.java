package com.project.cadmus_challenge.api.controllers;

import com.project.cadmus_challenge.api.bases.AlbumInputWrapper;
import com.project.cadmus_challenge.api.bases.FileStorageHandler;
import com.project.cadmus_challenge.api.responseobjs.PageResultDto;
import com.project.cadmus_challenge.api.responseobjs.SingleResultDto;
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
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/album")
@Tag(name = "Album controller API", description = "CRUD operations for the album.")
public class AlbumController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumController.class);

    @Autowired
    private IAlbumService _service;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new album", description = "Create a single new album using the requested body.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Album created successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Unexpected usecase exception."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<SingleResultDto<AlbumOutputDto>> create(
            @ModelAttribute @Valid AlbumInputWrapper dto
    ) {
        try {
            var result = _service.create(dto.toInputDto());
            FileStorageHandler.uploadImage(dto.coverImage(), getImageUniqueIdentifier(result));
            return createSuccessResponse(result, HttpStatus.CREATED, LOGGER);
        } catch (UnexpectedUseCaseException ex) {
            return createWarningResponse(ex, LOGGER);
        } catch (Exception ex) {
            return createErrorResponse(ex, LOGGER);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update album", description = "Update a single pre-existing album by its unique identifier using the requested body.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Album updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Unexpected usecase exception."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<SingleResultDto<AlbumOutputDto>> update(
            @PathVariable("id") Long id,
            @ModelAttribute @Valid AlbumInputWrapper dto
    ) {
        try {
            var result = _service.update(id, dto.toInputDto());
            FileStorageHandler.uploadImage(dto.coverImage(), getImageUniqueIdentifier(result));
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
            var result = _service.delete(id);
            FileStorageHandler.deleteImage(result.coverImage(), getImageUniqueIdentifier(result));
            return createSuccessResponse(result, HttpStatus.ACCEPTED, LOGGER);
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

    @GetMapping("/image/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get Album Profile Image (JPEG)", description = "Retrieve the profile image (JPEG) of the album.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Album profile image retrieved successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Album or image not found."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<Resource> getAlbumProfileImage(
            @PathVariable Long id
    ) {
        try {
            var dto = _service.findById(id);
            if (dto == null) {
                LOGGER.warn("Album not found for ID: " + id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            var resource = FileStorageHandler.getImage(
                    dto.coverImage(),
                    getImageUniqueIdentifier(dto)
            );
            if (resource == null || !resource.exists()) {
                LOGGER.warn("Image not found for album ID: " + id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);
        } catch (Exception ex) {
            LOGGER.error("Error retrieving album profile image for ID: " + id + " - " + ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static String getImageUniqueIdentifier(AlbumOutputDto dto) {
        return "Album" + dto.id();
    }
}
