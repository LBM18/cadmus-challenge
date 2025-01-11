package com.project.cadmus_challenge.integration.album;

import com.project.cadmus_challenge.integration.ControllerIntegrationTests;
import org.junit.jupiter.api.Test;

public class AlbumDeleteControllerIntegrationTests extends ControllerIntegrationTests {
	private static final String BASE_URI = ROOT_URI + "/album/{id}";

	@Test
	public void deleteAlbumTest() {
		super.webTestClient
				.delete()
				.uri(BASE_URI, PRE_EXISTING_ID)
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
		super.webTestClient
				.delete()
				.uri(BASE_URI, NON_EXISTING_ID)
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
