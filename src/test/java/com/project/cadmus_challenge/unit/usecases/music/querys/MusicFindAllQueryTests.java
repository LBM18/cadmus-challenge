package com.project.cadmus_challenge.unit.usecases.music.querys;

import com.project.cadmus_challenge.core.usecases.music.querys.MusicFindAllQuery;
import com.project.cadmus_challenge.domain.models.Album;
import com.project.cadmus_challenge.domain.models.Music;
import com.project.cadmus_challenge.domain.persistences.IMusicRepository;
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

public class MusicFindAllQueryTests extends UseCaseUnitTests {
    @Mock
    private IMusicRepository repositoryMock;

    private MusicFindAllQuery query;

    private final Integer page = 0;
    private final Integer size = 5;

    @BeforeEach
    public void setup() {
        this.query = new MusicFindAllQuery(this.page, this.size);
        this.query.set_repository(this.repositoryMock);
    }

    @Test
    public void shouldReturnPaginatedMusicsTest() {
        var musics = List.of(
                new Music(
                        1L,
                        "Imagine",
                        120L,
                        1L,
                        new Album(
                                1L,
                                "Love songs",
                                2024L,
                                "Love songs cover image",
                                null,
                                null
                        )
                ),
                new Music(
                        2L,
                        "Be happy",
                        90L,
                        2L,
                        new Album(
                                2L,
                                "Drama songs",
                                2000L,
                                "Drama songs cover image",
                                null,
                                null
                        )
                )
        );
        var pageRequest = PageRequest.of(this.page, this.size);
        var paginatedMusics = new PageImpl<>(musics, pageRequest, musics.size());

        when(this.repositoryMock.findAll(any(PageRequest.class))).thenReturn(paginatedMusics);

        var result = super.facade.execute(this.query);

        assertNotNull(result);
        assertEquals(musics.size(), result.getTotalElements());
        assertEquals(musics.get(0).getId(), result.getContent().get(0).getId());
        assertEquals(musics.get(0).getTitle(), result.getContent().get(0).getTitle());
        assertEquals(musics.get(0).getDuration(), result.getContent().get(0).getDuration());
        assertEquals(musics.get(0).getTrack(), result.getContent().get(0).getTrack());
        assertEquals(musics.get(0).getAlbum(), result.getContent().get(0).getAlbum());
        assertEquals(musics.get(1).getId(), result.getContent().get(1).getId());
        assertEquals(musics.get(1).getTitle(), result.getContent().get(1).getTitle());
        assertEquals(musics.get(1).getDuration(), result.getContent().get(1).getDuration());
        assertEquals(musics.get(1).getTrack(), result.getContent().get(1).getTrack());
        assertEquals(musics.get(1).getAlbum(), result.getContent().get(1).getAlbum());

        verify(this.repositoryMock, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void shouldReturnEmptyPageWhenNoDataExistsTest() {
        var pageRequest = PageRequest.of(this.page, this.size);
        var emptyPage = new PageImpl<Music>(List.of(), pageRequest, 0);

        when(this.repositoryMock.findAll(any(PageRequest.class))).thenReturn(emptyPage);

        var result = super.facade.execute(this.query);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getContent().size());

        verify(this.repositoryMock, times(1)).findAll(any(PageRequest.class));
    }
}
