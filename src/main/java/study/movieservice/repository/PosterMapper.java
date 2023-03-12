package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.movie.Poster;

@Mapper
public interface PosterMapper {

    void savePoster(Poster poster);
}
