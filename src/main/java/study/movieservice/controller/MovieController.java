package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.movieservice.domain.movie.Recommend;
import study.movieservice.service.RecommendService;

import static study.movieservice.domain.ExceptionMessageConst.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final RecommendService recommendService;

    @PostMapping("/recommend-reviews")
    @ResponseStatus(HttpStatus.OK)
    public String recommendJoin(@RequestBody Recommend recommend) {
        recommendService.recommendJoin(recommend);
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
