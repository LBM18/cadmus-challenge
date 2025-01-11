package com.project.cadmus_challenge.mappers;

import com.project.cadmus_challenge.application.dtos.ArtistInputDto;
import com.project.cadmus_challenge.application.mappers.ArtistMapper;
import com.project.cadmus_challenge.application.mappers.IArtistMapper;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.models.Artist;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ArtistMapperTests {
    private final IArtistMapper _mapper = new ArtistMapper();

    @Test
    public void artistMapperToDtoWithNullEntityTest() {
        var dto = _mapper.toDto(null);

        assertNull(dto);
    }

    @Test
    public void artistMapperToDtoTest() {
        var albums = new ArrayList<Album>();
        albums.add(new Album());

        var entity = new Artist(
                1L,
                "Name",
                "Nationality",
                "Website address",
                "Profile image",
                albums
        );
        var dto = _mapper.toDto(entity);

        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getName(), dto.name());
        assertEquals(entity.getNationality(), dto.nationality());
        assertEquals(entity.getWebsiteAddress(), dto.websiteAddress());
        assertEquals(entity.getProfileImage(), dto.profileImage());
    }

    @Test
    public void artistMapperToEntityWithNullDtoTest() {
        var entity = _mapper.toEntity(null);

        assertNull(entity);
    }

    @Test
    public void artistMapperToEntityTest() {
        var dto = new ArtistInputDto(
                "Name",
                "Nationality",
                "Website address",
                "Profile image"
        );
        var entity = _mapper.toEntity(dto);

        assertNull(entity.getId());
        assertEquals(dto.name(), entity.getName());
        assertEquals(dto.nationality(), entity.getNationality());
        assertEquals(dto.websiteAddress(), entity.getWebsiteAddress());
        assertEquals(dto.profileImage(), entity.getProfileImage());
        assertNull(entity.getAlbums());
    }
}
