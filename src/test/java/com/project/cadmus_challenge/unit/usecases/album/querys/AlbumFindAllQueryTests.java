package com.project.cadmus_challenge.unit.usecases.album.querys;

import com.project.cadmus_challenge.core.usecases.album.querys.AlbumFindAllQuery;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.models.Artist;
import com.project.cadmus_challenge.domain.persistences.IAlbumRepository;
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

public class AlbumFindAllQueryTests extends UseCaseUnitTests {
    @Mock
    private IAlbumRepository repositoryMock;

    private AlbumFindAllQuery query;

    private final Integer page = 0;
    private final Integer size = 5;

    @BeforeEach
    public void setup() {
        this.query = new AlbumFindAllQuery(this.page, this.size);
        this.query.set_repository(this.repositoryMock);
    }

    @Test
    public void shouldReturnPaginatedAlbumsTest() {
        var albums = List.of(
                new Album(
                        1L,
                        "Love songs",
                        2024L,
                        "Love songs cover image",
                        new Artist(
                                1L,
                                "Maria",
                                "Brazilian",
                                "https://www.maria.com.br",
                                "Maria profile image",
                                null
                        ),
                        null
                ),
                new Album(
                        2L,
                        "Rock songs",
                        2004L,
                        "Rock songs cover image",
                        new Artist(
                                2L,
                                "Ana",
                                "Brazilian",
                                "https://www.ana.com.br",
                                "Ana profile image",
                                null
                        ),
                        null
                )
        );
        var pageRequest = PageRequest.of(this.page, this.size);
        var paginatedAlbums = new PageImpl<>(albums, pageRequest, albums.size());

        when(this.repositoryMock.findAll(any(PageRequest.class))).thenReturn(paginatedAlbums);

        var result = super.facade.execute(this.query);

        assertNotNull(result);
        assertEquals(albums.size(), result.getTotalElements());
        assertEquals(albums.get(0).getId(), result.getContent().get(0).getId());
        assertEquals(albums.get(0).getTitle(), result.getContent().get(0).getTitle());
        assertEquals(albums.get(0).getReleaseYear(), result.getContent().get(0).getReleaseYear());
        assertEquals(albums.get(0).getCoverImage(), result.getContent().get(0).getCoverImage());
        assertEquals(albums.get(0).getArtist(), result.getContent().get(0).getArtist());
        assertEquals(albums.get(0).getMusics(), result.getContent().get(0).getMusics());
        assertEquals(albums.get(1).getId(), result.getContent().get(1).getId());
        assertEquals(albums.get(1).getTitle(), result.getContent().get(1).getTitle());
        assertEquals(albums.get(1).getReleaseYear(), result.getContent().get(1).getReleaseYear());
        assertEquals(albums.get(1).getCoverImage(), result.getContent().get(1).getCoverImage());
        assertEquals(albums.get(1).getArtist(), result.getContent().get(1).getArtist());
        assertEquals(albums.get(1).getMusics(), result.getContent().get(1).getMusics());

        verify(this.repositoryMock, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void shouldReturnEmptyPageWhenNoDataExistsTest() {
        var pageRequest = PageRequest.of(this.page, this.size);
        var emptyPage = new PageImpl<Album>(List.of(), pageRequest, 0);

        when(this.repositoryMock.findAll(any(PageRequest.class))).thenReturn(emptyPage);

        var result = super.facade.execute(this.query);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getContent().size());

        verify(this.repositoryMock, times(1)).findAll(any(PageRequest.class));
    }
}
