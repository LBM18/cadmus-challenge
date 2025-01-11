package com.project.cadmus_challenge.integration.music;

import com.project.cadmus_challenge.integration.ControllerIntegrationTests;
import org.junit.jupiter.api.Test;

public class MusicGetControllerIntegrationTests extends ControllerIntegrationTests {
	private static final String BASE_URI = ROOT_URI + "/music";
	private static final String GET_ALL_URI = BASE_URI + "/get-all";
	private static final String GET_BY_ID_URI = BASE_URI + "/get-by-id/{id}";
	private static final String GET_BY_ARTIST_URI = BASE_URI + "/get-by-artist/{artistId}";
	private static final String GET_BY_ALBUM_URI = BASE_URI + "/get-by-album/{albumId}";

	@Test
	public void getAllMusicTest() {
		super.webTestClient
				.get()
				.uri(GET_ALL_URI)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(true)
				.jsonPath("$.message").doesNotExist()
				.jsonPath("$.data").isArray()
				.jsonPath("$.data.length()").isEqualTo(3)
				.jsonPath("$.data[0].id").isEqualTo(-3L)
				.jsonPath("$.data[0].title").isEqualTo("Day of night")
				.jsonPath("$.data[0].duration.seconds").isEqualTo(30L)
				.jsonPath("$.data[0].duration.minutes").isEqualTo(0L)
				.jsonPath("$.data[0].duration.hours").isEqualTo(0L)
				.jsonPath("$.data[0].track").isEqualTo(2L)
				.jsonPath("$.data[0].albumId").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[0].album").doesNotExist()

				.jsonPath("$.data[1].id").isEqualTo(-2L)
				.jsonPath("$.data[1].title").isEqualTo("Be loved today")
				.jsonPath("$.data[1].duration.seconds").isEqualTo(0L)
				.jsonPath("$.data[1].duration.minutes").isEqualTo(1L)
				.jsonPath("$.data[1].duration.hours").isEqualTo(0L)
				.jsonPath("$.data[1].track").isEqualTo(1L)
				.jsonPath("$.data[1].albumId").isEqualTo(-2)
				.jsonPath("$.data[1].album").doesNotExist()

				.jsonPath("$.data[2].id").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[2].title").isEqualTo("Happy day")
				.jsonPath("$.data[2].duration.seconds").isEqualTo(30L)
				.jsonPath("$.data[2].duration.minutes").isEqualTo(1L)
				.jsonPath("$.data[2].duration.hours").isEqualTo(0L)
				.jsonPath("$.data[2].track").isEqualTo(1L)
				.jsonPath("$.data[2].albumId").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[2].album").doesNotExist()

				.jsonPath("$.page").isEqualTo(0)
				.jsonPath("$.size").isEqualTo(10)
				.jsonPath("$.totalElements").isEqualTo(3)
				.jsonPath("$.totalPages").isEqualTo(1)
				.jsonPath("$.number").isEqualTo(0)
		;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void getByIdMusicTest() {
		super.webTestClient
				.get()
				.uri(GET_BY_ID_URI, PRE_EXISTING_ID)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(true)
				.jsonPath("$.message").doesNotExist()
				.jsonPath("$.data").exists()
				.jsonPath("$.data.id").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data.title").isEqualTo("Happy day")
				.jsonPath("$.data.duration.seconds").isEqualTo(30L)
				.jsonPath("$.data.duration.minutes").isEqualTo(1L)
				.jsonPath("$.data.duration.hours").isEqualTo(0L)
				.jsonPath("$.data.track").isEqualTo(1L)
				.jsonPath("$.data.albumId").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data.album").doesNotExist()
		;
	}

	@Test
	public void getByIdMusicWithNonExistentIdTest() {
		super.webTestClient
				.get()
				.uri(GET_BY_ID_URI, NON_EXISTING_ID)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case MusicFindByIdQuery: Entity not found exception: Music ID " +
								NON_EXISTING_ID + " not found in the system.")
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void getByIdMusicWithInvalidIdErrorTest() {
		super.webTestClient
				.get()
				.uri(GET_BY_ID_URI, INVALID_ID)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void getMusicByArtistTest() {
		super.webTestClient
				.get()
				.uri(GET_BY_ARTIST_URI, PRE_EXISTING_ID)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(true)
				.jsonPath("$.message").doesNotExist()
				.jsonPath("$.data").isArray()
				.jsonPath("$.data.length()").isEqualTo(2)
				.jsonPath("$.data[0].id").isEqualTo(-3L)
				.jsonPath("$.data[0].title").isEqualTo("Day of night")
				.jsonPath("$.data[0].duration.seconds").isEqualTo(30L)
				.jsonPath("$.data[0].duration.minutes").isEqualTo(0L)
				.jsonPath("$.data[0].duration.hours").isEqualTo(0L)
				.jsonPath("$.data[0].track").isEqualTo(2L)
				.jsonPath("$.data[0].albumId").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[0].album").exists()
				.jsonPath("$.data[0].album.id").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[0].album.title").isEqualTo("Tranquility")
				.jsonPath("$.data[0].album.releaseYear").isEqualTo(2025L)
				.jsonPath("$.data[0].album.coverImage").isEqualTo("Tranquility cover image")
				.jsonPath("$.data[0].album.artistId").isEqualTo(PRE_EXISTING_ID)

				.jsonPath("$.data[1].id").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[1].title").isEqualTo("Happy day")
				.jsonPath("$.data[1].duration.seconds").isEqualTo(30L)
				.jsonPath("$.data[1].duration.minutes").isEqualTo(1L)
				.jsonPath("$.data[1].duration.hours").isEqualTo(0L)
				.jsonPath("$.data[1].track").isEqualTo(1L)
				.jsonPath("$.data[1].albumId").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[1].album").exists()
				.jsonPath("$.data[1].album.id").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[1].album.title").isEqualTo("Tranquility")
				.jsonPath("$.data[1].album.releaseYear").isEqualTo(2025L)
				.jsonPath("$.data[1].album.coverImage").isEqualTo("Tranquility cover image")
				.jsonPath("$.data[1].album.artistId").isEqualTo(PRE_EXISTING_ID)

		;
	}

	@Test
	public void getMusicByArtistWithNonExistentArtistIdTest() {
		super.webTestClient
				.get()
				.uri(GET_BY_ARTIST_URI, NON_EXISTING_ID)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case MusicFindByArtistQuery: Entity not found exception: Artist ID " + NON_EXISTING_ID + " not found in the system."
				)
				.jsonPath("$.data").doesNotExist();
	}

	@Test
	public void getMusicByArtistWithInvalidIdErrorTest() {
		super.webTestClient
				.get()
				.uri(GET_BY_ARTIST_URI, INVALID_ID)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void getMusicByAlbumSortedByTrackTest() {
		super.webTestClient
				.get()
				.uri(GET_BY_ALBUM_URI + "?isSortByTrack=true", PRE_EXISTING_ID)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(true)
				.jsonPath("$.message").doesNotExist()
				.jsonPath("$.data").isArray()
				.jsonPath("$.data.length()").isEqualTo(2)
				.jsonPath("$.data[0].id").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[0].title").isEqualTo("Happy day")
				.jsonPath("$.data[0].duration.seconds").isEqualTo(30L)
				.jsonPath("$.data[0].duration.minutes").isEqualTo(1L)
				.jsonPath("$.data[0].duration.hours").isEqualTo(0L)
				.jsonPath("$.data[0].track").isEqualTo(1L)
				.jsonPath("$.data[0].albumId").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[0].album").doesNotExist()

				.jsonPath("$.data[1].id").isEqualTo(-3L)
				.jsonPath("$.data[1].title").isEqualTo("Day of night")
				.jsonPath("$.data[1].duration.seconds").isEqualTo(30L)
				.jsonPath("$.data[1].duration.minutes").isEqualTo(0L)
				.jsonPath("$.data[1].duration.hours").isEqualTo(0L)
				.jsonPath("$.data[1].track").isEqualTo(2L)
				.jsonPath("$.data[1].albumId").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[1].album").doesNotExist()
		;
	}

	@Test
	public void getMusicByAlbumSortedAlphabeticallyTest() {
		super.webTestClient
				.get()
				.uri(GET_BY_ALBUM_URI + "?isSortByTrack=false", PRE_EXISTING_ID)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(true)
				.jsonPath("$.message").doesNotExist()
				.jsonPath("$.data").isArray()
				.jsonPath("$.data.length()").isEqualTo(2)
				.jsonPath("$.data[0].id").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[0].title").isEqualTo("Happy day")
				.jsonPath("$.data[0].duration.seconds").isEqualTo(30L)
				.jsonPath("$.data[0].duration.minutes").isEqualTo(1L)
				.jsonPath("$.data[0].track").isEqualTo(1L)
				.jsonPath("$.data[0].albumId").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[0].album").doesNotExist()

				.jsonPath("$.data[1].id").isEqualTo(-3L)
				.jsonPath("$.data[1].title").isEqualTo("Day of night")
				.jsonPath("$.data[1].duration.seconds").isEqualTo(30L)
				.jsonPath("$.data[1].duration.minutes").isEqualTo(0L)
				.jsonPath("$.data[1].duration.hours").isEqualTo(0L)
				.jsonPath("$.data[1].track").isEqualTo(2L)
				.jsonPath("$.data[1].albumId").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[1].album").doesNotExist()
		;
	}

	@Test
	public void getMusicByAlbumWithNonExistentAlbumIdTest() {
		super.webTestClient
				.get()
				.uri(GET_BY_ALBUM_URI, NON_EXISTING_ID)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case MusicFindByAlbumQuery: Entity not found exception: Album ID " + NON_EXISTING_ID + " not found in the system."
				)
				.jsonPath("$.data").doesNotExist();
	}

	@Test
	public void getMusicByAlbumWithInvalidIdErrorTest() {
		super.webTestClient
				.get()
				.uri(GET_BY_ALBUM_URI, INVALID_ID)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}
}
