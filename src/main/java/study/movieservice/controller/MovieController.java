package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.movieservice.controller.aop.annotation.LoginCheck;
import study.movieservice.domain.PagingVO;
import study.movieservice.domain.movie.Review;
import study.movieservice.service.ReviewService;

import static study.movieservice.domain.ExceptionMessageConst.*;

import study.movieservice.domain.movie.Recommend;
import study.movieservice.service.RecommendService;

import static study.movieservice.domain.ExceptionMessageConst.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final ReviewService reviewService;

    @PostMapping("/review")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck
    public String saveReview(@RequestBody Review review){
        reviewService.saveReview(review);
        return SUCCESS_SAVE_REVIEW.getMessage();
    }

    @DeleteMapping ("/review")
    @ResponseStatus(HttpStatus.OK)
    @LoginCheck
    public String deleteReview(@RequestParam Long reviewId){
        reviewService.deleteReview(reviewId);
        return SUCCESS_DELETE_REVIEW.getMessage();
    }

    @GetMapping("/review")
    @ResponseStatus(HttpStatus.OK)
    @LoginCheck
    public PagingVO getReviewList(@RequestParam(defaultValue = "1") Integer currentPageNum){

        return reviewService.getReviewList(currentPageNum);
    }


    private final RecommendService recommendService;

    @PostMapping("/recommend-reviews")
    @ResponseStatus(HttpStatus.OK)
    public String recommendJoin(@RequestBody Recommend recommend) {
        recommendService.recommendSave(recommend);
        return SUCCESS_RECOMMEND_JOIN.getMessage();
    }

    @PatchMapping("/recommend-reviews")
    @ResponseStatus(HttpStatus.OK)
    public String recommendUpdate(@RequestBody Recommend recommend) {
        recommendService.recommendUpdate(recommend);
        return SUCCESS_RECOMMEND_UPDATE.getMessage();
    }

    @DeleteMapping("/recommend-reviews")
    @ResponseStatus(HttpStatus.OK)
    public String recommendDelete(@RequestBody Recommend recommend) {
        recommendService.recommendDelete(recommend);
        return SUCCESS_RECOMMEND_DELETE.getMessage();
    }
}
