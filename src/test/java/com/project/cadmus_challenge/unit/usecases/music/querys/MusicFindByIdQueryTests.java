package com.project.cadmus_challenge.unit.usecases.music.querys;

import com.project.cadmus_challenge.core.bases.UnexpectedUseCaseException;
import com.project.cadmus_challenge.core.usecases.music.querys.MusicFindByIdQuery;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.models.Music;
import com.project.cadmus_challenge.domain.persistences.IMusicRepository;
import com.project.cadmus_challenge.unit.usecases.UseCaseUnitTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MusicFindByIdQueryTests extends UseCaseUnitTests {
    @Mock
    private IMusicRepository repositoryMock;

    private MusicFindByIdQuery query;

    private final Long id = 1L;

    @BeforeEach
    public void setup() {
        this.query = new MusicFindByIdQuery(this.id);
        this.query.set_repository(this.repositoryMock);
    }

    @Test
    public void shouldReturnMusicWhenIdExistsTest() {
        var music = new Music(
                this.id,
                "Imagine",
                120L,
                1L,
                new Album(
                        1L,
                        "Love songs",
                        2024L,
                        "Love songs cover image",
                        null,
                        null
                )
        );
        when(this.repositoryMock.findById(this.id)).thenReturn(Optional.of(music));

        var result = super.facade.execute(this.query);

        assertNotNull(result);
        assertEquals(music.getId(), result.getId());
        assertEquals(music.getTitle(), result.getTitle());
        assertEquals(music.getDuration(), result.getDuration());
        assertEquals(music.getTrack(), result.getTrack());
        assertEquals(music.getAlbum(), result.getAlbum());

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
                "Error executing use case MusicFindByIdQuery: Entity not found exception: Music ID " + this.id + " not found in the system.",
                exception.getMessage()
        );

        verify(this.repositoryMock, times(1)).findById(this.id);
    }
}
