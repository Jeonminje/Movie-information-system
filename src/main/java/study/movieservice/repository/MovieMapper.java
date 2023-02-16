package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.movie.Movie;
import study.movieservice.domain.movie.Poster;

@Mapper
public interface MovieMapper {

    void save(Movie movie);

    void savePoster(Poster poster);
}
