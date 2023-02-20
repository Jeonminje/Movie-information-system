package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.movieservice.controller.aop.annotation.LoginCheck;
import study.movieservice.domain.PagingVO;
import study.movieservice.domain.movie.Review;
import study.movieservice.service.MovieService;

import static study.movieservice.domain.ExceptionMessageConst.SUCCESS_SAVE_REVIEW;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/review")
    @ResponseStatus(HttpStatus.OK)
    @LoginCheck
    public String saveReview(@RequestBody Review review){
        movieService.saveReview(review);
        return SUCCESS_SAVE_REVIEW.getMessage();
    }

    @GetMapping("/review/list")
    @ResponseStatus(HttpStatus.OK)
    @LoginCheck
    public PagingVO getReviewList(@RequestParam(defaultValue = "1") Integer currentPageNum){

        return movieService.getReviewList(currentPageNum);
    }
}
