package com.project.cadmus_challenge.unit.usecases.music.querys;

import com.project.cadmus_challenge.core.bases.UnexpectedUseCaseException;
import com.project.cadmus_challenge.core.usecases.music.querys.MusicFindByAlbumQuery;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.models.Music;
import com.project.cadmus_challenge.domain.persistences.IAlbumRepository;
import com.project.cadmus_challenge.domain.persistences.IMusicRepository;
import com.project.cadmus_challenge.unit.usecases.UseCaseUnitTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MusicFindByAlbumQueryTests extends UseCaseUnitTests{
    @Mock
    private IAlbumRepository albumRepositoryMock;

    @Mock
    private IMusicRepository musicRepositoryMock;

    private MusicFindByAlbumQuery query;

    private final Long albumId = 1L;

    private Album album;
    private Music music;

    @BeforeEach
    public void setup() {
        this.album = new Album(
                this.albumId,
                "Love songs",
                2024L,
                "Love songs cover image",
                null,
                null
        );
        this.music = new Music(
                1L,
                "Song Title",
                180L,
                1L,
                this.album
        );

        this.query = new MusicFindByAlbumQuery(this.albumId, true);
        this.query.set_albumRepository(this.albumRepositoryMock);
        this.query.set_musicRepository(this.musicRepositoryMock);
    }

    @Test
    public void shouldReturnMusicsWhenAlbumIdExistsAndSortedByTrackTest() {
        var sort = Sort.by(Sort.Direction.ASC, "track");
        when(this.albumRepositoryMock.findById(this.albumId)).thenReturn(Optional.of(this.album));
        when(this.musicRepositoryMock.findByAlbumId(this.albumId, sort)).thenReturn(List.of(this.music));

        var result = super.facade.execute(this.query);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(this.music.getId(), result.get(0).getId());
        assertEquals(this.music.getTitle(), result.get(0).getTitle());
        assertEquals(this.music.getDuration(), result.get(0).getDuration());
        assertEquals(this.music.getTrack(), result.get(0).getTrack());
        assertEquals(this.music.getAlbum(), result.get(0).getAlbum());

        verify(this.albumRepositoryMock, times(1)).findById(this.albumId);
        verify(this.musicRepositoryMock, times(1)).findByAlbumId(this.albumId, sort);
    }

    @Test
    public void shouldReturnMusicsWhenAlbumIdExistsAndSortedByTitleTest() {
        this.query = new MusicFindByAlbumQuery(this.albumId, false);
        this.query.set_albumRepository(this.albumRepositoryMock);
        this.query.set_musicRepository(this.musicRepositoryMock);

        var sort = Sort.by(Sort.Direction.ASC, "title");
        when(this.albumRepositoryMock.findById(this.albumId)).thenReturn(Optional.of(this.album));
        when(this.musicRepositoryMock.findByAlbumId(this.albumId, sort)).thenReturn(List.of(this.music));

        var result = super.facade.execute(this.query);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(this.music.getId(), result.get(0).getId());
        assertEquals(this.music.getTitle(), result.get(0).getTitle());
        assertEquals(this.music.getDuration(), result.get(0).getDuration());
        assertEquals(this.music.getTrack(), result.get(0).getTrack());
        assertEquals(this.music.getAlbum(), result.get(0).getAlbum());

        verify(this.albumRepositoryMock, times(1)).findById(this.albumId);
        verify(this.musicRepositoryMock, times(1)).findByAlbumId(this.albumId, sort);
    }

    @Test
    public void shouldThrowExceptionForNonExistentAlbumIdErrorTest() {
        when(this.albumRepositoryMock.findById(this.albumId)).thenReturn(Optional.empty());

        var exception = assertThrows(
                UnexpectedUseCaseException.class,
                () -> super.facade.execute(this.query)
        );

        assertEquals(
                "Error executing use case MusicFindByAlbumQuery: Entity not found exception: Album ID " + this.albumId + " not found in the system.",
                exception.getMessage()
        );

        verify(this.albumRepositoryMock, times(1)).findById(this.albumId);
    }

    @Test
    public void shouldReturnEmptyListWhenNoMusicFoundTest() {
        var sort = Sort.by(Sort.Direction.ASC, "track");
        when(this.albumRepositoryMock.findById(this.albumId)).thenReturn(Optional.of(this.album));
        when(this.musicRepositoryMock.findByAlbumId(this.albumId, sort)).thenReturn(List.of());

        var result = super.facade.execute(this.query);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(this.albumRepositoryMock, times(1)).findById(this.albumId);
        verify(this.musicRepositoryMock, times(1)).findByAlbumId(this.albumId, sort);
    }
}
