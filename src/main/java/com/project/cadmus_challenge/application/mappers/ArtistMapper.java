package com.project.cadmus_challenge.application.mappers;

import com.project.cadmus_challenge.application.dtos.ArtistInputDto;
import com.project.cadmus_challenge.application.dtos.ArtistOutputDto;
import com.project.cadmus_challenge.domain.models.Artist;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper implements IArtistMapper {

    @Override
    public ArtistOutputDto toDto(Artist entity) {
        if (entity == null) {
            return null;
        }
        return new ArtistOutputDto(
                entity.getId(),
                entity.getName(),
                entity.getNationality(),
                entity.getWebsiteAddress(),
                entity.getProfileImage()
        );
    }

    @Override
    public Artist toEntity(ArtistInputDto dto) {
        if (dto == null) {
            return null;
        }
        return new Artist(
                null, // ID will be generated automatically
                dto.name(),
                dto.nationality(),
                dto.websiteAddress(),
                dto.profileImage(),
                null // Prevents cyclic mapping
        );
    }
}
