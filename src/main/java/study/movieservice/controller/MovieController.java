package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.movieservice.domain.movie.Recommend;
import study.movieservice.service.RecommendService;

import static study.movieservice.domain.ExceptionMessageConst.SUCCESS_RECOMMEND_UPDATE;


@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final RecommendService recommendService;

    @PostMapping("/recommend-reviews")
    @ResponseStatus(HttpStatus.OK)
    public String recommendUpdate(@RequestBody Recommend recommend) {
        recommendService.recommendHandle(recommend);
        return SUCCESS_RECOMMEND_UPDATE.getMessage();
    }
}
