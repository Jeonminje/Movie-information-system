package study.movieservice.domain.movie;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 리뷰 목록 반환 시 사용하는 리뷰 VO
 */
@Getter
@RequiredArgsConstructor
public class ReviewVO {
    private final Long reviewId;
    private final Long movieId;
    private final String loginId;
    private final Long memberId;
    private final String nickname;
    private final String content;
    private final Double rating;
    private final Long likeCount;
    private final Long unlikeCount;
}
