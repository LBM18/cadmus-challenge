package com.project.cadmus_challenge.integration.artist;

import com.project.cadmus_challenge.integration.ControllerIntegrationTests;
import org.junit.jupiter.api.Test;

public class ArtistGetControllerIntegrationTests extends ControllerIntegrationTests {
	private static final String BASE_URI = ROOT_URI + "/artist";
	private static final String GET_ALL_URI = BASE_URI + "/get-all";
	private static final String GET_BY_ID_URI = BASE_URI + "/get-by-id/{id}";

	@Test
	public void getAllArtistTest() {
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
				.jsonPath("$.data.length()").isEqualTo(2)
				.jsonPath("$.data[0].id").isEqualTo(-2L)
				.jsonPath("$.data[0].name").isEqualTo("Joao")
				.jsonPath("$.data[0].nationality").isEqualTo("Brazilian")
				.jsonPath("$.data[0].websiteAddress").isEqualTo("http://www.joao.com.br")
				.jsonPath("$.data[0].profileImage").isEqualTo("Joao profile image")

				.jsonPath("$.data[1].id").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[1].name").isEqualTo("James")
				.jsonPath("$.data[1].nationality").isEqualTo("British")
				.jsonPath("$.data[1].websiteAddress").isEqualTo("http://www.james.com")
				.jsonPath("$.data[1].profileImage").isEqualTo("James profile image")

				.jsonPath("$.page").isEqualTo(0)
				.jsonPath("$.size").isEqualTo(10)
				.jsonPath("$.totalElements").isEqualTo(2)
				.jsonPath("$.totalPages").isEqualTo(1)
				.jsonPath("$.number").isEqualTo(0)
		;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void getByIdArtistTest() {
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
				.jsonPath("$.data.name").isEqualTo("James")
				.jsonPath("$.data.nationality").isEqualTo("British")
				.jsonPath("$.data.websiteAddress").isEqualTo("http://www.james.com")
				.jsonPath("$.data.profileImage").isEqualTo("James profile image")
		;
	}

	@Test
	public void getByIdArtistWithNonExistentIdTest() {
		super.webTestClient
				.get()
				.uri(GET_BY_ID_URI, NON_EXISTING_ID)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case ArtistFindByIdQuery: Entity not found exception: Artist ID " +
								NON_EXISTING_ID + " not found in the system.")
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void getByIdArtistWithInvalidIdErrorTest() {
		super.webTestClient
				.get()
				.uri(GET_BY_ID_URI, INVALID_ID)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}
}
