package com.project.cadmus_challenge.application.services;

import com.project.cadmus_challenge.application.dtos.AlbumInputDto;
import com.project.cadmus_challenge.application.dtos.AlbumOutputDto;
import com.project.cadmus_challenge.application.mappers.IAlbumMapper;
import com.project.cadmus_challenge.core.bases.UseCaseFacade;
import com.project.cadmus_challenge.core.usecases.album.commands.AlbumCreateCommand;
import com.project.cadmus_challenge.core.usecases.album.commands.AlbumDeleteCommand;
import com.project.cadmus_challenge.core.usecases.album.commands.AlbumEditCommand;
import com.project.cadmus_challenge.core.usecases.album.querys.AlbumFindAllQuery;
import com.project.cadmus_challenge.core.usecases.album.querys.AlbumFindByIdQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlbumService implements IAlbumService {
    private final UseCaseFacade facade;
    private final IAlbumMapper _mapper;

    @Override
    public AlbumOutputDto create(AlbumInputDto dto) {
        var entity = _mapper.toEntity(dto);
        var result = this.facade.execute(new AlbumCreateCommand(entity, dto.artistId()));
        return _mapper.toDto(result);
    }

    @Override
    public AlbumOutputDto update(Long id, AlbumInputDto dto) {
        var entity = _mapper.toEntity(dto);
        var result = this.facade.execute(new AlbumEditCommand(entity, id, dto.artistId()));
        return _mapper.toDto(result);
    }

    @Override
    public AlbumOutputDto delete(Long id) {
        var result = this.facade.execute(new AlbumDeleteCommand(id));
        return _mapper.toDto(result);
    }

    @Override
    public AlbumOutputDto findById(Long id) {
        var result = this.facade.execute(new AlbumFindByIdQuery(id));
        return _mapper.toDto(result);
    }

    @Override
    public Page<AlbumOutputDto> findAll(Integer page, Integer size) {
        var result = this.facade.execute(new AlbumFindAllQuery(page, size));
        return result.map(_mapper::toDto);
    }
}
