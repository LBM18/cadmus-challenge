package com.project.cadmus_challenge.unit.mappers;

import com.project.cadmus_challenge.application.bases.DurationMusic;
import com.project.cadmus_challenge.application.dtos.MusicInputDto;
import com.project.cadmus_challenge.application.mappers.AlbumMapper;
import com.project.cadmus_challenge.application.mappers.IAlbumMapper;
import com.project.cadmus_challenge.application.mappers.IMusicMapper;
import com.project.cadmus_challenge.application.mappers.MusicMapper;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.models.Music;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MusicMapperTests {
    private final IAlbumMapper _albumMapper = new AlbumMapper();
    private final IMusicMapper _musicMapper = new MusicMapper(_albumMapper);

    @Test
    public void musicMapperToDtoWithNullEntityTest() {
        var dto = _musicMapper.toDto(null, false);

        assertNull(dto);
    }

    @Test
    public void musicMapperToDtoWithoutMappingAlbumTest() {
        var entity = new Music(
                1L,
                "Title",
                10L,
                1L,
                new Album(1L, null, null, null, null, null)
        );
        var dto = _musicMapper.toDto(entity, false);

        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getTitle(), dto.title());
        assertEquals(entity.getDuration(), dto.duration().toTotalSeconds());
        assertEquals(entity.getTrack(), dto.track());
        assertEquals(entity.getAlbum().getId(), dto.albumId());
        assertNull(dto.album());
    }

    @Test
    public void musicMapperToDtoWithMappingAlbumTest() {
        var entity = new Music(
                1L,
                "Title",
                10L,
                1L,
                new Album(1L, null, null, null, new Artist(), null)
        );
        var dto = _musicMapper.toDto(entity, true);

        assertEquals(entity.getId(), dto.id());
        assertEquals(entity.getTitle(), dto.title());
        assertEquals(entity.getDuration(), dto.duration().toTotalSeconds());
        assertEquals(entity.getTrack(), dto.track());
        assertEquals(entity.getAlbum().getId(), dto.albumId());
        assertEquals(_albumMapper.toDto(entity.getAlbum()), dto.album());
    }

    @Test
    public void musicMapperToEntityWithNullDtoTest() {
        var entity = _musicMapper.toEntity(null);

        assertNull(entity);
    }

    @Test
    public void musicMapperToEntityTest() {
        var duration = new DurationMusic(10L);

        var dto = new MusicInputDto(
                "Title",
                duration,
                1L,
                1L
        );
        var entity = _musicMapper.toEntity(dto);

        assertNull(entity.getId());
        assertEquals(dto.title(), entity.getTitle());
        assertEquals(duration.toTotalSeconds(), entity.getDuration());
        assertEquals(dto.track(), entity.getTrack());
        assertNull(entity.getAlbum());
    }
}
