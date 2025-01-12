package com.project.cadmus_challenge.api.bases;

import com.project.cadmus_challenge.application.dtos.AlbumInputDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record AlbumInputWrapper(
        @NotBlank(message = "{notBlank.album.title}")
        String title,

        @NotNull(message = "{notNull.album.releaseYear}")
        Long releaseYear,

        @NotNull(message = "{notBlank.album.coverImage}")
        MultipartFile coverImage,

        @NotNull(message = "{notNull.artist.id}")
        Long artistId
) {
        public AlbumInputDto toInputDto() {
                return new AlbumInputDto(
                        this.title,
                        this.releaseYear,
                        FileStorageHandler.getFileNameFromMultipartFile(this.coverImage),
                        this.artistId
                );
        }
}
