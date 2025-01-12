package com.project.cadmus_challenge.integration.artist;

import com.project.cadmus_challenge.integration.ControllerIntegrationTests;
import org.junit.jupiter.api.Test;

public class ArtistDeleteControllerIntegrationTests extends ControllerIntegrationTests {
	private static final String BASE_URI = ROOT_URI + "/artist/{id}";

	@Test
	public void deleteArtistTest() {
		super.webTestClient
				.delete()
				.uri(BASE_URI, PRE_EXISTING_ID)
				.exchange()
				.expectStatus().isAccepted()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(true)
				.jsonPath("$.message").doesNotExist()
				.jsonPath("$.data").exists()
				.jsonPath("$.data.id").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data.name").isEqualTo("James")
				.jsonPath("$.data.nationality").isEqualTo("British")
				.jsonPath("$.data.websiteAddress").isEqualTo("http://www.james.com")
				.jsonPath("$.data.profileImage").isEqualTo("James profile image")
		;
	}

	@Test
	public void deleteArtistWithNonExistentIdErrorTest() {
		super.webTestClient
				.delete()
				.uri(BASE_URI, NON_EXISTING_ID)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case ArtistDeleteCommand: Entity not found exception: Artist ID " +
								NON_EXISTING_ID + " not found in the system."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void deleteArtistWithInvalidIdErrorTest() {
		super.webTestClient
				.delete()
				.uri(BASE_URI, INVALID_ID)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}
}
