package com.project.cadmus_challenge.unit.usecases.artist.querys;

import com.project.cadmus_challenge.core.usecases.artist.querys.ArtistFindAllQuery;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.persistences.IArtistRepository;
import com.project.cadmus_challenge.unit.usecases.UseCaseUnitTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ArtistFindAllQueryTests extends UseCaseUnitTests {
    @Mock
    private IArtistRepository repositoryMock;

    private ArtistFindAllQuery query;

    private final Integer page = 0;
    private final Integer size = 5;

    @BeforeEach
    public void setup() {
        this.query = new ArtistFindAllQuery(this.page, this.size);
        this.query.set_repository(this.repositoryMock);
    }

    @Test
    public void shouldReturnPaginatedArtistsTest() {
        var artists = List.of(
                new Artist(
                        1L,
                        "Ana",
                        "Brazilian",
                        "https://www.ana.com.br",
                        "Ana profile image",
                        null
                ),
                new Artist(
                        2L,
                        "Maria",
                        "Brazilian",
                        "https://www.maria.com.br",
                        "Maria profile image",
                        null
                )
        );
        var pageRequest = PageRequest.of(this.page, this.size);
        var paginatedArtists = new PageImpl<>(artists, pageRequest, artists.size());

        when(this.repositoryMock.findAll(any(PageRequest.class))).thenReturn(paginatedArtists);

        var result = super.facade.execute(this.query);

        assertNotNull(result);
        assertEquals(artists.size(), result.getTotalElements());
        assertEquals(artists.get(0).getId(), result.getContent().get(0).getId());
        assertEquals(artists.get(0).getName(), result.getContent().get(0).getName());
        assertEquals(artists.get(0).getNationality(), result.getContent().get(0).getNationality());
        assertEquals(artists.get(0).getWebsiteAddress(), result.getContent().get(0).getWebsiteAddress());
        assertEquals(artists.get(0).getProfileImage(), result.getContent().get(0).getProfileImage());
        assertEquals(artists.get(0).getAlbums(), result.getContent().get(0).getAlbums());
        assertEquals(artists.get(1).getId(), result.getContent().get(1).getId());
        assertEquals(artists.get(1).getName(), result.getContent().get(1).getName());
        assertEquals(artists.get(1).getNationality(), result.getContent().get(1).getNationality());
        assertEquals(artists.get(1).getWebsiteAddress(), result.getContent().get(1).getWebsiteAddress());
        assertEquals(artists.get(1).getProfileImage(), result.getContent().get(1).getProfileImage());
        assertEquals(artists.get(1).getAlbums(), result.getContent().get(1).getAlbums());

        verify(this.repositoryMock, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void shouldReturnEmptyPageWhenNoDataExistsTest() {
        var pageRequest = PageRequest.of(this.page, this.size);
        var emptyPage = new PageImpl<Artist>(List.of(), pageRequest, 0);

        when(this.repositoryMock.findAll(any(PageRequest.class))).thenReturn(emptyPage);

        var result = super.facade.execute(this.query);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getContent().size());

        verify(this.repositoryMock, times(1)).findAll(any(PageRequest.class));
    }
}
