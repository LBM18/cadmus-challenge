package com.project.cadmus_challenge.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_album")
    private Long id;

    @NotBlank(message = "{notBlank.album.title}")
    @Column(name = "tx_title", nullable = false, length = 200)
    private String title;

    @NotNull(message = "{notNull.album.releaseYear}")
    @Check(constraints = "nb_release_year <= EXTRACT(YEAR FROM CURRENT_DATE)")
    @Column(name = "nb_release_year", nullable = false)
    private Long releaseYear;

    @NotBlank(message = "{notBlank.album.coverImage}")
    @Column(name = "tx_cover_image", nullable = false, length = 300)
    private String coverImage;

    @NotNull(message = "{notNull.album.artist}")
    @ManyToOne
    @JoinColumn(name = "id_artist", referencedColumnName = "id_artist", nullable = false)
    private Artist artist;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Music> musics = new ArrayList<>();
}
