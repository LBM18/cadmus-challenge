package com.project.cadmus_challenge.integration.music;

import com.project.cadmus_challenge.integration.ControllerIntegrationTests;
import org.junit.jupiter.api.Test;

public class MusicDeleteControllerIntegrationTests extends ControllerIntegrationTests {
	private static final String BASE_URI = ROOT_URI + "/music/{id}";

	@Test
	public void deleteMusicTest() {
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
	public void deleteMusicWithNonExistentIdErrorTest() {
		super.webTestClient
				.delete()
				.uri(BASE_URI, NON_EXISTING_ID)
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
