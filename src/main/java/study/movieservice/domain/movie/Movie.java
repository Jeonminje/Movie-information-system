package study.movieservice.domain.movie;

import lombok.Getter;
import lombok.Builder;
import java.util.Date;

@Builder
@Getter
public class Movie {
    private final Long movieId;
    private final String movieName;
    private final String genre;
    private final String actor;
    private final Integer runningTime;
    private final String supervisor;
    private final Date openingDate;
}
