package com.project.cadmus_challenge.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlbumInputDto(
        @NotBlank(message = "{notBlank.album.title}")
        String title,

        @NotNull(message = "{notNull.album.releaseYear}")
        Long releaseYear,

        @NotBlank(message = "{notBlank.album.coverImage}")
        String coverImage,

        @NotNull(message = "{notNull.artist.id}")
        Long artistId
) {}
