package com.project.cadmus_challenge.unit.usecases.album.querys;

import com.project.cadmus_challenge.core.bases.UnexpectedUseCaseException;
import com.project.cadmus_challenge.core.usecases.album.querys.AlbumFindByIdQuery;
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

public class AlbumFindByIdQueryTests extends UseCaseUnitTests {
    @Mock
    private IAlbumRepository repositoryMock;

    private AlbumFindByIdQuery query;

    private final Long id = 1L;

    @BeforeEach
    public void setup() {
        this.query = new AlbumFindByIdQuery(this.id);
        this.query.set_repository(this.repositoryMock);
    }

    @Test
    public void shouldReturnAlbumWhenIdExistsTest() {
        var album = new Album(
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
        when(this.repositoryMock.findById(this.id)).thenReturn(Optional.of(album));

        var result = super.facade.execute(this.query);

        assertNotNull(result);
        assertEquals(album.getId(), result.getId());
        assertEquals(album.getTitle(), result.getTitle());
        assertEquals(album.getReleaseYear(), result.getReleaseYear());
        assertEquals(album.getCoverImage(), result.getCoverImage());
        assertEquals(album.getArtist(), result.getArtist());
        assertEquals(album.getMusics(), result.getMusics());

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
                "Error executing use case AlbumFindByIdQuery: Entity not found exception: Album ID " + this.id + " not found in the system.",
                exception.getMessage()
        );

        verify(this.repositoryMock, times(1)).findById(this.id);
    }
}
