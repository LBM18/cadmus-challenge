package com.project.cadmus_challenge.application.dtos;

import com.project.cadmus_challenge.application.bases.DurationMusic;

public record MusicOutputDto(
        Long id,
        String title,
        DurationMusic duration,
        Long track,
        Long albumId,
        AlbumOutputDto album
) {}
