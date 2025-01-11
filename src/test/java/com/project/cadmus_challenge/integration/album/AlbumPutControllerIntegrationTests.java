package com.project.cadmus_challenge.integration.album;

import com.project.cadmus_challenge.application.dtos.AlbumInputDto;
import com.project.cadmus_challenge.integration.ControllerIntegrationTests;
import org.junit.jupiter.api.Test;

public class AlbumPutControllerIntegrationTests extends ControllerIntegrationTests {
	private static final String BASE_URI = ROOT_URI + "/album/{id}";

	@Test
	public void updateAlbumTest() {
		var inputDto = new AlbumInputDto(
				" Rock songs  ",
				2020L,
				" Rock songs cover image ",
				PRE_EXISTING_ID
		);
		super.webTestClient
				.put()
				.uri(BASE_URI, PRE_EXISTING_ID)
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
		super.webTestClient
				.put()
				.uri(BASE_URI, PRE_EXISTING_ID)
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
		super.webTestClient
				.put()
				.uri(BASE_URI, PRE_EXISTING_ID)
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
		super.webTestClient
				.put()
				.uri(BASE_URI, PRE_EXISTING_ID)
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
		super.webTestClient
				.put()
				.uri(BASE_URI, PRE_EXISTING_ID)
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
		super.webTestClient
				.put()
				.uri(BASE_URI, NON_EXISTING_ID)
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
		super.webTestClient
				.put()
				.uri(BASE_URI, INVALID_ID)
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
		super.webTestClient
				.put()
				.uri(BASE_URI, PRE_EXISTING_ID)
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
}
