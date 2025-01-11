package com.project.cadmus_challenge.domain.persistences;

import com.project.cadmus_challenge.domain.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IArtistRepository extends JpaRepository<Artist, Long> {
}
