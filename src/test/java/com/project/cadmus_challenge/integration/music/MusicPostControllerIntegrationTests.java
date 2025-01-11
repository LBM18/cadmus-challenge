package com.project.cadmus_challenge.integration.music;

import com.project.cadmus_challenge.application.bases.DurationMusic;
import com.project.cadmus_challenge.application.dtos.MusicInputDto;
import com.project.cadmus_challenge.integration.ControllerIntegrationTests;
import org.junit.jupiter.api.Test;

public class MusicPostControllerIntegrationTests extends ControllerIntegrationTests {
	private static final String BASE_URI = ROOT_URI + "/music";

	@Test
	public void createMusicTest() {
		var duration = new DurationMusic(1L, 0L, 0L);
		var inputDto = new MusicInputDto(
				" I Love You  ",
				duration,
				1L,
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
				.jsonPath("$.data.duration").isEqualTo(duration)
				.jsonPath("$.data.track").isEqualTo(inputDto.track())
				.jsonPath("$.data.albumId").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data.album").doesNotExist()
		;
	}

	@Test
	public void createMusicWithBlankTitleErrorTest() {
		var inputDto = new MusicInputDto(
				"   ",
				new DurationMusic(1L, 0L, 0L),
				1L,
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
	public void createMusicWithNullDurationErrorTest() {
		var inputDto = new MusicInputDto(
				" I Love You  ",
				null,
				1L,
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
	public void createMusicWithNullTrackErrorTest() {
		var inputDto = new MusicInputDto(
				" I Love You  ",
				new DurationMusic(1L, 0L, 0L),
				null,
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
	public void createMusicWithNullAlbumIdErrorTest() {
		var inputDto = new MusicInputDto(
				" I Love You  ",
				new DurationMusic(1L, 0L, 0L),
				1L,
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
	public void createMusicWithInvalidTrackErrorTest() {
		var inputDto = new MusicInputDto(
				" I Love You  ",
				new DurationMusic(1L, 0L, 0L),
				0L,
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
						"Error executing use case MusicCreateCommand: Business exception: Music track number must be greater than zero."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void createMusicWithInvalidDurationErrorTest() {
		var inputDto = new MusicInputDto(
				" I Love You  ",
				new DurationMusic(0L, 0L, 0L),
				1L,
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
						"Error executing use case MusicCreateCommand: Business exception: Music duration must represent valid minutes and seconds."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}
}
