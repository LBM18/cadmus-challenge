package com.project.cadmus_challenge.application.services;

import com.project.cadmus_challenge.application.dtos.ArtistInputDto;
import com.project.cadmus_challenge.application.dtos.ArtistOutputDto;
import org.springframework.data.domain.Page;

public interface IArtistService {
    ArtistOutputDto create(ArtistInputDto dto);
    ArtistOutputDto update(Long id, ArtistInputDto dto);
    void delete(Long id);
    ArtistOutputDto findById(Long id);
    Page<ArtistOutputDto> findAll(Integer page, Integer size);
}
