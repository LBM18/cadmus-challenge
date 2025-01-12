package com.project.cadmus_challenge.unit.usecases.artist.commands;

import com.project.cadmus_challenge.core.bases.UnexpectedUseCaseException;
import com.project.cadmus_challenge.core.usecases.artist.commands.ArtistCreateCommand;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;
import com.project.cadmus_challenge.unit.usecases.UseCaseUnitTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArtistCreateCommandTests extends UseCaseUnitTests {
    @Mock
    private IArtistRepository repositoryMock;

    private ArtistCreateCommand command;

    private Artist artist;

    @BeforeEach
    public void setup() {
        this.artist = new Artist(
                null,
                "  Maria  ",
                "  Brazilian  ",
                "  https://www.maria.com.br  ",
                "  Maria profile image  ",
                null
        );
        this.command = new ArtistCreateCommand(this.artist);
        this.command.set_repository(this.repositoryMock);
    }

    @Test
    public void shouldCreateArtistTest() {
        when(this.repositoryMock.save(any(Artist.class))).thenReturn(this.artist);

        var result = super.facade.execute(this.command);

        assertNotNull(result);
        assertEquals(this.artist.getId(), result.getId());
        assertEquals(this.artist.getName().trim(), result.getName());
        assertEquals(this.artist.getNationality().trim(), result.getNationality());
        assertEquals(this.artist.getWebsiteAddress().trim(), result.getWebsiteAddress());
        assertEquals(this.artist.getProfileImage().trim(), result.getProfileImage());
        assertEquals(this.artist.getAlbums(), result.getAlbums());

        verify(this.repositoryMock, times(1)).save(this.artist);
    }

    @Test
    public void shouldThrowsExceptionWithInvalidWebsiteAddressErrorTest() {
        this.artist.setWebsiteAddress("  www.maria.com.br  ");

        var exception = assertThrows(
                UnexpectedUseCaseException.class, () -> super.facade.execute(this.command)
        );
        assertEquals(
                "Error executing use case ArtistCreateCommand: Business exception: Artist website address must be a valid URL.",
                exception.getMessage()
        );

        verify(this.repositoryMock, times(0)).save(this.artist);
    }
}
