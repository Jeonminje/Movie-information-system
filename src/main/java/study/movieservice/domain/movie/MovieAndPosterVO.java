package study.movieservice.domain.movie;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public class MovieAndPosterVO {
    private final Long movieId;
    private final String movieName;
    private final String genre;
    private final String actor;
    private final Integer runningTime;
    private final String supervisor;
    private final Date openingDate;
    private final String saveFilePath;
}