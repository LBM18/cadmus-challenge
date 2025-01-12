package com.project.cadmus_challenge.unit.usecases.artist.querys;

import com.project.cadmus_challenge.core.bases.UnexpectedUseCaseException;
import com.project.cadmus_challenge.core.usecases.artist.querys.ArtistFindByIdQuery;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;
import com.project.cadmus_challenge.unit.usecases.UseCaseUnitTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ArtistFindByIdQueryTests extends UseCaseUnitTests {
    @Mock
    private IArtistRepository repositoryMock;

    private ArtistFindByIdQuery query;

    private final Long id = 1L;

    @BeforeEach
    public void setup() {
        this.query = new ArtistFindByIdQuery(this.id);
        this.query.set_repository(this.repositoryMock);
    }

    @Test
    public void shouldReturnArtistWhenIdExistsTest() {
        var artist = new Artist(
                this.id,
                "Maria",
                "Brazilian",
                "https://www.maria.com.br",
                "Maria profile image",
                null
        );
        when(this.repositoryMock.findById(this.id)).thenReturn(Optional.of(artist));

        var result = super.facade.execute(this.query);

        assertNotNull(result);
        assertEquals(artist.getId(), result.getId());
        assertEquals(artist.getName(), result.getName());
        assertEquals(artist.getNationality(), result.getNationality());
        assertEquals(artist.getWebsiteAddress(), result.getWebsiteAddress());
        assertEquals(artist.getProfileImage(), result.getProfileImage());
        assertEquals(artist.getAlbums(), result.getAlbums());

        verify(this.repositoryMock, times(1)).findById(this.id);
    }

    @Test
    public void shouldThrowExceptionForNonExistentIdErrorTest() {
        when(this.repositoryMock.findById(this.id)).thenReturn(Optional.empty());

        var exception = assertThrows(
                UnexpectedUseCaseException.class,
                () -> super.facade.execute(this.query)
        );
        assertEquals(
                "Error executing use case ArtistFindByIdQuery: Entity not found exception: Artist ID " + this.id + " not found in the system.",
                exception.getMessage()
        );

        verify(this.repositoryMock, times(1)).findById(this.id);
    }
}
