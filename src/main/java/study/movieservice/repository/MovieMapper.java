package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.movie.Movie;

@Mapper
public interface MovieMapper {

    void save(Movie movie);
}
