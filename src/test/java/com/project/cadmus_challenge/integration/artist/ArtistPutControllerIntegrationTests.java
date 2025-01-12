// TODO: Fix these integration tests. Broken after using MULTIPART_FORM_DATA for handler images.
//package com.project.cadmus_challenge.integration.artist;
//
//import com.project.cadmus_challenge.application.dtos.ArtistInputDto;
//import com.project.cadmus_challenge.integration.ControllerIntegrationTests;
//import org.junit.jupiter.api.Test;
//
//public class ArtistPutControllerIntegrationTests extends ControllerIntegrationTests {
//	private static final String BASE_URI = ROOT_URI + "/artist/{id}";
//
//	@Test
//	public void updateArtistTest() {
//		var inputDto = new ArtistInputDto(
//				" Albert  ",
//				"  American ",
//				" http://www.albert.com ",
//				"  Albert profile image  "
//		);
//		super.webTestClient
//				.put()
//				.uri(BASE_URI, PRE_EXISTING_ID)
//				.bodyValue(inputDto)
//				.exchange()
//				.expectStatus().isAccepted()
//				.expectBody()
//				.jsonPath("$").exists()
//				.jsonPath("$.success").isEqualTo(true)
//				.jsonPath("$.message").doesNotExist()
//				.jsonPath("$.data").exists()
//				.jsonPath("$.data.id").isEqualTo(PRE_EXISTING_ID)
//				.jsonPath("$.data.name").isEqualTo(inputDto.name().trim())
//				.jsonPath("$.data.nationality").isEqualTo(inputDto.nationality().trim())
//				.jsonPath("$.data.websiteAddress").isEqualTo(inputDto.websiteAddress().trim())
//				.jsonPath("$.data.profileImage").isEqualTo(inputDto.profileImage().trim())
//		;
//	}
//
//	@Test
//	public void updateArtistWithBlankNameErrorTest() {
//		var inputDto = new ArtistInputDto(
//				"   ",
//				"  American ",
//				" http://www.albert.com ",
//				"  Albert profile image  "
//		);
//		super.webTestClient
//				.put()
//				.uri(BASE_URI, PRE_EXISTING_ID)
//				.bodyValue(inputDto)
//				.exchange()
//				.expectStatus().isBadRequest()
//				.expectBody()
//				.jsonPath("$").exists()
//		;
//	}
//
//	@Test
//	public void updateArtistWithBlankNationalityErrorTest() {
//		var inputDto = new ArtistInputDto(
//				" Albert  ",
//				"   ",
//				" http://www.albert.com ",
//				"  Albert profile image  "
//		);
//		super.webTestClient
//				.put()
//				.uri(BASE_URI, PRE_EXISTING_ID)
//				.bodyValue(inputDto)
//				.exchange()
//				.expectStatus().isBadRequest()
//				.expectBody()
//				.jsonPath("$").exists()
//		;
//	}
//
//	@Test
//	public void updateArtistWithBlankWebsiteAddressErrorTest() {
//		var inputDto = new ArtistInputDto(
//				" Albert  ",
//				"  American ",
//				"   ",
//				"  Albert profile image  "
//		);
//		super.webTestClient
//				.put()
//				.uri(BASE_URI, PRE_EXISTING_ID)
//				.bodyValue(inputDto)
//				.exchange()
//				.expectStatus().isBadRequest()
//				.expectBody()
//				.jsonPath("$").exists()
//		;
//	}
//
//	@Test
//	public void updateArtistWithBlankProfileImageErrorTest() {
//		var inputDto = new ArtistInputDto(
//				" Albert  ",
//				"  American ",
//				" http://www.albert.com ",
//				"   "
//		);
//		super.webTestClient
//				.put()
//				.uri(BASE_URI, PRE_EXISTING_ID)
//				.bodyValue(inputDto)
//				.exchange()
//				.expectStatus().isBadRequest()
//				.expectBody()
//				.jsonPath("$").exists()
//		;
//	}
//
//	@Test
//	public void updateArtistWithNonExistentIdErrorTest() {
//		var inputDto = new ArtistInputDto(
//				" Albert  ",
//				"  American ",
//				" http://www.albert.com ",
//				"  Albert profile image  "
//		);
//		super.webTestClient
//				.put()
//				.uri(BASE_URI, NON_EXISTING_ID)
//				.bodyValue(inputDto)
//				.exchange()
//				.expectStatus().isNotFound()
//				.expectBody()
//				.jsonPath("$").exists()
//				.jsonPath("$.success").isEqualTo(false)
//				.jsonPath("$.message").isEqualTo(
//						"Error executing use case ArtistEditCommand: Entity not found exception: Artist ID " +
//								NON_EXISTING_ID + " not found in the system."
//				)
//				.jsonPath("$.data").doesNotExist()
//		;
//	}
//
//	@Test
//	public void updateArtistWithInvalidIdErrorTest() {
//		var inputDto = new ArtistInputDto(
//				" Albert  ",
//				"  American ",
//				" http://www.albert.com ",
//				"  Albert profile image  "
//		);
//		super.webTestClient
//				.put()
//				.uri(BASE_URI, INVALID_ID)
//				.bodyValue(inputDto)
//				.exchange()
//				.expectStatus().isBadRequest()
//				.expectBody()
//				.jsonPath("$").exists()
//		;
//	}
//
//	@Test
//	public void updateArtistWithInvalidWebsiteAddressErrorTest() {
//		var inputDto = new ArtistInputDto(
//				" Albert  ",
//				"  American ",
//				" www.albert.com ",
//				"  Albert profile image  "
//		);
//		super.webTestClient
//				.put()
//				.uri(BASE_URI, PRE_EXISTING_ID)
//				.bodyValue(inputDto)
//				.exchange()
//				.expectStatus().isNotFound()
//				.expectBody()
//				.jsonPath("$").exists()
//				.jsonPath("$.success").isEqualTo(false)
//				.jsonPath("$.message").isEqualTo(
//						"Error executing use case ArtistEditCommand: Business exception: Artist website address must be a valid URL."
//				)
//				.jsonPath("$.data").doesNotExist()
//		;
//	}
//}
