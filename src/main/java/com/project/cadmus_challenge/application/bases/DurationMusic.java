package com.project.cadmus_challenge.application.bases;

import jakarta.validation.constraints.NotNull;

public record DurationMusic(
        @NotNull(message = "{notNull.music.duration.seconds}")
        Long seconds,

        @NotNull(message = "{notNull.music.duration.minutes}")
        Long minutes,

        @NotNull(message = "{notNull.music.duration.hours}")
        Long hours
) {
    public DurationMusic(Long totalSeconds) {
        this(
                totalSeconds % 60, // Seconds
                (totalSeconds % 3600) / 60, // Minutes
                totalSeconds / 3600 // Hours
        );
    }

    public Long toTotalSeconds() {
        if (
                this.seconds < 0 || this.minutes < 0 || this.hours < 0
                        || this.seconds >= 60 || this.minutes >= 60
        ) {
            return -1L; // Will throw a business exception in the use case.
        }
        return this.hours * 3600 + this.minutes * 60 + this.seconds;
    }
}
