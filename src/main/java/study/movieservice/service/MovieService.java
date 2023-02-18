package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.movieservice.domain.movie.Movie;
import study.movieservice.repository.MovieMapper;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieMapper movieMapper;

    public void addMovie(Movie movie){
        movieMapper.save(movie);
    }
}
