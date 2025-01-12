package com.project.cadmus_challenge.application.services;

import com.project.cadmus_challenge.application.dtos.ArtistInputDto;
import com.project.cadmus_challenge.application.dtos.ArtistOutputDto;
import com.project.cadmus_challenge.application.mappers.IArtistMapper;
import com.project.cadmus_challenge.core.bases.UseCaseFacade;
import com.project.cadmus_challenge.core.usecases.artist.commands.ArtistCreateCommand;
import com.project.cadmus_challenge.core.usecases.artist.commands.ArtistDeleteCommand;
import com.project.cadmus_challenge.core.usecases.artist.commands.ArtistEditCommand;
import com.project.cadmus_challenge.core.usecases.artist.querys.ArtistFindAllQuery;
import com.project.cadmus_challenge.core.usecases.artist.querys.ArtistFindByIdQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistService implements IArtistService {
    private final UseCaseFacade facade;
    private final IArtistMapper _mapper;

    @Override
    public ArtistOutputDto create(ArtistInputDto dto) {
        var entity = _mapper.toEntity(dto);
        var result = this.facade.execute(new ArtistCreateCommand(entity));
        return _mapper.toDto(result);
    }

    @Override
    public ArtistOutputDto update(Long id, ArtistInputDto dto) {
        var entity = _mapper.toEntity(dto);
        var result = this.facade.execute(new ArtistEditCommand(entity, id));
        return _mapper.toDto(result);
    }

    @Override
    public ArtistOutputDto delete(Long id) {
        var result = this.facade.execute(new ArtistDeleteCommand(id));
        return _mapper.toDto(result);
    }

    @Override
    public ArtistOutputDto findById(Long id) {
        var result = this.facade.execute(new ArtistFindByIdQuery(id));
        return _mapper.toDto(result);
    }

    @Override
    public Page<ArtistOutputDto> findAll(Integer page, Integer size) {
        var result = this.facade.execute(new ArtistFindAllQuery(page, size));
        return result.map(_mapper::toDto);
    }
}
