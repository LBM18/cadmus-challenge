package com.project.cadmus_challenge.domain.persistences;

import com.project.cadmus_challenge.domain.models.Music;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMusicRepository extends JpaRepository<Music, Long> {
    List<Music> findByAlbumArtistId(Long artistId);
    List<Music> findByAlbumId(Long albumId, Sort sort);
}
