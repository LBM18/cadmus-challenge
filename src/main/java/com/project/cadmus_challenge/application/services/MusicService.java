package com.project.cadmus_challenge.application.services;

import com.project.cadmus_challenge.application.dtos.MusicInputDto;
import com.project.cadmus_challenge.application.dtos.MusicOutputDto;
import com.project.cadmus_challenge.application.mappers.IMusicMapper;
import com.project.cadmus_challenge.core.bases.UseCaseFacade;
import com.project.cadmus_challenge.core.usecases.music.commands.MusicCreateCommand;
import com.project.cadmus_challenge.core.usecases.music.commands.MusicDeleteCommand;
import com.project.cadmus_challenge.core.usecases.music.commands.MusicEditCommand;
import com.project.cadmus_challenge.core.usecases.music.querys.MusicFindAllQuery;
import com.project.cadmus_challenge.core.usecases.music.querys.MusicFindByAlbumQuery;
import com.project.cadmus_challenge.core.usecases.music.querys.MusicFindByArtistQuery;
import com.project.cadmus_challenge.core.usecases.music.querys.MusicFindByIdQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicService implements IMusicService {
    private final UseCaseFacade facade;
    private final IMusicMapper _mapper;

    @Override
    public MusicOutputDto create(MusicInputDto dto) {
        var entity = _mapper.toEntity(dto);
        var result = this.facade.execute(new MusicCreateCommand(entity, dto.albumId()));
        return _mapper.toDto(result, false);
    }

    @Override
    public MusicOutputDto update(Long id, MusicInputDto dto) {
        var entity = _mapper.toEntity(dto);
        var result = this.facade.execute(new MusicEditCommand(entity, id, dto.albumId()));
        return _mapper.toDto(result, false);
    }

    @Override
    public MusicOutputDto delete(Long id) {
        var result = this.facade.execute(new MusicDeleteCommand(id));
        return _mapper.toDto(result, false);
    }

    @Override
    public MusicOutputDto findById(Long id) {
        var result = this.facade.execute(new MusicFindByIdQuery(id));
        return _mapper.toDto(result, false);
    }

    @Override
    public Page<MusicOutputDto> findAll(Integer page, Integer size) {
        var result = this.facade.execute(new MusicFindAllQuery(page, size));
        return result.map(entity -> _mapper.toDto(entity, false));
    }

    @Override
    public List<MusicOutputDto> findByArtist(Long artistId) {
        var result = this.facade.execute(new MusicFindByArtistQuery(artistId));
        return result.stream().map(entity -> _mapper.toDto(entity, true)).collect(Collectors.toList());
    }

    @Override
    public List<MusicOutputDto> findByAlbum(Long albumId, Boolean isSortByTrack) {
        var result = this.facade.execute(new MusicFindByAlbumQuery(albumId, isSortByTrack));
        return result.stream().map(entity -> _mapper.toDto(entity, false)).collect(Collectors.toList());
    }
}
