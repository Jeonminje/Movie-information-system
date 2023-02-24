package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.movieservice.domain.movie.Recommend;
import study.movieservice.repository.RecommendMapper;

import static study.movieservice.domain.ExceptionMessageConst.*;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendMapper recommendMapper;
    private final SessionManager sessionManager;

    /**
     * 추천입력을 받았을때 review 테이블의 해당객체를 등록하는 메소드
     *
     * @param inputRecommend 어떤 리뷰인지 알려주는 reviewId와
     *                       1일때 추천, 0일때 비추천을 나타내는 recommendState 정보보가담겨있는 Recommend 객체
     * @throws IllegalArgumentException 만약 reviewId, memberId가 일치하는 객체가 DB에 없을때 발생
     */
    public void recommendSave(Recommend inputRecommend) {
        Long memberId = sessionManager.getMemberId();

        if (recommendMapper.findByReviewIdAndMemberId(inputRecommend.getReviewId(), memberId))
            throw new IllegalArgumentException(FAILED_RECOMMEND_REQUEST.getMessage());

        Recommend recommend = Recommend.builder()
                .memberId(memberId)
                .reviewId(inputRecommend.getReviewId())
                .recommendState(inputRecommend.getRecommendState())
                .build();

        recommendMapper.recommendSave(recommend);
    }

    /**
     * 추천 수정을 입력받았을때 review 테이블의 해당객체를 수정하는 메소드
     *
     * @param inputRecommend 어떤 리뷰인지 알려주는 reviewId와
     *                       1일때 추천, 0일때 비추천을 나타내는 recommendState 정보보가담겨있는 Recommend 객체
     * @throws IllegalArgumentException 만약 reviewId, memberId가 일치하는 객체가 DB에 없을때 발생
     */
    public void recommendUpdate(Recommend inputRecommend) {
        Long memberId = sessionManager.getMemberId();

        if (recommendMapper.findByReviewIdAndMemberId(inputRecommend.getReviewId(), memberId))
            throw new IllegalArgumentException(FAILED_RECOMMEND_REQUEST.getMessage());
        recommendMapper.recommendUpdate(inputRecommend, memberId);
    }

    /**
     * 추천 삭제를 요청받았을때 review 테이블의 해당객체를 삭제하는 메소드
     *
     * @param inputRecommend 어떤 리뷰인지 알려주는 reviewId와
     *                       1일때 추천, 0일때 비추천을 나타내는 recommendState 정보보가담겨있는 Recommend 객체
     * @throws IllegalArgumentException 만약 reviewId, memberId가 일치하는 객체가 DB에 없을때 발생
     */
    public void recommendDelete(Recommend inputRecommend) {
        Long memberId = sessionManager.getMemberId();

        if (recommendMapper.findByReviewIdAndMemberId(inputRecommend.getReviewId(), memberId))
            throw new IllegalArgumentException(FAILED_RECOMMEND_REQUEST.getMessage());
        recommendMapper.recommendDelete(memberId, inputRecommend.getReviewId());
    }
}
