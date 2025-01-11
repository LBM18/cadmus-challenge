package com.project.cadmus_challenge.core.usecases.album;

import com.project.cadmus_challenge.core.bases.BusinessException;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.persistences.IAlbumRepository;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;

import java.time.LocalDate;

import static com.project.cadmus_challenge.core.bases.UseCaseUtils.validateAlbumId;
import static com.project.cadmus_challenge.core.bases.UseCaseUtils.validateArtistId;

public final class AlbumUtils {

    private AlbumUtils() {
        // Prevent instantiation
    }

    public static void prepareEntity(Long albumId, Album album, Long artistId, IAlbumRepository albumRepository, IArtistRepository artistRepository) {
        if (albumId != null) {
            validateAlbumId(albumId, albumRepository);
        }
        album.setId(albumId);
        album.setTitle(album.getTitle().trim());
        album.setCoverImage(album.getCoverImage().trim());
        album.setArtist(validateArtistId(artistId, artistRepository));
    }

    public static void validateBusinessRules(Album album) {
        validateReleaseYear(album);
    }

    private static void validateReleaseYear(Album album) {
        if (album.getReleaseYear() > LocalDate.now().getYear()) {
            throw new BusinessException("Album release year must not be a date later than the current one.");
        }
    }
}
