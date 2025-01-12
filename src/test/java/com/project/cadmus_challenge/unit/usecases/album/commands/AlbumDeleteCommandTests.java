package com.project.cadmus_challenge.unit.usecases.album.commands;

import com.project.cadmus_challenge.core.bases.UnexpectedUseCaseException;
import com.project.cadmus_challenge.core.usecases.album.commands.AlbumDeleteCommand;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.persistences.IAlbumRepository;
import com.project.cadmus_challenge.unit.usecases.UseCaseUnitTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AlbumDeleteCommandTests extends UseCaseUnitTests {
    @Mock
    private IAlbumRepository repositoryMock;

    private AlbumDeleteCommand command;

    private final Long id = 1L;

    private Album album;

    @BeforeEach
    public void setup() {
        this.album = new Album(
                this.id,
                "Love songs",
                2024L,
                "Love songs cover image",
                new Artist(
                        1L,
                        "Maria",
                        "Brazilian",
                        "https://www.maria.com.br",
                        "Maria profile image",
                        null
                ),
                null
        );

        this.command = new AlbumDeleteCommand(this.id);
        this.command.set_repository(this.repositoryMock);

        when(this.repositoryMock.findById(this.id)).thenReturn(Optional.of(this.album));
    }

    @Test
    public void shouldDeleteAlbumAndReturnEntityTest() {
        var result = super.facade.execute(this.command);

        assertNotNull(result);
        assertEquals(this.album.getId(), result.getId());
        assertEquals(this.album.getTitle(), result.getTitle());
        assertEquals(this.album.getReleaseYear(), result.getReleaseYear());
        assertEquals(this.album.getCoverImage(), result.getCoverImage());
        assertEquals(this.album.getArtist(), result.getArtist());
        assertEquals(this.album.getMusics(), result.getMusics());

        verify(this.repositoryMock, times(1)).findById(this.id);
        verify(this.repositoryMock, times(1)).delete(this.album);
    }

    @Test
    void shouldThrowsExceptionForNonExistentIdErrorTest() {
        when(this.repositoryMock.findById(this.id)).thenReturn(Optional.empty());

        var exception = assertThrows(
                UnexpectedUseCaseException.class, () -> super.facade.execute(this.command)
        );
        assertEquals(
                "Error executing use case AlbumDeleteCommand: Entity not found exception: Album ID " + this.id + " not found in the system.",
                exception.getMessage()
        );

        verify(this.repositoryMock, times(1)).findById(this.id);
        verify(this.repositoryMock, times(0)).delete(this.album);
    }
}
