package com.project.cadmus_challenge.unit.usecases.artist.commands;

import com.project.cadmus_challenge.core.bases.UnexpectedUseCaseException;
import com.project.cadmus_challenge.core.usecases.artist.commands.ArtistDeleteCommand;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;
import com.project.cadmus_challenge.unit.usecases.UseCaseUnitTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ArtistDeleteCommandTests extends UseCaseUnitTests {
    @Mock
    private IArtistRepository repositoryMock;

    private ArtistDeleteCommand command;

    private final Long id = 1L;

    @BeforeEach
    public void setup() {
        this.command = new ArtistDeleteCommand(this.id);
        this.command.set_repository(this.repositoryMock);

        when(this.repositoryMock.findById(this.id)).thenReturn(
                Optional.of(new Artist(
                        this.id,
                        "Ana",
                        "Brazilian",
                        "https://www.ana.com.br",
                        "Ana profile image",
                        null
                        )
                )
        );
    }

    @Test
    public void shouldDeleteArtistTest() {
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
                "Error executing use case ArtistDeleteCommand: Entity not found exception: Artist ID " + this.id + " not found in the system.",
                exception.getMessage()
        );

        verify(this.repositoryMock, times(0)).deleteById(this.id);
    }
}
