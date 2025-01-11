package com.project.cadmus_challenge.application.dtos;

public record ArtistOutputDto(
        Long id,
        String name,
        String nationality,
        String websiteAddress,
        String profileImage
) {}
