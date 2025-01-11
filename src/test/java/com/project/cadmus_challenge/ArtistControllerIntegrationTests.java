package com.project.cadmus_challenge;

import com.project.cadmus_challenge.application.dtos.ArtistInputDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ArtistControllerIntegrationTests {
	private static final String BASE_URI = "/api/v1/artist";
	private static final String ID_URI = BASE_URI + "/{id}";
	private static final String GET_ALL_URI = BASE_URI + "/get-all";
	private static final String GET_BY_ID_URI = BASE_URI + "/get-by-id/{id}";

	private static final Long PRE_EXISTING_ID = 0L;
	private static final Long NON_EXISTING_ID = -1L;
	private static final String INVALID_ID = "ID";

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void createArtistTest() {
		var inputDto = new ArtistInputDto(
				" Ana  ",
				"  Brazilian ",
				" https://www.ana.com.br ",
				"  Ana profile image  "
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
	public void createArtistWithBlankNationalityErrorTest() {
		var inputDto = new ArtistInputDto(
				" Ana  ",
				"   ",
				" https://www.ana.com.br ",
				"  Ana profile image  "
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
	public void createArtistWithBlankWebsiteAddressErrorTest() {
		var inputDto = new ArtistInputDto(
				" Ana  ",
				"  Brazilian ",
				"   ",
				"  Ana profile image  "
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
	public void createArtistWithBlankProfileImageErrorTest() {
		var inputDto = new ArtistInputDto(
				" Ana  ",
				"  Brazilian ",
				" https://www.ana.com.br ",
				"   "
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
	public void createArtistWithInvalidWebsiteAddressErrorTest() {
		var inputDto = new ArtistInputDto(
				" Ana  ",
				"  Brazilian ",
				" ana.com.br ",
				"  Ana profile image  "
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
						"Error executing use case ArtistCreateCommand: Business exception: Artist website address must be a valid URL."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void updateArtistTest() {
		var inputDto = new ArtistInputDto(
				" Albert  ",
				"  American ",
				" http://www.albert.com ",
				"  Albert profile image  "
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
				.jsonPath("$.data.name").isEqualTo(inputDto.name().trim())
				.jsonPath("$.data.nationality").isEqualTo(inputDto.nationality().trim())
				.jsonPath("$.data.websiteAddress").isEqualTo(inputDto.websiteAddress().trim())
				.jsonPath("$.data.profileImage").isEqualTo(inputDto.profileImage().trim())
		;
	}

	@Test
	public void updateArtistWithBlankNameErrorTest() {
		var inputDto = new ArtistInputDto(
				"   ",
				"  American ",
				" http://www.albert.com ",
				"  Albert profile image  "
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
	public void updateArtistWithBlankNationalityErrorTest() {
		var inputDto = new ArtistInputDto(
				" Albert  ",
				"   ",
				" http://www.albert.com ",
				"  Albert profile image  "
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
	public void updateArtistWithBlankWebsiteAddressErrorTest() {
		var inputDto = new ArtistInputDto(
				" Albert  ",
				"  American ",
				"   ",
				"  Albert profile image  "
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
	public void updateArtistWithBlankProfileImageErrorTest() {
		var inputDto = new ArtistInputDto(
				" Albert  ",
				"  American ",
				" http://www.albert.com ",
				"   "
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
	public void updateArtistWithNonExistentIdErrorTest() {
		var inputDto = new ArtistInputDto(
				" Albert  ",
				"  American ",
				" http://www.albert.com ",
				"  Albert profile image  "
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
						"Error executing use case ArtistEditCommand: Entity not found exception: Artist ID " +
								NON_EXISTING_ID + " not found in the system."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void updateArtistWithInvalidIdErrorTest() {
		var inputDto = new ArtistInputDto(
				" Albert  ",
				"  American ",
				" http://www.albert.com ",
				"  Albert profile image  "
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
	public void updateArtistWithInvalidWebsiteAddressErrorTest() {
		var inputDto = new ArtistInputDto(
				" Albert  ",
				"  American ",
				" www.albert.com ",
				"  Albert profile image  "
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
						"Error executing use case ArtistEditCommand: Business exception: Artist website address must be a valid URL."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void deleteArtistTest() {
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
	public void deleteArtistWithNonExistentIdErrorTest() {
		this.webTestClient
				.delete()
				.uri(ID_URI, NON_EXISTING_ID)
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
	public void getAllArtistTest() {
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
				.jsonPath("$.data[0].name").isEqualTo("Joao")
				.jsonPath("$.data[0].nationality").isEqualTo("Brazilian")
				.jsonPath("$.data[0].websiteAddress").isEqualTo("http://www.joao.com.br")
				.jsonPath("$.data[0].profileImage").isEqualTo("Joao profile image")

				.jsonPath("$.data[1].id").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data[1].name").isEqualTo("James")
				.jsonPath("$.data[1].nationality").isEqualTo("British")
				.jsonPath("$.data[1].websiteAddress").isEqualTo("http://www.james.com")
				.jsonPath("$.data[1].profileImage").isEqualTo("British profile image")

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
				.jsonPath("$.data.name").isEqualTo("James")
				.jsonPath("$.data.nationality").isEqualTo("British")
				.jsonPath("$.data.websiteAddress").isEqualTo("http://www.james.com")
				.jsonPath("$.data.profileImage").isEqualTo("British profile image")
		;
	}

	@Test
	public void getByIdArtistWithNonExistentIdTest() {
		this.webTestClient
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
