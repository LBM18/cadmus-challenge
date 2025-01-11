package com.project.cadmus_challenge.core.usecases.album.querys;

import com.project.cadmus_challenge.core.bases.UseCase;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.persistences.IAlbumRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import static com.project.cadmus_challenge.core.bases.UseCaseUtils.createPageRequest;

@RequiredArgsConstructor
public class AlbumFindAllQuery extends UseCase<Page<Album>> {
    @Setter
    @Autowired
    private IAlbumRepository _repository;

    private final Integer page;
    private final Integer size;

    @Override
    protected Page<Album> execute() {
        return _repository.findAll(createPageRequest(this.page, this.size));
    }
}
