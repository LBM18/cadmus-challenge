package com.project.cadmus_challenge.core.usecases.artist.commands;

import com.project.cadmus_challenge.core.bases.UseCase;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import static com.project.cadmus_challenge.core.bases.UseCaseUtils.validateArtistId;

@RequiredArgsConstructor
public class ArtistDeleteCommand extends UseCase<Void> {
    @Setter
    @Autowired
    private IArtistRepository _repository;

    @NotNull(message = "{notNull.artist.id}")
    private final Long id;

    @Override
    protected Void execute() {
        validateArtistId(this.id, _repository);
        _repository.deleteById(this.id);
        return null;
    }
}
