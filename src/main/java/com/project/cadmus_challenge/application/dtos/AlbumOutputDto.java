package com.project.cadmus_challenge.application.dtos;

public record AlbumOutputDto(
        Long id,
        String title,
        Long releaseYear,
        String coverImage,
        Long artistId
) {}
