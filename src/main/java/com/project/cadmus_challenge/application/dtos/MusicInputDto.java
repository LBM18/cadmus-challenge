package com.project.cadmus_challenge.application.dtos;

import com.project.cadmus_challenge.application.bases.DurationMusic;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MusicInputDto(
        @NotBlank(message = "{notBlank.music.title}")
        String title,

        @NotNull(message = "{notNull.music.duration}")
        DurationMusic duration,

        @NotNull(message = "{notNull.music.track}")
        Long track,

        @NotNull(message = "{notNull.album.id}")
        Long albumId
) {}
