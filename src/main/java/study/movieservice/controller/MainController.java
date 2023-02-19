package study.movieservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import study.movieservice.domain.movie.Movie;
import study.movieservice.service.MovieService;
import static study.movieservice.domain.ExceptionMessageConst.SUCCESS_SAVE_MOVIE;
import static study.movieservice.domain.ExceptionMessageConst.SUCCESS_SAVE_POSTER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final MovieService movieService;

    @PostMapping("/save-movies")
    @ResponseStatus(HttpStatus.CREATED)
    public String movieJoin(@RequestBody Movie movie) {
        movieService.addMovie(movie);
        return SUCCESS_SAVE_MOVIE.getMessage();
    }
    @PostMapping("/save-poster")
    @ResponseStatus(HttpStatus.CREATED)
    public String posterJoin(@RequestPart MultipartFile file,@RequestPart Long movieId){
        movieService.addPoster(file,movieId);
        return SUCCESS_SAVE_POSTER.getMessage();
    }
}
