package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import study.movieservice.domain.movie.Movie;
import study.movieservice.domain.movie.MovieInfo;
import study.movieservice.domain.movie.RatingVO;
import study.movieservice.repository.MovieMapper;
import study.movieservice.repository.ReviewMapper;

import java.util.List;
import java.util.Optional;

import static study.movieservice.domain.ExceptionMessageConst.FAILED_BRING_DATA;
import static study.movieservice.domain.ExceptionMessageConst.ILLEGAL_MOVIE_ID;

@Service
@RequiredArgsConstructor
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

        try{
            movieOptional = Optional.ofNullable(movieMapper.getMovie(movieId));
            ratingAverage = reviewMapper.getRatingAverage(movieId);
            ratingCnt = reviewMapper.getRowCount(movieId);
            data = reviewMapper.getRatingList(movieId);
        }catch (DataAccessException e){
            throw new IllegalArgumentException(FAILED_BRING_DATA.getMessage());
        }

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
