package com.project.cadmus_challenge.integration.album;

import com.project.cadmus_challenge.application.dtos.AlbumInputDto;
import com.project.cadmus_challenge.integration.ControllerIntegrationTests;
import org.junit.jupiter.api.Test;

public class AlbumPostControllerIntegrationTests extends ControllerIntegrationTests {
	private static final String BASE_URI = ROOT_URI + "/album";

	@Test
	public void createAlbumTest() {
		var inputDto = new AlbumInputDto(
				" Love songs  ",
				2025L,
				" Love songs cover image ",
				PRE_EXISTING_ID
		);
		super.webTestClient
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
		super.webTestClient
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
		super.webTestClient
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
		super.webTestClient
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
		super.webTestClient
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
		super.webTestClient
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
}
