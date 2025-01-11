package com.project.cadmus_challenge.core.usecases.artist;

import com.project.cadmus_challenge.core.bases.BusinessException;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;

import java.net.MalformedURLException;

import static com.project.cadmus_challenge.core.bases.UseCaseUtils.validateArtistId;

public final class ArtistUtils {

    private ArtistUtils() {
        // Prevent instantiation
    }

    public static void prepareEntity(Long artistId, Artist artist, IArtistRepository artistRepository) {
        if (artistId != null) {
            validateArtistId(artistId, artistRepository);
        }
        artist.setId(artistId);
        artist.setName(artist.getName().trim());
        artist.setNationality(artist.getNationality().trim());
        artist.setWebsiteAddress(artist.getWebsiteAddress().trim());
        artist.setProfileImage(artist.getProfileImage().trim());
    }

    public static void validateBusinessRules(Artist artist) {
        validateWebsiteAddress(artist);
    }

    private static void validateWebsiteAddress(Artist artist) {
        if (!isValidUrl(artist.getWebsiteAddress())) {
            throw new BusinessException("Artist website address must be a valid URL.");
        }
    }

    /**
     * Validates if a given string is a well-formed URL.
     *
     * @param url The URL string to validate.
     * @return True if the URL is valid, false otherwise.
     */
    private static Boolean isValidUrl(String url) {
        try {
            new java.net.URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
