package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.movieservice.domain.SessionConst;
import study.movieservice.domain.movie.Review;
import study.movieservice.domain.movie.ReviewVO;
import study.movieservice.repository.ReviewMapper;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static study.movieservice.domain.ExceptionMessageConst.FAILED_BRING_REVIEW;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final ReviewMapper reviewMapper;
    private final HttpSession httpSession;

    public void saveReview(Review reviewDto) {
        Long memberId = (Long) httpSession.getAttribute(SessionConst.MEMBER_ID);

        Review review = Review.builder()
                .movieId(reviewDto.getMovieId())
                .memberId(memberId)
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating()).build();
        reviewMapper.save(review);
    }

    public List<Map<String, Object>> getReviewList(Integer currentPageNum){

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        try{
            int total = reviewMapper.getTotalRowCount();
            int reviewPerPage = 10;
            int totalPageNum = (int) Math.ceil((double) total/reviewPerPage);
            int startIdx = (currentPageNum - 1) * reviewPerPage;
            List<ReviewVO> data = reviewMapper.getReviewList(startIdx);

            result.add(makeMap("total", total));
            result.add(makeMap("totalPageNum", totalPageNum));
            result.add(makeMap("currentPage", currentPageNum));
            result.add(makeMap("data", data));
        } catch (Exception e){
            throw  new IllegalArgumentException(FAILED_BRING_REVIEW.getMessage());
        }

        return result;
    }

    private Map<String, Object> makeMap(String s, Object o){
        Map<String, Object> map = new HashMap<>();
        map.put(s, o);
        return map;
    }
}
