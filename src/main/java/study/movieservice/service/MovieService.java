package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.movieservice.domain.movie.Movie;
import study.movieservice.repository.MovieMapper;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieMapper movieMapper;

    public void addMovie(Movie inputMovie){
        Movie movie = Movie.builder()
                .movieName(inputMovie.getMovieName())
                .genre(inputMovie.getGenre())
                .actor(inputMovie.getActor())
                .runningTime(inputMovie.getRunningTime())
                .supervisor(inputMovie.getSupervisor())
                .openingDate(inputMovie.getOpeningDate()).build();

        movieMapper.save(movie);
    }
}
