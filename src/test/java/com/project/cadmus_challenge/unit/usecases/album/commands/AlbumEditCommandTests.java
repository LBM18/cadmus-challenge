package com.project.cadmus_challenge.unit.usecases.album.commands;

import com.project.cadmus_challenge.core.bases.UnexpectedUseCaseException;
import com.project.cadmus_challenge.core.usecases.album.commands.AlbumEditCommand;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.persistences.IAlbumRepository;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;
import com.project.cadmus_challenge.unit.usecases.UseCaseUnitTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AlbumEditCommandTests extends UseCaseUnitTests {
    @Mock
    private IArtistRepository artistRepositoryMock;

    @Mock
    private IAlbumRepository albumRepositoryMock;

    private AlbumEditCommand command;

    private Album album;

    private Artist artist;

    private final Long albumId = 1L;

    private final Long artistId = 2L;

    @BeforeEach
    public void setup() {
        this.artist = new Artist(
                this.artistId,
                "  Maria  ",
                "  Brazilian  ",
                "  https://www.maria.com.br  ",
                "  Maria profile image  ",
                null
        );
        this.album = new Album(
                this.albumId,
                "  Love songs  ",
                2024L,
                "  Love songs cover image  ",
                this.artist,
                null
        );
        this.command = new AlbumEditCommand(this.album, this.albumId, this.artistId);
        this.command.set_artistRepository(this.artistRepositoryMock);
        this.command.set_albumRepository(this.albumRepositoryMock);

        when(this.albumRepositoryMock.findById(this.albumId)).thenReturn(
                Optional.of(new Album(
                        this.albumId,
                        "  Rock songs  ",
                        2004L,
                        "  Rock songs cover image  ",
                        this.artist,
                        null
                ))
        );
    }

    @Test
    public void shouldEditAlbumTest() {
        when(this.artistRepositoryMock.findById(this.artistId)).thenReturn(Optional.of(this.artist));
        when(this.albumRepositoryMock.findById(this.albumId)).thenReturn(Optional.of(this.album));
        when(this.albumRepositoryMock.save(any(Album.class))).thenReturn(this.album);

        var result = super.facade.execute(this.command);

        assertNotNull(result);
        assertEquals(this.album.getId(), result.getId());
        assertEquals(this.album.getTitle().trim(), result.getTitle());
        assertEquals(this.album.getReleaseYear(), result.getReleaseYear());
        assertEquals(this.album.getCoverImage().trim(), result.getCoverImage());
        assertEquals(this.album.getArtist(), result.getArtist());
        assertEquals(this.album.getMusics(), result.getMusics());

        verify(this.artistRepositoryMock, times(1)).findById(this.artistId);
        verify(this.albumRepositoryMock, times(1)).findById(this.albumId);
        verify(this.albumRepositoryMock, times(1)).save(this.album);
    }

    @Test
    void shouldThrowsExceptionForNonExistentIdErrorTest() {
        when(this.albumRepositoryMock.findById(this.albumId)).thenReturn(Optional.empty());

        var exception = assertThrows(
                UnexpectedUseCaseException.class, () -> super.facade.execute(this.command)
        );
        assertEquals(
                "Error executing use case AlbumEditCommand: Entity not found exception: Album ID " + this.albumId + " not found in the system.",
                exception.getMessage()
        );

        verify(this.artistRepositoryMock, times(0)).findById(this.artistId);
        verify(this.albumRepositoryMock, times(1)).findById(this.albumId);
        verify(this.albumRepositoryMock, times(0)).save(this.album);
    }

    @Test
    void shouldThrowsExceptionForNonExistentArtistIdErrorTest() {
        when(this.albumRepositoryMock.findById(this.albumId)).thenReturn(Optional.of(this.album));
        when(this.artistRepositoryMock.findById(this.artistId)).thenReturn(Optional.empty());

        var exception = assertThrows(
                UnexpectedUseCaseException.class, () -> super.facade.execute(this.command)
        );
        assertEquals(
                "Error executing use case AlbumEditCommand: Entity not found exception: Artist ID " + this.artistId + " not found in the system.",
                exception.getMessage()
        );

        verify(this.artistRepositoryMock, times(1)).findById(this.artistId);
        verify(this.albumRepositoryMock, times(1)).findById(this.albumId);
        verify(this.albumRepositoryMock, times(0)).save(this.album);
    }

    @Test
    public void shouldThrowsExceptionWithInvalidWebsiteAddressErrorTest() {
        when(this.albumRepositoryMock.findById(this.albumId)).thenReturn(Optional.of(this.album));
        when(this.artistRepositoryMock.findById(this.artistId)).thenReturn(Optional.of(this.artist));

        this.album.setReleaseYear(3000L);

        var exception = assertThrows(
                UnexpectedUseCaseException.class, () -> super.facade.execute(this.command)
        );
        assertEquals(
                "Error executing use case AlbumEditCommand: Business exception: Album release year must not be a date later than the current one.",
                exception.getMessage()
        );

        verify(this.artistRepositoryMock, times(1)).findById(this.artistId);
        verify(this.albumRepositoryMock, times(1)).findById(this.albumId);
        verify(this.albumRepositoryMock, times(0)).save(this.album);
    }
}
