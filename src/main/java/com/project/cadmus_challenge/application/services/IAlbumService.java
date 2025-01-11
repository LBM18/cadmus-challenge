package com.project.cadmus_challenge.application.services;

import com.project.cadmus_challenge.application.dtos.AlbumInputDto;
import com.project.cadmus_challenge.application.dtos.AlbumOutputDto;
import org.springframework.data.domain.Page;

public interface IAlbumService {
    AlbumOutputDto create(AlbumInputDto dto);
    AlbumOutputDto update(Long id, AlbumInputDto dto);
    void delete(Long id);
    AlbumOutputDto findById(Long id);
    Page<AlbumOutputDto> findAll(Integer page, Integer size);
}
