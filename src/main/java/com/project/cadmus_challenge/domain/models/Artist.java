package com.project.cadmus_challenge.domain.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_artist")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artist")
    private Long id;

    @NotBlank(message = "{notBlank.artist.name}")
    @Column(name = "tx_name", nullable = false, length = 200)
    private String name;

    @NotBlank(message = "{notBlank.artist.nationality}")
    @Column(name = "tx_nationality", nullable = false, length = 100)
    private String nationality;

    @NotBlank(message = "{notBlank.artist.websiteAddress}")
    @Column(name = "tx_website_address", nullable = false, length = 300)
    private String websiteAddress;

    @NotBlank(message = "{notBlank.artist.profileImage}")
    @Column(name = "tx_profile_image", nullable = false, length = 300)
    private String profileImage;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Album> albums = new ArrayList<>();
}
