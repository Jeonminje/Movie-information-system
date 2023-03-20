package study.movieservice.domain.movie;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@EqualsAndHashCode
public class Movie {
    private final Long movieId;
    private final String movieName;
    private final String genre;
    private final String actor;
    private final Integer runningTime;
    private final String supervisor;
    private final LocalDate openingDate;
}
