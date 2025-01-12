package com.project.cadmus_challenge.api.bases;

import com.project.cadmus_challenge.application.dtos.ArtistInputDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ArtistInputWrapper(
        @NotBlank(message = "{notBlank.artist.name}")
        String name,

        @NotBlank(message = "{notBlank.artist.nationality}")
        String nationality,

        @NotBlank(message = "{notBlank.artist.websiteAddress}")
        String websiteAddress,

        @NotNull(message = "{notBlank.artist.profileImage}")
        MultipartFile profileImage
) {
    public ArtistInputDto toInputDto() {
        return new ArtistInputDto(
                this.name,
                this.nationality,
                this.websiteAddress,
                FileStorageHandler.getFileNameFromMultipartFile(this.profileImage)
        );
    }
}
