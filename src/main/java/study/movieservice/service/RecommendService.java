package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.movieservice.domain.SessionConst;
import study.movieservice.domain.movie.Recommend;
import study.movieservice.repository.RecommendMapper;

import javax.servlet.http.HttpSession;

import static study.movieservice.domain.ExceptionMessageConst.*;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendMapper recommendMapper;
    private final SessionManager sessionManager;

    public void recommendJoin(Recommend inputRecommend) {
        if (recommendMapper.findByReviewIdAndMemberId(inputRecommend.getReviewId(), inputRecommend.getMemberId()).isEmpty()) {

            Long memberId = sessionManager.getMemberId();

            Recommend recommend = Recommend.builder()
                    .memberId(memberId)
                    .reviewId(inputRecommend.getReviewId())
                    .recommendState(inputRecommend.getRecommendState())
                    .build();

            recommendMapper.reviewJoin(recommend);
        }
        else throw new IllegalArgumentException(FAILED_RECOMMEND_REQUEST.getMessage());
    }

    public void recommendUpdate(Recommend inputRecommend) {
        Long memberId = sessionManager.getMemberId();

        if (recommendMapper.findByReviewIdAndMemberId(inputRecommend.getReviewId(), memberId).isPresent())
            recommendMapper.reviewUpdate(inputRecommend, memberId);
        else throw new IllegalArgumentException(FAILED_RECOMMEND_REQUEST.getMessage());
    }

    public void recommendDelete(Recommend inputRecommend) {
        Long memberId = sessionManager.getMemberId();

        if (recommendMapper.findByReviewIdAndMemberId(inputRecommend.getReviewId(), memberId).isPresent())
            recommendMapper.deleteRecommend(memberId, inputRecommend.getReviewId());
        else throw new IllegalArgumentException(FAILED_RECOMMEND_REQUEST.getMessage());
    }
}
