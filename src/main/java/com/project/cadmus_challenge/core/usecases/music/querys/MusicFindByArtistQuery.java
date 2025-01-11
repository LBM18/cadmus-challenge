package com.project.cadmus_challenge.core.usecases.music.querys;

import com.project.cadmus_challenge.core.bases.UseCase;
import com.project.cadmus_challenge.domain.models.Music;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;
import com.project.cadmus_challenge.domain.persistences.IMusicRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.project.cadmus_challenge.core.bases.UseCaseUtils.validateArtistId;

@RequiredArgsConstructor
public class MusicFindByArtistQuery extends UseCase<List<Music>> {
    @Setter
    @Autowired
    private IArtistRepository _artistRepository;

    @Setter
    @Autowired
    private IMusicRepository _musicRepository;

    @NotNull(message = "{notNull.artist.id}")
    private final Long artistId;

    @Override
    protected List<Music> execute() {
        validateArtistId(this.artistId, _artistRepository);
        return _musicRepository.findByAlbumArtistId(this.artistId);
    }
}
