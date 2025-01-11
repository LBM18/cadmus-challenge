package com.project.cadmus_challenge.core.usecases.music.commands;

import com.project.cadmus_challenge.core.bases.UseCase;
import com.project.cadmus_challenge.domain.models.Music;
import com.project.cadmus_challenge.domain.persistences.IAlbumRepository;
import com.project.cadmus_challenge.domain.persistences.IMusicRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import static com.project.cadmus_challenge.core.usecases.music.MusicUtils.prepareEntity;
import static com.project.cadmus_challenge.core.usecases.music.MusicUtils.validateBusinessRules;

@RequiredArgsConstructor
public class MusicCreateCommand extends UseCase<Music> {
    @Setter
    @Autowired
    private IAlbumRepository _albumRepository;

    @Setter
    @Autowired
    private IMusicRepository _musicRepository;

    @NotNull(message = "{notNull.music.entity}")
    private final Music entity;

    @NotNull(message = "{notNull.album.id}")
    private final Long albumId;

    @Override
    protected Music execute() {
        prepareEntity(null, this.entity, this.albumId, _musicRepository, _albumRepository);
        validateBusinessRules(this.entity);
        return _musicRepository.save(this.entity);
    }
}
