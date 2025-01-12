package com.project.cadmus_challenge.unit.usecases.music.querys;

import com.project.cadmus_challenge.core.bases.UnexpectedUseCaseException;
import com.project.cadmus_challenge.core.usecases.music.querys.MusicFindByArtistQuery;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.models.Music;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;
import com.project.cadmus_challenge.domain.persistences.IMusicRepository;
import com.project.cadmus_challenge.unit.usecases.UseCaseUnitTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MusicFindByArtistQueryTests extends UseCaseUnitTests {
    @Mock
    private IArtistRepository artistRepositoryMock;

    @Mock
    private IMusicRepository musicRepositoryMock;

    private MusicFindByArtistQuery query;

    private final Long artistId = 1L;

    private Music music;

    private Artist artist;

    @BeforeEach
    public void setup() {
        this.artist = new Artist(
                this.artistId,
                "Maria",
                "Brazilian",
                "https://www.maria.com.br",
                "Maria profile image",
                null
        );
        this.music = new Music(
                1L,
                "Song Title",
                180L,
                1L,
                new Album(
                        1L,
                        "Love songs",
                        2024L,
                        "Love songs cover image",
                        this.artist,
                        null
                )
        );

        this.query = new MusicFindByArtistQuery(this.artistId);
        this.query.set_artistRepository(this.artistRepositoryMock);
        this.query.set_musicRepository(this.musicRepositoryMock);
    }

    @Test
    public void shouldReturnMusicsWhenArtistIdExistsTest() {
        when(this.artistRepositoryMock.findById(this.artistId)).thenReturn(Optional.of(this.artist));
        when(this.musicRepositoryMock.findByAlbumArtistId(this.artistId)).thenReturn(List.of(this.music));

        var result = super.facade.execute(this.query);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(this.music.getId(), result.get(0).getId());
        assertEquals(this.music.getTitle(), result.get(0).getTitle());
        assertEquals(this.music.getDuration(), result.get(0).getDuration());
        assertEquals(this.music.getTrack(), result.get(0).getTrack());
        assertEquals(this.music.getAlbum(), result.get(0).getAlbum());

        verify(this.artistRepositoryMock, times(1)).findById(this.artistId);
        verify(this.musicRepositoryMock, times(1)).findByAlbumArtistId(this.artistId);
    }

    @Test
    public void shouldThrowExceptionForNonExistentArtistIdErrorTest() {
        when(this.artistRepositoryMock.findById(this.artistId)).thenReturn(Optional.empty());

        var exception = assertThrows(
                UnexpectedUseCaseException.class,
                () -> super.facade.execute(this.query)
        );

        assertEquals(
                "Error executing use case MusicFindByArtistQuery: Entity not found exception: Artist ID " + this.artistId + " not found in the system.",
                exception.getMessage()
        );

        verify(this.artistRepositoryMock, times(1)).findById(this.artistId);
    }

    @Test
    public void shouldReturnEmptyListForNoMusicFoundTest() {
        when(this.artistRepositoryMock.findById(this.artistId)).thenReturn(Optional.of(this.artist));
        when(this.musicRepositoryMock.findByAlbumArtistId(this.artistId)).thenReturn(List.of());

        var result = super.facade.execute(this.query);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(this.artistRepositoryMock, times(1)).findById(this.artistId);
        verify(this.musicRepositoryMock, times(1)).findByAlbumArtistId(this.artistId);
    }
}
