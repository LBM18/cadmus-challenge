package com.project.cadmus_challenge.integration.music;

import com.project.cadmus_challenge.application.bases.DurationMusic;
import com.project.cadmus_challenge.application.dtos.MusicInputDto;
import com.project.cadmus_challenge.integration.ControllerIntegrationTests;
import org.junit.jupiter.api.Test;

public class MusicPutControllerIntegrationTests extends ControllerIntegrationTests {
	private static final String BASE_URI = ROOT_URI + "/music/{id}";

	@Test
	public void updateMusicTest() {
		var duration = new DurationMusic(0L, 1L, 0L);
		var inputDto = new MusicInputDto(
				" Imagine ",
				duration,
				2L,
				PRE_EXISTING_ID
		);
		super.webTestClient
				.put()
				.uri(BASE_URI, PRE_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isAccepted()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(true)
				.jsonPath("$.message").doesNotExist()
				.jsonPath("$.data").exists()
				.jsonPath("$.data.id").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data.title").isEqualTo(inputDto.title().trim())
				.jsonPath("$.data.duration").isEqualTo(duration)
				.jsonPath("$.data.track").isEqualTo(inputDto.track())
				.jsonPath("$.data.albumId").isEqualTo(PRE_EXISTING_ID)
				.jsonPath("$.data.album").doesNotExist()
		;
	}

	@Test
	public void updateMusicWithBlankTitleErrorTest() {
		var inputDto = new MusicInputDto(
				"   ",
				new DurationMusic(0L, 1L, 0L),
				2L,
				PRE_EXISTING_ID
		);
		super.webTestClient
				.put()
				.uri(BASE_URI, PRE_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}

	@Test
	public void updateMusicWithNullDurationErrorTest() {
		var inputDto = new MusicInputDto(
				" Imagine ",
				null,
				2L,
				PRE_EXISTING_ID
		);
		super.webTestClient
				.put()
				.uri(BASE_URI, PRE_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}

	@Test
	public void updateMusicWithNullTrackErrorTest() {
		var inputDto = new MusicInputDto(
				" Imagine ",
				new DurationMusic(0L, 1L, 0L),
				null,
				PRE_EXISTING_ID
		);
		super.webTestClient
				.put()
				.uri(BASE_URI, PRE_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}

	@Test
	public void updateMusicWithNullAlbumIdErrorTest() {
		var inputDto = new MusicInputDto(
				" Imagine ",
				new DurationMusic(0L, 1L, 0L),
				2L,
				null
		);
		super.webTestClient
				.put()
				.uri(BASE_URI, PRE_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}

	@Test
	public void updateMusicWithNonExistentIdErrorTest() {
		var inputDto = new MusicInputDto(
				" Imagine ",
				new DurationMusic(0L, 1L, 0L),
				2L,
				PRE_EXISTING_ID
		);
		super.webTestClient
				.put()
				.uri(BASE_URI, NON_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case MusicEditCommand: Entity not found exception: Music ID " +
								NON_EXISTING_ID + " not found in the system."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void updateMusicWithInvalidIdErrorTest() {
		var inputDto = new MusicInputDto(
				" Imagine ",
				new DurationMusic(0L, 1L, 0L),
				2L,
				PRE_EXISTING_ID
		);
		super.webTestClient
				.put()
				.uri(BASE_URI, INVALID_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.jsonPath("$").exists()
		;
	}

	@Test
	public void updateMusicWithInvalidTrackErrorTest() {
		var inputDto = new MusicInputDto(
				" Imagine ",
				new DurationMusic(0L, 1L, 0L),
				0L,
				PRE_EXISTING_ID
		);
		super.webTestClient
				.put()
				.uri(BASE_URI, PRE_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case MusicEditCommand: Business exception: Music track number must be greater than zero."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}

	@Test
	public void updateMusicWithInvalidDurationErrorTest() {
		var inputDto = new MusicInputDto(
				" Imagine ",
				new DurationMusic(-1L, -1L, -1L),
				2L,
				PRE_EXISTING_ID
		);
		super.webTestClient
				.put()
				.uri(BASE_URI, PRE_EXISTING_ID)
				.bodyValue(inputDto)
				.exchange()
				.expectStatus().isNotFound()
				.expectBody()
				.jsonPath("$").exists()
				.jsonPath("$.success").isEqualTo(false)
				.jsonPath("$.message").isEqualTo(
						"Error executing use case MusicEditCommand: Business exception: Music duration must represent valid minutes and seconds."
				)
				.jsonPath("$.data").doesNotExist()
		;
	}
}
