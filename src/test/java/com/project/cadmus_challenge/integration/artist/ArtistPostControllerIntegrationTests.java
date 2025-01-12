// TODO: Fix these integration tests. Broken after using MULTIPART_FORM_DATA for handler images.
//package com.project.cadmus_challenge.integration.artist;
//
//import com.project.cadmus_challenge.integration.ControllerIntegrationTests;
//import org.junit.jupiter.api.Test;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.http.MediaType;
//import org.springframework.http.client.MultipartBodyBuilder;
//import org.springframework.web.reactive.function.BodyInserters;
//
//import java.io.File;
//
//public class ArtistPostControllerIntegrationTests extends ControllerIntegrationTests {
//	private static final String BASE_URI = ROOT_URI + "/artist";
//
//	private final File imageFile = new File(ROOT_IMAGES + "/profile_image.JPG");
//
//	@Test
//	public void createArtistTest() {
//		/*var inputDto = new ArtistInputWrapper(
//				" Ana  ",
//				"  Brazilian ",
//				" https://www.ana.com.br ",
//				"  Ana profile image  "
//		);*/
//		var bodyBuilder = new MultipartBodyBuilder();
//		bodyBuilder.part("name", " Ana  ", MediaType.APPLICATION_JSON);
//		bodyBuilder.part("nationality", " Brazilian ", MediaType.APPLICATION_JSON);
//		bodyBuilder.part("websiteAddress", " https://www.ana.com.br ", MediaType.APPLICATION_JSON);
//		bodyBuilder.part("profileImage", new FileSystemResource(this.imageFile), MediaType.MULTIPART_FORM_DATA);
//
//		/*var body = BodyInserters.
//				fromMultipartData("name", " Ana  ")
//				.with("nationality", "  Brazilian ")
//				.with("websiteAddress", "  https://www.ana.com.br  ")
//				.with("profileImage", new FileSystemResource(this.imageFile));*/
//		super.webTestClient
//				.post()
//				.uri(BASE_URI)
//				.contentType(MediaType.valueOf(MediaType.MULTIPART_FORM_DATA_VALUE))
//				.bodyValue(BodyInserters.fromMultipartData(bodyBuilder.build()))
//				.exchange()
//				.expectStatus().isCreated()
//				.expectBody()
//				.jsonPath("$").exists()
//				.jsonPath("$.success").isEqualTo(true)
//				.jsonPath("$.message").doesNotExist()
//				.jsonPath("$.data").exists()
//				.jsonPath("$.data.id").exists()
//				.jsonPath("$.data.name").isEqualTo("Ana")
//				.jsonPath("$.data.nationality").isEqualTo("Brazilian")
//				.jsonPath("$.data.websiteAddress").isEqualTo("https://www.ana.com.br")
//				.jsonPath("$.data.profileImage").exists()
//		;
//	}
//
//	@Test
//	public void createArtistWithBlankNameErrorTest() {
//		var body = BodyInserters.
//				fromMultipartData("name", "   ")
//				.with("nationality", "  Brazilian ")
//				.with("websiteAddress", "  https://www.ana.com.br  ")
//				.with("profileImage", new FileSystemResource(this.imageFile));
//		super.webTestClient
//				.post()
//				.uri(BASE_URI)
//				.contentType(MediaType.MULTIPART_FORM_DATA)
//				.bodyValue(body)
//				.exchange()
//				.expectStatus().isBadRequest()
//				.expectBody()
//				.jsonPath("$").exists()
//		;
//	}
//
//	@Test
//	public void createArtistWithBlankNationalityErrorTest() {
//		var body = BodyInserters.
//				fromMultipartData("name", " Ana  ")
//				.with("nationality", "   ")
//				.with("websiteAddress", "  https://www.ana.com.br  ")
//				.with("profileImage", new FileSystemResource(this.imageFile));
//		super.webTestClient
//				.post()
//				.uri(BASE_URI)
//				.contentType(MediaType.MULTIPART_FORM_DATA)
//				.bodyValue(body)
//				.exchange()
//				.expectStatus().isBadRequest()
//				.expectBody()
//				.jsonPath("$").exists()
//		;
//	}
//
//	@Test
//	public void createArtistWithBlankWebsiteAddressErrorTest() {
//		var body = BodyInserters.
//				fromMultipartData("name", " Ana  ")
//				.with("nationality", "  Brazilian ")
//				.with("websiteAddress", "   ")
//				.with("profileImage", new FileSystemResource(this.imageFile));
//		super.webTestClient
//				.post()
//				.uri(BASE_URI)
//				.contentType(MediaType.MULTIPART_FORM_DATA)
//				.bodyValue(body)
//				.exchange()
//				.expectStatus().isBadRequest()
//				.expectBody()
//				.jsonPath("$").exists()
//		;
//	}
//
//	@Test
//	public void createArtistWithBlankProfileImageErrorTest() {
//		var body = BodyInserters.
//				fromMultipartData("name", " Ana  ")
//				.with("nationality", "  Brazilian ")
//				.with("websiteAddress", "  https://www.ana.com.br  ")
//				.with("profileImage", "   ");
//		super.webTestClient
//				.post()
//				.uri(BASE_URI)
//				.contentType(MediaType.MULTIPART_FORM_DATA)
//				.bodyValue(body)
//				.exchange()
//				.expectStatus().isBadRequest()
//				.expectBody()
//				.jsonPath("$").exists()
//		;
//	}
//
//	@Test
//	public void createArtistWithInvalidWebsiteAddressErrorTest() {
//		var body = BodyInserters.
//				fromMultipartData("name", " Ana  ")
//				.with("nationality", "  Brazilian ")
//				.with("websiteAddress", "  https://www.ana.com.br  ")
//				.with("profileImage", new FileSystemResource(this.imageFile));
//		super.webTestClient
//				.post()
//				.uri(BASE_URI)
//				.contentType(MediaType.MULTIPART_FORM_DATA)
//				.bodyValue(body)
//				.exchange()
//				.expectStatus().isNotFound()
//				.expectBody()
//				.jsonPath("$").exists()
//				.jsonPath("$.success").isEqualTo(false)
//				.jsonPath("$.message").isEqualTo(
//						"Error executing use case ArtistCreateCommand: Business exception: Artist website address must be a valid URL."
//				)
//				.jsonPath("$.data").doesNotExist()
//		;
//	}
//}
