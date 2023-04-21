package study.movieservice.repository;

import org.apache.ibatis.annotations.Mapper;
import study.movieservice.domain.movie.Movie;
import study.movieservice.domain.movie.MovieAndPosterVO;
import study.movieservice.domain.movie.ReviewVO;

import java.util.List;

/**
 * DB와 movie 관련 객체들을 연결해주는 인터페이스
 */
@Mapper
public interface MovieMapper {
    /**
     * movie 객체내용을 DB에 저장하는 메소드
     *
     * @param movie 입력받은 movie 객체
     */
    void save(Movie movie);

    /**
     * movieId를 통해 movie 객체를 DB에서 불러오는 메소드
     *
     * @param movieId 찾고자 하는 movie 객체의 ID값
     * @return movieId를 통해 찾은 movie 객체
     */
    Movie getMovie(Long movieId);

    /**
     * 전체 상영중인 영화 갯수 리턴하는 메소드
     *
     * @return 전체 상영중인 영화의 총 갯수
     */
    int getTotalRowCount();

    /**
     * 현재 상영중인 영화 갯수 리턴하는 메소드
     *
     * @return 현재 상영중인 영화의 총 갯수
     */
    int getCurrentRowCount();

    /**
     * 전체 영화와 그에 매칭되는 포스터 경로까지 불러오는 메소드
     *
     * @param startIdx 페이지 넘버
     * @param moviePerPage 한 페이지당 들어갈 영화 갯수
     * @return 전체 영화와 포스터정보를 가지고있는 List
     */
    List<MovieAndPosterVO> getAllMovieAndPosterList(int startIdx,int moviePerPage);
    /**
     * 현재 상영중인 영화와 그에 매칭되는 포스터 경로까지 불러오는 메소드
     *
     * @param startIdx 페이지 넘버
     * @param moviePerPage 한 페이지당 들어갈 영화 갯수
     * @return 현재 상영중인 영화와 포스터정보를 가지고있는 List
     */
    List<MovieAndPosterVO> getCurrentMovieAndPosterList(int startIdx,int moviePerPage);
}
