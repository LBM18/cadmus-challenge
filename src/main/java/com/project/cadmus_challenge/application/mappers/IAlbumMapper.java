package com.project.cadmus_challenge.application.mappers;

import com.project.cadmus_challenge.application.dtos.AlbumInputDto;
import com.project.cadmus_challenge.application.dtos.AlbumOutputDto;
import com.project.cadmus_challenge.domain.models.Album;

public interface IAlbumMapper {
    AlbumOutputDto toDto(Album entity);
    Album toEntity(AlbumInputDto dto);
}
