package com.project.cadmus_challenge.integration.artist;

import com.project.cadmus_challenge.application.dtos.ArtistInputDto;
import com.project.cadmus_challenge.integration.ControllerIntegrationTests;
import org.junit.jupiter.api.Test;

public class ArtistPostControllerIntegrationTests extends ControllerIntegrationTests {
	private static final String BASE_URI = ROOT_URI + "/artist";

	@Test
	public void createArtistTest() {
		var inputDto = new ArtistInputDto(
				" Ana  ",
				"  Brazilian ",
				" https://www.ana.com.br ",
				"  Ana profile image  "
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
				.jsonPath("$.data.name").isEqualTo(inputDto.name().trim())
				.jsonPath("$.data.nationality").isEqualTo(inputDto.nationality().trim())
				.jsonPath("$.data.websiteAddress").isEqualTo(inputDto.websiteAddress().trim())
				.jsonPath("$.data.profileImage").isEqualTo(inputDto.profileImage().trim())
		;
	}

	@Test
	public void createArtistWithBlankNameErrorTest() {
		var inputDto = new ArtistInputDto(
				"   ",
				"  Brazilian ",
				" https://www.ana.com.br ",
				"  Ana profile image  "
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
	public void createArtistWithBlankNationalityErrorTest() {
		var inputDto = new ArtistInputDto(
				" Ana  ",
				"   ",
				" https://www.ana.com.br ",
				"  Ana profile image  "
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
	public void createArtistWithBlankWebsiteAddressErrorTest() {
		var inputDto = new ArtistInputDto(
				" Ana  ",
				"  Brazilian ",
				"   ",
				"  Ana profile image  "
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
	public void createArtistWithBlankProfileImageErrorTest() {
		var inputDto = new ArtistInputDto(
				" Ana  ",
				"  Brazilian ",
				" https://www.ana.com.br ",
				"   "
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
	public void createArtistWithInvalidWebsiteAddressErrorTest() {
		var inputDto = new ArtistInputDto(
				" Ana  ",
				"  Brazilian ",
				" ana.com.br ",
				"  Ana profile image  "
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
						"Error executing use case ArtistCreateCommand: Business exception: Artist website address must be a valid URL."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}
}
