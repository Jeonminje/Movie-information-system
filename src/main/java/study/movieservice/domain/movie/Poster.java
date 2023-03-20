package study.movieservice.domain.movie;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Poster {
    private final Long posterId;
    private final Long movieId;
    private final String saveFilePath;
}
