package com.project.cadmus_challenge.unit.usecases.artist.commands;

import com.project.cadmus_challenge.core.bases.UnexpectedUseCaseException;
import com.project.cadmus_challenge.core.usecases.artist.commands.ArtistEditCommand;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;
import com.project.cadmus_challenge.unit.usecases.UseCaseUnitTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArtistEditCommandTests extends UseCaseUnitTests {
    @Mock
    private IArtistRepository repositoryMock;

    private ArtistEditCommand command;

    private Artist artist;

    private final Long id = 1L;

    @BeforeEach
    public void setup() {
        this.artist = new Artist(
                this.id,
                "  Maria  ",
                "  Brazilian  ",
                "  https://www.maria.com.br  ",
                "  Maria profile image  ",
                null
        );
        this.command = new ArtistEditCommand(this.artist, this.id);
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
    public void shouldEditArtistTest() {
        when(this.repositoryMock.save(any(Artist.class))).thenReturn(this.artist);

        var result = super.facade.execute(this.command);

        assertNotNull(result);
        assertEquals(this.artist.getId(), result.getId());
        assertEquals(this.artist.getName().trim(), result.getName());
        assertEquals(this.artist.getNationality().trim(), result.getNationality());
        assertEquals(this.artist.getWebsiteAddress().trim(), result.getWebsiteAddress());
        assertEquals(this.artist.getProfileImage().trim(), result.getProfileImage());
        assertEquals(this.artist.getAlbums(), result.getAlbums());

        verify(this.repositoryMock, times(1)).findById(this.id);
        verify(this.repositoryMock, times(1)).save(this.artist);
    }

    @Test
    void shouldThrowsExceptionForNonExistentIdErrorTest() {
        when(this.repositoryMock.findById(this.id)).thenReturn(Optional.empty());

        var exception = assertThrows(
                UnexpectedUseCaseException.class, () -> super.facade.execute(this.command)
        );
        assertEquals(
                "Error executing use case ArtistEditCommand: Entity not found exception: Artist ID " + this.id + " not found in the system.",
                exception.getMessage()
        );

        verify(this.repositoryMock, times(1)).findById(this.id);
        verify(this.repositoryMock, times(0)).save(this.artist);
    }

    @Test
    public void shouldThrowsExceptionWithInvalidWebsiteAddressErrorTest() {
        this.artist.setWebsiteAddress("  www.maria.com.br  ");

        var exception = assertThrows(
                UnexpectedUseCaseException.class, () -> super.facade.execute(this.command)
        );
        assertEquals(
                "Error executing use case ArtistEditCommand: Business exception: Artist website address must be a valid URL.",
                exception.getMessage()
        );

        verify(this.repositoryMock, times(1)).findById(this.id);
        verify(this.repositoryMock, times(0)).save(this.artist);
    }
}
