package com.project.cadmus_challenge.unit.usecases.music.commands;

import com.project.cadmus_challenge.core.bases.UnexpectedUseCaseException;
import com.project.cadmus_challenge.core.usecases.music.commands.MusicDeleteCommand;
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

public class MusicDeleteCommandTests extends UseCaseUnitTests {
    @Mock
    private IMusicRepository repositoryMock;

    private MusicDeleteCommand command;

    private final Long id = 1L;

    private Music music;

    @BeforeEach
    public void setup() {
        this.music = new Music(
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

        this.command = new MusicDeleteCommand(this.id);
        this.command.set_repository(this.repositoryMock);

        when(this.repositoryMock.findById(this.id)).thenReturn(Optional.of(this.music));
    }

    @Test
    public void shouldDeleteMusicAndReturnEntityTest() {
        var result = super.facade.execute(this.command);

        assertNotNull(result);
        assertEquals(this.music.getId(), result.getId());
        assertEquals(this.music.getTitle(), result.getTitle());
        assertEquals(this.music.getDuration(), result.getDuration());
        assertEquals(this.music.getTrack(), result.getTrack());
        assertEquals(this.music.getAlbum(), result.getAlbum());

        verify(this.repositoryMock, times(1)).findById(this.id);
        verify(this.repositoryMock, times(1)).delete(this.music);
    }

    @Test
    void shouldThrowsExceptionForNonExistentIdErrorTest() {
        when(this.repositoryMock.findById(this.id)).thenReturn(Optional.empty());

        var exception = assertThrows(
                UnexpectedUseCaseException.class, () -> super.facade.execute(this.command)
        );
        assertEquals(
                "Error executing use case MusicDeleteCommand: Entity not found exception: Music ID " + this.id + " not found in the system.",
                exception.getMessage()
        );

        verify(this.repositoryMock, times(1)).findById(this.id);
        verify(this.repositoryMock, times(0)).delete(this.music);
    }
}
