package com.project.cadmus_challenge.core.usecases.artist.querys;

import com.project.cadmus_challenge.core.bases.UseCase;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import static com.project.cadmus_challenge.core.bases.UseCaseUtils.createPageRequest;

@RequiredArgsConstructor
public class ArtistFindAllQuery extends UseCase<Page<Artist>> {
    @Setter
    @Autowired
    private IArtistRepository _repository;

    private final Integer page;
    private final Integer size;

    @Override
    protected Page<Artist> execute() {
        return _repository.findAll(createPageRequest(this.page, this.size));
    }
}
