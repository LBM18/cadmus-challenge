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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AlbumDeleteCommandTests extends UseCaseUnitTests {
    @Mock
    private IAlbumRepository repositoryMock;

    private AlbumDeleteCommand command;

    private final Long id = 1L;

    @BeforeEach
    public void setup() {
        this.command = new AlbumDeleteCommand(this.id);
        this.command.set_repository(this.repositoryMock);

        when(this.repositoryMock.findById(this.id)).thenReturn(
                Optional.of(new Album(
                        this.id,
                        "  Love songs  ",
                        2024L,
                        "  Love songs cover image  ",
                        new Artist(
                                1L,
                                "  Maria  ",
                                "  Brazilian  ",
                                "  https://www.maria.com.br  ",
                                "  Maria profile image  ",
                                null
                        ),
                        null
                ))
        );
    }

    @Test
    public void shouldDeleteAlbumTest() {
        super.facade.execute(this.command);

        verify(this.repositoryMock, times(1)).findById(this.id);
        verify(this.repositoryMock, times(1)).deleteById(this.id);
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

        verify(this.repositoryMock, times(0)).deleteById(this.id);
    }
}
