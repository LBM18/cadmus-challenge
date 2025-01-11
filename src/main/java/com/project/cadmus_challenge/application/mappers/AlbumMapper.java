package com.project.cadmus_challenge.application.mappers;

import com.project.cadmus_challenge.application.dtos.AlbumInputDto;
import com.project.cadmus_challenge.application.dtos.AlbumOutputDto;
import com.project.cadmus_challenge.domain.models.Album;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper implements IAlbumMapper {

    @Override
    public AlbumOutputDto toDto(Album entity) {
        if (entity == null) {
            return null;
        }
        return new AlbumOutputDto(
                entity.getId(),
                entity.getTitle(),
                entity.getReleaseYear(),
                entity.getCoverImage(),
                entity.getArtist().getId()
        );
    }

    @Override
    public Album toEntity(AlbumInputDto dto) {
        if (dto == null) {
            return null;
        }
        return new Album(
                null, // ID will be generated automatically
                dto.title(),
                dto.releaseYear(),
                dto.coverImage(),
                null, // Artist ID will be assigned directly in the Service
                null // Prevents cyclic mapping
        );
    }
}
