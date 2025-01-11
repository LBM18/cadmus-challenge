package com.project.cadmus_challenge.application.services;

import com.project.cadmus_challenge.application.dtos.MusicInputDto;
import com.project.cadmus_challenge.application.dtos.MusicOutputDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IMusicService {
    MusicOutputDto create(MusicInputDto dto);
    MusicOutputDto update(Long id, MusicInputDto dto);
    void delete(Long id);
    MusicOutputDto findById(Long id);
    Page<MusicOutputDto> findAll(Integer page, Integer size);
    List<MusicOutputDto> findByArtist(Long artistId);
    List<MusicOutputDto> findByAlbum(Long albumId, Boolean isSortByTrack);
}
