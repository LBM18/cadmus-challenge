package com.project.cadmus_challenge.core.usecases.artist.commands;

import com.project.cadmus_challenge.core.bases.UseCase;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import static com.project.cadmus_challenge.core.usecases.artist.ArtistUtils.prepareEntity;
import static com.project.cadmus_challenge.core.usecases.artist.ArtistUtils.validateBusinessRules;

@RequiredArgsConstructor
public class ArtistEditCommand extends UseCase<Artist> {
    @Setter
    @Autowired
    private IArtistRepository _repository;

    @NotNull(message = "{notNull.artist.entity}")
    private final Artist entity;

    @NotNull(message = "{notNull.artist.id}")
    private final Long id;

    @Override
    protected Artist execute() {
        prepareEntity(id, this.entity, _repository);
        validateBusinessRules(this.entity);
        return _repository.save(this.entity);
    }
}