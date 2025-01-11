package com.project.cadmus_challenge.application.mappers;

import com.project.cadmus_challenge.application.dtos.ArtistInputDto;
import com.project.cadmus_challenge.application.dtos.ArtistOutputDto;
import com.project.cadmus_challenge.domain.models.Artist;

public interface IArtistMapper {
    ArtistOutputDto toDto(Artist entity);
    Artist toEntity(ArtistInputDto dto);
}
