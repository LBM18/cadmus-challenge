package com.project.cadmus_challenge.domain.persistences;

import com.project.cadmus_challenge.domain.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAlbumRepository extends JpaRepository<Album, Long> {
}
