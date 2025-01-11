package com.project.cadmus_challenge.core.usecases.music.querys;

import com.project.cadmus_challenge.core.bases.UseCase;
import com.project.cadmus_challenge.domain.models.Music;
import com.project.cadmus_challenge.domain.persistences.IAlbumRepository;
import com.project.cadmus_challenge.domain.persistences.IMusicRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

import static com.project.cadmus_challenge.core.bases.UseCaseUtils.validateAlbumId;

@RequiredArgsConstructor
public class MusicFindByAlbumQuery  extends UseCase<List<Music>> {
    @Setter
    @Autowired
    private IAlbumRepository _albumRepository;

    @Setter
    @Autowired
    private IMusicRepository _musicRepository;

    @NotNull(message = "{notNull.album.id}")
    private final Long albumId;

    private final Boolean isSortByTrack;

    @Override
    protected List<Music> execute() {
        validateAlbumId(this.albumId, _albumRepository);

        var sortField = this.isSortByTrack ? "track" : "title";
        var sort = Sort.by(Sort.Direction.ASC, sortField);

        return _musicRepository.findByAlbumId(this.albumId, sort);
    }
}
