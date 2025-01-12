package com.project.cadmus_challenge.core.usecases.album.commands;

import com.project.cadmus_challenge.core.bases.UseCase;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.persistences.IAlbumRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import static com.project.cadmus_challenge.core.bases.UseCaseUtils.validateAlbumId;

@RequiredArgsConstructor
public class AlbumDeleteCommand extends UseCase<Album> {
    @Setter
    @Autowired
    private IAlbumRepository _repository;

    @NotNull(message = "{notNull.album.id}")
    private final Long id;

    @Override
    protected Album execute() {
        var entity = validateAlbumId(this.id, _repository);
        _repository.delete(entity);
        return entity;
    }
}
