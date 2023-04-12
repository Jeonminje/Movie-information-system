package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.movie.Poster;

/**
 * DB와 poster 객체들을 연결해주는 인터페이스
 */
@Mapper
public interface PosterMapper {
    /**
     * 포스터를 저장하기 위한 메소드
     *
     * @param poster 입력받은 poster 정보
     */
    void savePoster(Poster poster);
}
