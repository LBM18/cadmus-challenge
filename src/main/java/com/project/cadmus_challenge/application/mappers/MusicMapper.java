package com.project.cadmus_challenge.application.mappers;

import com.project.cadmus_challenge.application.bases.DurationMusic;
import com.project.cadmus_challenge.application.dtos.MusicInputDto;
import com.project.cadmus_challenge.application.dtos.MusicOutputDto;
import com.project.cadmus_challenge.domain.models.Music;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MusicMapper implements IMusicMapper {
    private final IAlbumMapper _mapper;

    @Override
    public MusicOutputDto toDto(Music entity, Boolean isMappingAlbum) {
        if (entity == null) {
            return null;
        }
        return new MusicOutputDto(
                entity.getId(),
                entity.getTitle(),
                new DurationMusic(entity.getDuration()),
                entity.getTrack(),
                entity.getAlbum().getId(),
                isMappingAlbum ? _mapper.toDto(entity.getAlbum()) : null
        );
    }

    @Override
    public Music toEntity(MusicInputDto dto) {
        if (dto == null) {
            return null;
        }
        return new Music(
                null, // ID will be generated automatically
                dto.title(),
                dto.duration().toTotalSeconds(),
                dto.track(),
                null // Album ID will be assigned directly in the Service
        );
    }
}
