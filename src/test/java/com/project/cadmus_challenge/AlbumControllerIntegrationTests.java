package com.project.cadmus_challenge;

import com.project.cadmus_challenge.application.dtos.AlbumInputDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AlbumControllerIntegrationTests {
	private static final String BASE_URI = "/api/v1/album";
	private static final String ID_URI = BASE_URI + "/{id}";
	private static final String GET_ALL_URI = BASE_URI + "/get-all";
	private static final String GET_BY_ID_URI = BASE_URI + "/get-by-id/{id}";

	private static final Long PRE_EXISTING_ID = 0L;
	private static final Long NON_EXISTING_ID = -1L;
	private static final String INVALID_ID = "ID";

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void createAlbumTest() {
		var inputDto = new AlbumInputDto(
				" Love songs  ",
				2025L,
				" Love songs cover image ",
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
				.jsonPath("$.data.releaseYear").isEqualTo(inputDto.releaseYear())
				.jsonPath("$.data.coverImage").isEqualTo(inputDto.coverImage().trim())
				.jsonPath("$.data.artistId").isEqualTo(PRE_EXISTING_ID)
		;
	}

	@Test
	public void createAlbumWithBlankTitleErrorTest() {
		var inputDto = new AlbumInputDto(
				"   ",
				2025L,
				" Love songs cover image ",
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
	public void createAlbumWithNullReleaseYearErrorTest() {
		var inputDto = new AlbumInputDto(
				" Love songs  ",
				null,
				" Love songs cover image ",
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
	public void createAlbumWithBlankCoverImageErrorTest() {
		var inputDto = new AlbumInputDto(
				" Love songs  ",
				2025L,
				"   ",
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
	public void createAlbumWithNullArtistIdErrorTest() {
		var inputDto = new AlbumInputDto(
				" Love songs  ",
				2025L,
				" Love songs cover image ",
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
	public void createAlbumWithInvalidReleaseYearErrorTest() {
		var inputDto = new AlbumInputDto(
				" Love songs  ",
				3000L,
				" Love songs cover image ",
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
						"Error executing use case AlbumCreateCommand: Business exception: Album release year must not be a date later than the current one."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void updateAlbumTest() {
		var inputDto = new AlbumInputDto(
				" Rock songs  ",
				2020L,
				" Rock songs cover image ",
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
				.jsonPath("$.data.releaseYear").isEqualTo(inputDto.releaseYear())
				.jsonPath("$.data.coverImage").isEqualTo(inputDto.coverImage().trim())
				.jsonPath("$.data.artistId").isEqualTo(PRE_EXISTING_ID)
		;
	}

	@Test
	public void updateAlbumWithBlankTitleErrorTest() {
		var inputDto = new AlbumInputDto(
				"   ",
				2020L,
				" Rock songs cover image ",
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
	public void updateAlbumWithNullReleaseYearErrorTest() {
		var inputDto = new AlbumInputDto(
				" Rock songs  ",
				null,
				" Rock songs cover image ",
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
	public void updateAlbumWithBlankCoverImageErrorTest() {
		var inputDto = new AlbumInputDto(
				" Rock songs  ",
				2020L,
				"   ",
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
	public void updateAlbumWithNullArtistIdErrorTest() {
		var inputDto = new AlbumInputDto(
				" Rock songs  ",
				2020L,
				" Rock songs cover image ",
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
	public void updateAlbumWithNonExistentIdErrorTest() {
		var inputDto = new AlbumInputDto(
				" Rock songs  ",
				2020L,
				" Rock songs cover image ",
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
						"Error executing use case AlbumEditCommand: Entity not found exception: Album ID " +
								NON_EXISTING_ID + " not found in the system."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void updateAlbumWithInvalidIdErrorTest() {
		var inputDto = new AlbumInputDto(
				" Rock songs  ",
				2020L,
				" Rock songs cover image ",
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
	public void updateAlbumWithInvalidReleaseYearErrorTest() {
		var inputDto = new AlbumInputDto(
				" Rock songs  ",
				3000L,
				" Rock songs cover image ",
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
						"Error executing use case AlbumEditCommand: Business exception: Album release year must not be a date later than the current one."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void deleteAlbumTest() {
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
	public void deleteAlbumWithNonExistentIdErrorTest() {
		this.webTestClient
				.delete()
				.uri(ID_URI, NON_EXISTING_ID)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case AlbumDeleteCommand: Entity not found exception: Album ID " +
								NON_EXISTING_ID + " not found in the system."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void deleteAlbumWithInvalidIdErrorTest() {
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
	public void getAllAlbumTest() {
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
				.jsonPath("$.data.length()").isEqualTo(2)

				.jsonPath("$.data[0].id").isEqualTo(-2)
				.jsonPath("$.data[0].title").isEqualTo("Happiness")
				.jsonPath("$.data[0].releaseYear").isEqualTo(2022)
				.jsonPath("$.data[0].coverImage").isEqualTo("Happiness cover image")
				.jsonPath("$.data[0].artistId").isEqualTo(-2)

				.jsonPath("$.data[1].id").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[1].title").isEqualTo("Tranquility")
				.jsonPath("$.data[1].releaseYear").isEqualTo(2025)
				.jsonPath("$.data[1].coverImage").isEqualTo("Tranquility cover image")
				.jsonPath("$.data[1].artistId").isEqualTo(PRE_EXISTING_ID)

				.jsonPath("$.page").isEqualTo(0)
				.jsonPath("$.size").isEqualTo(10)
				.jsonPath("$.totalElements").isEqualTo(2)
				.jsonPath("$.totalPages").isEqualTo(1)
				.jsonPath("$.number").isEqualTo(0)
		;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void getByIdAlbumTest() {
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
				.jsonPath("$.data.title").isEqualTo("Tranquility")
				.jsonPath("$.data.releaseYear").isEqualTo(2025)
				.jsonPath("$.data.coverImage").isEqualTo("Tranquility cover image")
				.jsonPath("$.data.artistId").isEqualTo(PRE_EXISTING_ID)
		;
	}

	@Test
	public void getByIdAlbumWithNonExistentIdTest() {
		this.webTestClient
				.get()
				.uri(GET_BY_ID_URI, NON_EXISTING_ID)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case AlbumFindByIdQuery: Entity not found exception: Album ID " +
								NON_EXISTING_ID + " not found in the system.")
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void getByIdAlbumWithInvalidIdErrorTest() {
		this.webTestClient
				.get()
				.uri(GET_BY_ID_URI, INVALID_ID)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}
}
