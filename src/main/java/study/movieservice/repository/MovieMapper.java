package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.movie.Movie;
import study.movieservice.domain.movie.MovieAndPosterVO;
import study.movieservice.domain.movie.ReviewVO;

import java.util.List;

@Mapper
public interface MovieMapper {

    void save(Movie movie);

    int getTotalRowCount();

    int getCurrentRowCount();

    List<MovieAndPosterVO> getAllMovieAndPosterList(int startIdx,int moviePerPage);

    List<MovieAndPosterVO> getCurrentMovieAndPosterList(int startIdx,int moviePerPage);
    
    Movie getMovie(Long movieId);
}
