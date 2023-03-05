package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.movieservice.domain.movie.Movie;
import study.movieservice.domain.movie.MovieInfo;
import study.movieservice.domain.movie.RatingVO;
import study.movieservice.repository.MovieMapper;
import study.movieservice.repository.ReviewMapper;

import java.util.List;
import java.util.Optional;

import static study.movieservice.domain.ExceptionMessageConst.ILLEGAL_MOVIE_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final MovieMapper movieMapper;
    private final ReviewMapper reviewMapper;

    public void addMovie(Movie movie){
        movieMapper.save(movie);
    }

    public MovieInfo getMovieAndRating(Long movieId){

        Optional<Movie> movieOptional;
        Double ratingAverage;
        Integer ratingCnt;
        List<RatingVO> data;

        movieOptional = Optional.ofNullable(movieMapper.getMovie(movieId));
        ratingAverage = reviewMapper.getRatingAverage(movieId);
        ratingCnt = reviewMapper.getRowCount(movieId);
        data = reviewMapper.getRatingList(movieId);

        if(!movieOptional.isPresent()){
            throw new IllegalArgumentException(ILLEGAL_MOVIE_ID.getMessage());
        }

        MovieInfo movieInfo = MovieInfo.builder()
                .movie(movieOptional.get())
                .ratingAverage(ratingAverage)
                .ratingCnt(ratingCnt)
                .data(data)
                .build();

        return movieInfo;
    }
}
