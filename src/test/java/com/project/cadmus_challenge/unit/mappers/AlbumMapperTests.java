package com.project.cadmus_challenge.unit.mappers;

import com.project.cadmus_challenge.application.dtos.AlbumInputDto;
import com.project.cadmus_challenge.application.mappers.AlbumMapper;
import com.project.cadmus_challenge.application.mappers.IAlbumMapper;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.models.Music;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AlbumMapperTests {
    private final IAlbumMapper _mapper = new AlbumMapper();

    @Test
    public void albumMapperToDtoWithNullEntityTest() {
        var dto = _mapper.toDto(null);

        assertNull(dto);
    }

    @Test
    public void albumMapperToDtoTest() {
        var musics = new ArrayList<Music>();
        musics.add(new Music());

        var entity = new Album(
                1L,
                "Title",
                2025L,
                "Cover image",
                new Artist(1L, null, null, null, null, null),
                musics
        );
        var dto = _mapper.toDto(entity);

        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getTitle(), dto.title());
        assertEquals(entity.getReleaseYear(), dto.releaseYear());
        assertEquals(entity.getCoverImage(), dto.coverImage());
        assertEquals(entity.getArtist().getId(), dto.artistId());
    }

    @Test
    public void albumMapperToEntityWithNullDtoTest() {
        var entity = _mapper.toEntity(null);

        assertNull(entity);
    }

    @Test
    public void albumMapperToEntityTest() {
        var dto = new AlbumInputDto(
                "Title",
                2025L,
                "Cover image",
                1L
        );
        var entity = _mapper.toEntity(dto);

        assertNull(entity.getId());
        assertEquals(dto.title(), entity.getTitle());
        assertEquals(dto.releaseYear(), entity.getReleaseYear());
        assertEquals(dto.coverImage(), entity.getCoverImage());
        assertNull(entity.getArtist());
        assertNull(entity.getMusics());
    }
}
