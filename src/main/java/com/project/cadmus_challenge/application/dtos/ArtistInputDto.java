package com.project.cadmus_challenge.application.dtos;

import jakarta.validation.constraints.NotBlank;

public record ArtistInputDto(
        @NotBlank(message = "{notBlank.artist.name}")
        String name,

        @NotBlank(message = "{notBlank.artist.nationality}")
        String nationality,

        @NotBlank(message = "{notBlank.artist.websiteAddress}")
        String websiteAddress,

        @NotBlank(message = "{notBlank.artist.profileImage}")
        String profileImage
) {}
