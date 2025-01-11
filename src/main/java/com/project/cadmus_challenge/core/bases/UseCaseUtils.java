package com.project.cadmus_challenge.core.bases;

import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.models.Music;
import com.project.cadmus_challenge.domain.persistences.IAlbumRepository;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;
import com.project.cadmus_challenge.domain.persistences.IMusicRepository;
import org.springframework.data.domain.PageRequest;

public final class UseCaseUtils {
    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_SIZE = 10;

    private UseCaseUtils() {
        // Prevent instantiation
    }

    /**
     * Creates a PageRequest with the given page and size, or default values if invalid.
     *
     * @param page The requested page number (zero-based), defaults to 0 if null or negative.
     * @param size The requested page size, defaults to 10 if null or less than 1.
     * @return A PageRequest object.
     */
    public static PageRequest createPageRequest(Integer page, Integer size) {
        var currentPage = (page != null && page >= 0) ? page : DEFAULT_PAGE;
        var currentSize = (size != null && size > 0) ? size : DEFAULT_SIZE;

        return PageRequest.of(currentPage, currentSize);
    }

    public static Artist validateArtistId(Long artistId, IArtistRepository artistRepository) {
        return artistRepository.findById(artistId).orElseThrow(() -> new EntityNotFoundException("Artist ID " + artistId + " not found in the system."));
    }

    public static Album validateAlbumId(Long albumId, IAlbumRepository albumRepository) {
        return albumRepository.findById(albumId).orElseThrow(() -> new EntityNotFoundException("Album ID " + albumId + " not found in the system."));
    }

    public static Music validateMusicId(Long musicId, IMusicRepository musicRepository) {
        return musicRepository.findById(musicId).orElseThrow(() -> new EntityNotFoundException("Music ID " + musicId + " not found in the system."));
    }
}
