package com.project.cadmus_challenge.core.usecases.music;

import com.project.cadmus_challenge.core.bases.BusinessException;
import com.project.cadmus_challenge.domain.models.Music;
import com.project.cadmus_challenge.domain.persistences.IAlbumRepository;
import com.project.cadmus_challenge.domain.persistences.IMusicRepository;

import static com.project.cadmus_challenge.core.bases.UseCaseUtils.validateAlbumId;
import static com.project.cadmus_challenge.core.bases.UseCaseUtils.validateMusicId;

public final class MusicUtils {

    private MusicUtils() {
        // Prevent instantiation
    }

    public static void prepareEntity(Long musicId, Music music, Long albumId, IMusicRepository musicRepository, IAlbumRepository albumRepository) {
        if (musicId != null) {
            validateMusicId(musicId, musicRepository);
        }
        music.setId(musicId);
        music.setTitle(music.getTitle().trim());
        music.setAlbum(validateAlbumId(albumId, albumRepository));
    }

    public static void validateBusinessRules(Music music) {
        validateTrack(music);
        validateDuration(music);
    }

    private static void validateTrack(Music music) {
        if (music.getTrack() <= 0) {
            throw new BusinessException("Music track number must be greater than zero.");
        }
    }

    private static void validateDuration(Music music) {
        if (music.getDuration() < 1) {
            throw new BusinessException("Music duration must represent valid minutes and seconds.");
        }
    }
}
