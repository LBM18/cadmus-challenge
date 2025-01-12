package com.project.cadmus_challenge.unit.bases;

import com.project.cadmus_challenge.application.bases.DurationMusic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DurationMusicTests {

    @Test
    void durationMusicShouldConvertTotalSecondsToDurationCorrectlyTest() {
        var duration = new DurationMusic(7265L);

        assertEquals(5, duration.seconds());
        assertEquals(1, duration.minutes());
        assertEquals(2, duration.hours());
    }

    @Test
    void durationMusicShouldHandleZeroDurationTest() {
        var duration = new DurationMusic(0L);

        assertEquals(0, duration.seconds());
        assertEquals(0, duration.minutes());
        assertEquals(0, duration.hours());
    }

    @Test
    void durationMusicShouldConvertDurationToTotalSecondsCorrectlyTest() {
        var duration = new DurationMusic(5L, 1L, 2L);

        assertEquals(7265L, duration.toTotalSeconds());
    }

    @Test
    void durationMusicShouldThrowExceptionForNegativeHoursTest() {
        var duration = new DurationMusic(0L, 0L, -1L);

        assertEquals(-1L, duration.toTotalSeconds());
    }

    @Test
    void durationMusicShouldThrowExceptionForNegativeMinutesTest() {
        var duration = new DurationMusic(-1L, 0L, 0L);

        assertEquals(-1L, duration.toTotalSeconds());
    }

    @Test
    void durationMusicShouldThrowExceptionForNegativeSecondsTest() {
        var duration = new DurationMusic(0L, -1L, 0L);

        assertEquals(-1L, duration.toTotalSeconds());
    }

    @Test
    void durationMusicShouldThrowExceptionForInvalidMinutesTest() {
        var duration = new DurationMusic(0L, 60L, 0L);

        assertEquals(-1L, duration.toTotalSeconds());
    }

    @Test
    void durationMusicShouldThrowExceptionForInvalidSecondsTest() {
        var duration = new DurationMusic(60L, 0L, 0L);

        assertEquals(-1L, duration.toTotalSeconds());
    }

    @Test
    void durationMusicShouldConvertLargeDurationToTotalSecondsTest() {
        var duration = new DurationMusic(59L, 59L, 100L);

        assertEquals(363599L, duration.toTotalSeconds());
    }

    @Test
    void durationMusicShouldHandleMaxValidSecondsTest() {
        var duration = new DurationMusic(59L, 59L, 0L);

        assertEquals(3599L, duration.toTotalSeconds());
    }

    @Test
    void durationMusicShouldHandleBoundaryValuesTest() {
        var duration = new DurationMusic(59L, 59L, 1L);

        assertEquals(7199L, duration.toTotalSeconds());
    }
}
