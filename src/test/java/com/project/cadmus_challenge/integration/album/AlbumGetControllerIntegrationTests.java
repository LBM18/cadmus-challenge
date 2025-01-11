package com.project.cadmus_challenge.integration.album;

import com.project.cadmus_challenge.integration.ControllerIntegrationTests;
import org.junit.jupiter.api.Test;

public class AlbumGetControllerIntegrationTests extends ControllerIntegrationTests {
	private static final String BASE_URI = ROOT_URI + "/album";
	private static final String GET_ALL_URI = BASE_URI + "/get-all";
	private static final String GET_BY_ID_URI = BASE_URI + "/get-by-id/{id}";

	@Test
	public void getAllAlbumTest() {
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
				.jsonPath("$.data.title").isEqualTo("Tranquility")
				.jsonPath("$.data.releaseYear").isEqualTo(2025)
				.jsonPath("$.data.coverImage").isEqualTo("Tranquility cover image")
				.jsonPath("$.data.artistId").isEqualTo(PRE_EXISTING_ID)
		;
	}

	@Test
	public void getByIdAlbumWithNonExistentIdTest() {
		super.webTestClient
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
