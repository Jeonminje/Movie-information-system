package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import study.movieservice.domain.SessionConst;
import study.movieservice.domain.movie.Review;
import study.movieservice.repository.ReviewMapper;

import javax.servlet.http.HttpSession;

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

    public JSONObject getReviewList(Integer currentPageNum){

        int total = reviewMapper.getTotalRowCount();
        int reviewPerPage = 10;
        int totalPageNum = (int) Math.ceil((double) total/reviewPerPage);
        int startIdx = (currentPageNum - 1) * reviewPerPage;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", total);
        jsonObject.put("totalPageNum", totalPageNum);
        jsonObject.put("currentPage", currentPageNum);
        jsonObject.put("data", reviewMapper.getReviewList(startIdx));

        return jsonObject;
    }
}
