package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.movieservice.domain.movie.Movie;
import study.movieservice.service.MovieService;
import static study.movieservice.domain.ExceptionMessageConst.SUCCESS_SAVE_MOVIE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final MovieService movieService;

    @PostMapping("/save-movies")
    @ResponseStatus(HttpStatus.OK)
    public String memberJoin(@RequestBody Movie movie) {
        movieService.addMovie(movie);
        return SUCCESS_SAVE_MOVIE.getMessage();
    }
}
