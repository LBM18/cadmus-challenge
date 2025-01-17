package com.project.cadmus_challenge.core.usecases.music.querys;

import com.project.cadmus_challenge.core.bases.UseCase;
import com.project.cadmus_challenge.domain.models.Music;
import com.project.cadmus_challenge.domain.persistences.IMusicRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import static com.project.cadmus_challenge.core.bases.UseCaseUtils.validateMusicId;

@RequiredArgsConstructor
public class MusicFindByIdQuery extends UseCase<Music> {
    @Setter
    @Autowired
    private IMusicRepository _repository;

    @NotNull(message = "{notNull.music.id}")
    private final Long id;

    @Override
    protected Music execute() {
        return validateMusicId(this.id, _repository);
    }
}
