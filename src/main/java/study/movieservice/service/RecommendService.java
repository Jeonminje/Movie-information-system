package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.movieservice.domain.SessionConst;
import study.movieservice.domain.movie.Recommend;
import study.movieservice.repository.RecommendMapper;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendMapper recommendMapper;
    private final HttpSession httpSession;

    public void recommendHandle(Recommend inputRecommend){

        Long memberId = (Long) httpSession.getAttribute(SessionConst.MEMBER_ID);
        Long reviewId=inputRecommend.getReviewId();

        if(recommendMapper.findByReviewIdAndMemberId(memberId,reviewId).isPresent()){
            recommendUpdate(inputRecommend, memberId, reviewId);
        }
        else {
            reviewJoin(inputRecommend, memberId);
        }
    }

    private void recommendUpdate(Recommend inputRecommend, Long memberId, Long reviewId) {
        Recommend saved=(recommendMapper.findByReviewIdAndMemberId(memberId, reviewId)).get();

        if(saved.getRecommendState()!= inputRecommend.getRecommendState()){
            recommendMapper.reviewUpdate(inputRecommend);
        }
        else{
            recommendMapper.deleteRecommend(memberId, reviewId);
        }
    }

    private void reviewJoin(Recommend inputRecommend, Long memberId) {
        Recommend recommend = Recommend.builder()
                .memberId(memberId)
                .reviewId(inputRecommend.getReviewId())
                .recommendState(inputRecommend.getRecommendState())
                .build();

        recommendMapper.reviewJoin(recommend);
    }
}
