package study.movieservice.domain.movie;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 포스터 정보를 담는 DTO
 */
@Getter
@Builder
@EqualsAndHashCode
public class Poster {
    private final Long posterId;
    private final Long movieId;
    private final String saveFilePath;
}
