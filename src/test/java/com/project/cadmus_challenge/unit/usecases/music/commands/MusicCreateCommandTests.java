package com.project.cadmus_challenge.unit.usecases.music.commands;

import com.project.cadmus_challenge.core.bases.UnexpectedUseCaseException;
import com.project.cadmus_challenge.core.usecases.music.commands.MusicCreateCommand;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.models.Music;
import com.project.cadmus_challenge.domain.persistences.IAlbumRepository;
import com.project.cadmus_challenge.domain.persistences.IMusicRepository;
import com.project.cadmus_challenge.unit.usecases.UseCaseUnitTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MusicCreateCommandTests extends UseCaseUnitTests {
    @Mock
    private IAlbumRepository albumRepositoryMock;

    @Mock
    private IMusicRepository musicRepositoryMock;

    private MusicCreateCommand command;

    private Music music;

    private Album album;

    private final Long albumId = 1L;

    @BeforeEach
    public void setup() {
        this.album = new Album(
                this.albumId,
                "  Love songs  ",
                2024L,
                "  Love songs cover image  ",
                null,
                null
        );
        this.music = new Music(
                null,
                "  Imagine  ",
                120L,
                1L,
                this.album
        );
        this.command = new MusicCreateCommand(this.music, this.albumId);
        this.command.set_albumRepository(this.albumRepositoryMock);
        this.command.set_musicRepository(this.musicRepositoryMock);
    }

    @Test
    public void shouldCreateMusicTest() {
        when(this.albumRepositoryMock.findById(this.albumId)).thenReturn(Optional.of(this.album));
        when(this.musicRepositoryMock.save(any(Music.class))).thenReturn(this.music);

        var result = super.facade.execute(this.command);

        assertNotNull(result);
        assertEquals(this.music.getId(), result.getId());
        assertEquals(this.music.getTitle().trim(), result.getTitle());
        assertEquals(this.music.getDuration(), result.getDuration());
        assertEquals(this.music.getTrack(), result.getTrack());
        assertEquals(this.music.getAlbum(), result.getAlbum());

        verify(this.albumRepositoryMock, times(1)).findById(this.albumId);
        verify(this.musicRepositoryMock, times(1)).save(any(Music.class));
    }

    @Test
    void shouldThrowsExceptionForNonExistentAlbumIdErrorTest() {
        when(this.albumRepositoryMock.findById(this.albumId)).thenReturn(Optional.empty());

        var exception = assertThrows(
                UnexpectedUseCaseException.class, () -> super.facade.execute(this.command)
        );
        assertEquals(
                "Error executing use case MusicCreateCommand: Entity not found exception: Album ID " + this.albumId + " not found in the system.",
                exception.getMessage()
        );

        verify(this.musicRepositoryMock, times(0)).save(any(Music.class));
    }

    @Test
    public void shouldThrowsExceptionWithInvalidTrackErrorTest() {
        when(this.albumRepositoryMock.findById(this.albumId)).thenReturn(Optional.of(this.album));

        this.music.setTrack(0L);

        var exception = assertThrows(
                UnexpectedUseCaseException.class, () -> super.facade.execute(this.command)
        );
        assertEquals(
                "Error executing use case MusicCreateCommand: Business exception: Music track number must be greater than zero.",
                exception.getMessage()
        );

        verify(this.musicRepositoryMock, times(0)).save(any(Music.class));
    }

    @Test
    public void shouldThrowsExceptionWithInvalidDurationErrorTest() {
        when(this.albumRepositoryMock.findById(this.albumId)).thenReturn(Optional.of(this.album));

        this.music.setDuration(0L);

        var exception = assertThrows(
                UnexpectedUseCaseException.class, () -> super.facade.execute(this.command)
        );
        assertEquals(
                "Error executing use case MusicCreateCommand: Business exception: Music duration must represent valid minutes and seconds.",
                exception.getMessage()
        );

        verify(this.musicRepositoryMock, times(0)).save(any(Music.class));
    }
}
