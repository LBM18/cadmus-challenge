package com.project.cadmus_challenge.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_music")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_music")
    private Long id;

    @NotBlank(message = "{notBlank.music.title}")
    @Column(name = "tx_title", nullable = false, length = 200)
    private String title;

    @NotNull(message = "{notNull.music.duration}")
    @Column(name = "nb_duration", nullable = false)
    private Long duration; // Seconds

    @NotNull(message = "{notNull.music.track}")
    @Check(constraints = "nb_track > 0")
    @Column(name = "nb_track", nullable = false)
    private Long track;

    @NotNull(message = "{notNull.music.album}")
    @ManyToOne
    @JoinColumn(name = "id_album", referencedColumnName = "id_album", nullable = false)
    private Album album;
}
