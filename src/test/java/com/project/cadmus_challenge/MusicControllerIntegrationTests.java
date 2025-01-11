package com.project.cadmus_challenge;

import com.project.cadmus_challenge.application.bases.DurationMusic;
import com.project.cadmus_challenge.application.dtos.MusicInputDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MusicControllerIntegrationTests {
	private static final String BASE_URI = "/api/v1/music";
	private static final String ID_URI = BASE_URI + "/{id}";
	private static final String GET_ALL_URI = BASE_URI + "/get-all";
	private static final String GET_BY_ID_URI = BASE_URI + "/get-by-id/{id}";
	private static final String GET_BY_ARTIST_URI = BASE_URI + "/get-by-artist/{artistId}";
	private static final String GET_BY_ALBUM_URI = BASE_URI + "/get-by-album/{albumId}";

	private static final Long PRE_EXISTING_ID = 0L;
	private static final Long NON_EXISTING_ID = -1L;
	private static final String INVALID_ID = "ID";

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void createMusicTest() {
		var duration = new DurationMusic(1L, 0L, 0L);
		var inputDto = new MusicInputDto(
				" I Love You  ",
				duration,
				1L,
				PRE_EXISTING_ID
		);
		this.webTestClient
				.post()
				.uri(BASE_URI)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isCreated()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(true)
				.jsonPath("$.message").doesNotExist()
				.jsonPath("$.data").exists()
				.jsonPath("$.data.id").exists()
				.jsonPath("$.data.title").isEqualTo(inputDto.title().trim())
				.jsonPath("$.data.duration").isEqualTo(duration)
				.jsonPath("$.data.track").isEqualTo(inputDto.track())
				.jsonPath("$.data.albumId").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data.album").doesNotExist()
		;
	}

	@Test
	public void createMusicWithBlankTitleErrorTest() {
		var inputDto = new MusicInputDto(
				"   ",
				new DurationMusic(1L, 0L, 0L),
				1L,
				PRE_EXISTING_ID
		);
		this.webTestClient
				.post()
				.uri(BASE_URI)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}

	@Test
	public void createMusicWithNullDurationErrorTest() {
		var inputDto = new MusicInputDto(
				" I Love You  ",
				null,
				1L,
				PRE_EXISTING_ID
		);
		this.webTestClient
				.post()
				.uri(BASE_URI)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}

	@Test
	public void createMusicWithNullTrackErrorTest() {
		var inputDto = new MusicInputDto(
				" I Love You  ",
				new DurationMusic(1L, 0L, 0L),
				null,
				PRE_EXISTING_ID
		);
		this.webTestClient
				.post()
				.uri(BASE_URI)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}

	@Test
	public void createMusicWithNullAlbumIdErrorTest() {
		var inputDto = new MusicInputDto(
				" I Love You  ",
				new DurationMusic(1L, 0L, 0L),
				1L,
				null
		);
		this.webTestClient
				.post()
				.uri(BASE_URI)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}

	@Test
	public void createMusicWithInvalidTrackErrorTest() {
		var inputDto = new MusicInputDto(
				" I Love You  ",
				new DurationMusic(1L, 0L, 0L),
				0L,
				PRE_EXISTING_ID
		);
		this.webTestClient
				.post()
				.uri(BASE_URI)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case MusicCreateCommand: Business exception: Music track number must be greater than zero."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void createMusicWithInvalidDurationErrorTest() {
		var inputDto = new MusicInputDto(
				" I Love You  ",
				new DurationMusic(0L, 0L, 0L),
				1L,
				PRE_EXISTING_ID
		);
		this.webTestClient
				.post()
				.uri(BASE_URI)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case MusicCreateCommand: Business exception: Music duration must represent valid minutes and seconds."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void updateMusicTest() {
		var duration = new DurationMusic(0L, 1L, 0L);
		var inputDto = new MusicInputDto(
				" Imagine ",
				duration,
				2L,
				PRE_EXISTING_ID
		);
		this.webTestClient
				.put()
				.uri(ID_URI, PRE_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isAccepted()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(true)
				.jsonPath("$.message").doesNotExist()
				.jsonPath("$.data").exists()
				.jsonPath("$.data.id").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data.title").isEqualTo(inputDto.title().trim())
				.jsonPath("$.data.duration").isEqualTo(duration)
				.jsonPath("$.data.track").isEqualTo(inputDto.track())
				.jsonPath("$.data.albumId").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data.album").doesNotExist()
		;
	}

	@Test
	public void updateMusicWithBlankTitleErrorTest() {
		var inputDto = new MusicInputDto(
				"   ",
				new DurationMusic(0L, 1L, 0L),
				2L,
				PRE_EXISTING_ID
		);
		this.webTestClient
				.put()
				.uri(ID_URI, PRE_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}

	@Test
	public void updateMusicWithNullDurationErrorTest() {
		var inputDto = new MusicInputDto(
				" Imagine ",
				null,
				2L,
				PRE_EXISTING_ID
		);
		this.webTestClient
				.put()
				.uri(ID_URI, PRE_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}

	@Test
	public void updateMusicWithNullTrackErrorTest() {
		var inputDto = new MusicInputDto(
				" Imagine ",
				new DurationMusic(0L, 1L, 0L),
				null,
				PRE_EXISTING_ID
		);
		this.webTestClient
				.put()
				.uri(ID_URI, PRE_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}

	@Test
	public void updateMusicWithNullAlbumIdErrorTest() {
		var inputDto = new MusicInputDto(
				" Imagine ",
				new DurationMusic(0L, 1L, 0L),
				2L,
				null
		);
		this.webTestClient
				.put()
				.uri(ID_URI, PRE_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}

	@Test
	public void updateMusicWithNonExistentIdErrorTest() {
		var inputDto = new MusicInputDto(
				" Imagine ",
				new DurationMusic(0L, 1L, 0L),
				2L,
				PRE_EXISTING_ID
		);
		this.webTestClient
				.put()
				.uri(ID_URI, NON_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case MusicEditCommand: Entity not found exception: Music ID " +
								NON_EXISTING_ID + " not found in the system."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void updateMusicWithInvalidIdErrorTest() {
		var inputDto = new MusicInputDto(
				" Imagine ",
				new DurationMusic(0L, 1L, 0L),
				2L,
				PRE_EXISTING_ID
		);
		this.webTestClient
				.put()
				.uri(ID_URI, INVALID_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}

	@Test
	public void updateMusicWithInvalidTrackErrorTest() {
		var inputDto = new MusicInputDto(
				" Imagine ",
				new DurationMusic(0L, 1L, 0L),
				0L,
				PRE_EXISTING_ID
		);
		this.webTestClient
				.put()
				.uri(ID_URI, PRE_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case MusicEditCommand: Business exception: Music track number must be greater than zero."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void updateMusicWithInvalidDurationErrorTest() {
		var inputDto = new MusicInputDto(
				" Imagine ",
				new DurationMusic(-1L, -1L, -1L),
				2L,
				PRE_EXISTING_ID
		);
		this.webTestClient
				.put()
				.uri(ID_URI, PRE_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case MusicEditCommand: Business exception: Music duration must represent valid minutes and seconds."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void deleteMusicTest() {
		this.webTestClient
				.delete()
				.uri(ID_URI, PRE_EXISTING_ID)
				.exchange()
				.expectStatus().isAccepted()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(true)
				.jsonPath("$.message").doesNotExist()
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void deleteMusicWithNonExistentIdErrorTest() {
		this.webTestClient
				.delete()
				.uri(ID_URI, NON_EXISTING_ID)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case MusicDeleteCommand: Entity not found exception: Music ID " +
								NON_EXISTING_ID + " not found in the system."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void deleteMusicWithInvalidIdErrorTest() {
		this.webTestClient
				.delete()
				.uri(ID_URI, INVALID_ID)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void getAllMusicTest() {
		this.webTestClient
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
		this.webTestClient
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
		this.webTestClient
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
		this.webTestClient
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
		this.webTestClient
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
		this.webTestClient
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
		this.webTestClient
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
		this.webTestClient
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
		this.webTestClient
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
		this.webTestClient
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
		this.webTestClient
				.get()
				.uri(GET_BY_ALBUM_URI, INVALID_ID)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}
}
