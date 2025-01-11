package com.project.cadmus_challenge.core.usecases.album.commands;

import com.project.cadmus_challenge.core.bases.UseCase;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.persistences.IAlbumRepository;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import static com.project.cadmus_challenge.core.usecases.album.AlbumUtils.prepareEntity;
import static com.project.cadmus_challenge.core.usecases.album.AlbumUtils.validateBusinessRules;

@RequiredArgsConstructor
public class AlbumEditCommand extends UseCase<Album> {
    @Setter
    @Autowired
    private IArtistRepository _artistRepository;

    @Setter
    @Autowired
    private IAlbumRepository _albumRepository;

    @NotNull(message = "{notNull.album.entity}")
    private final Album entity;

    @NotNull(message = "{notNull.album.id}")
    private final Long albumId;

    @NotNull(message = "{notNull.artist.id}")
    private final Long artistId;

    @Override
    protected Album execute() {
        prepareEntity(this.albumId, this.entity, this.artistId, _albumRepository, _artistRepository);
        validateBusinessRules(this.entity);
        return _albumRepository.save(this.entity);
    }
}
