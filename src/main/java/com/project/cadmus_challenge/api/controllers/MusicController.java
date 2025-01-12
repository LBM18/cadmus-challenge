package com.project.cadmus_challenge.api.controllers;

import com.project.cadmus_challenge.api.bases.BaseController;
import com.project.cadmus_challenge.api.responseobjs.ListResultDto;
import com.project.cadmus_challenge.api.responseobjs.PageResultDto;
import com.project.cadmus_challenge.api.responseobjs.SingleResultDto;
import com.project.cadmus_challenge.application.dtos.MusicInputDto;
import com.project.cadmus_challenge.application.dtos.MusicOutputDto;
import com.project.cadmus_challenge.application.services.IMusicService;
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
@RequestMapping("/api/v1/music")
@Tag(name = "Music controller API", description = "CRUD operations for the music.")
public class MusicController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MusicController.class);

    @Autowired
    private IMusicService _service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new music", description = "Create a single new music using the requested body.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Music created successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Unexpected usecase exception."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<SingleResultDto<MusicOutputDto>> create(
            @RequestBody @Valid MusicInputDto dto
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
    @Operation(summary = "Update music", description = "Update a single pre-existing music by its unique identifier using the requested body.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Music updated successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Unexpected usecase exception."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<SingleResultDto<MusicOutputDto>> update(
            @PathVariable("id") Long id, @RequestBody @Valid MusicInputDto dto
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
    @Operation(summary = "Delete music", description = "Delete a single music by its unique identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "Music deleted successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Unexpected usecase exception."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<SingleResultDto<MusicOutputDto>> delete(
            @PathVariable("id") Long id
    ) {
        try {
            var result = _service.delete(id);
            return createSuccessResponse(result, HttpStatus.ACCEPTED, LOGGER);
        } catch (UnexpectedUseCaseException ex) {
            return createWarningResponse(ex, LOGGER);
        } catch (Exception ex) {
            return createErrorResponse(ex, LOGGER);
        }
    }

    @GetMapping("/get-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get music by ID", description = "Retrieve a single music by its unique identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Music retrieved successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Unexpected usecase exception."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<SingleResultDto<MusicOutputDto>> getById(
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
    @Operation(summary = "Get all musics with pagination", description = "Retrieve all musics in database with pagination.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Musics retrieved successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Unexpected usecase exception."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<PageResultDto<MusicOutputDto>> getAll(
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

    @GetMapping("/get-by-artist/{artistId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get musics by artist", description = "Retrieve all musics by a specific artist, including album information.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Musics retrieved successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Unexpected usecase exception."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<ListResultDto<MusicOutputDto>> getByArtist(
            @PathVariable("artistId") Long artistId
    ) {
        try {
            var result = _service.findByArtist(artistId);
            return createListSuccessResponse(result, LOGGER);
        } catch (UnexpectedUseCaseException ex) {
            return createListWarningResponse(ex, LOGGER);
        } catch (Exception ex) {
            return createListErrorResponse(ex, LOGGER);
        }
    }

    @GetMapping("/get-by-album/{albumId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get musics by album", description = "Retrieve all musics by a specific album, sorted by track number or title.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Musics retrieved successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request, unacceptable format or data."),
            @ApiResponse(responseCode = "404", description = "Unexpected usecase exception."),
            @ApiResponse(responseCode = "500", description = "Unexpected internal server error.")
    })
    public ResponseEntity<ListResultDto<MusicOutputDto>> getByAlbum(
            @PathVariable("albumId") Long albumId,
            @RequestParam(value = "sortByTrack", defaultValue = "true") Boolean sortByTrack
    ) {
        try {
            var result = _service.findByAlbum(albumId, sortByTrack);
            return createListSuccessResponse(result, LOGGER);
        } catch (UnexpectedUseCaseException ex) {
            return createListWarningResponse(ex, LOGGER);
        } catch (Exception ex) {
            return createListErrorResponse(ex, LOGGER);
        }
    }
}
