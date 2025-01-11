package com.project.cadmus_challenge.application.mappers;

import com.project.cadmus_challenge.application.dtos.MusicInputDto;
import com.project.cadmus_challenge.application.dtos.MusicOutputDto;
import com.project.cadmus_challenge.domain.models.Music;

public interface IMusicMapper {
    MusicOutputDto toDto(Music entity, Boolean isMappingAlbum);
    Music toEntity(MusicInputDto dto);
}
