package study.movieservice.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import study.movieservice.domain.PagingVO;
import study.movieservice.domain.movie.*;
import study.movieservice.repository.MovieMapper;
import study.movieservice.repository.PosterMapper;
import study.movieservice.repository.ReviewMapper;
import study.movieservice.service.fileIO.FileIO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static study.movieservice.domain.ExceptionMessageConst.*;
import static study.movieservice.domain.movie.MovieListType.*;

/**
 * movie, poster와 관련된 비지니스 로직을 처리하는 클래스
 */
@Service
public class MovieService {
    /**
     * spring bean 자동 주입 필드
     */
    private final MovieMapper movieMapper;
    private final PosterMapper posterMapper;
    private final FileIO fileIO;
    private final ReviewMapper reviewMapper;
    /**
     * 페이지 별 영화 개수
     */
    private final Integer moviePerPage;

    public MovieService(MovieMapper movieMapper, PosterMapper posterMapper, @Qualifier("fileIOS3") FileIO fileIO, @Value("${moviePerPage}") Integer moviePerPage, ReviewMapper reviewMapper) {
        this.movieMapper = movieMapper;
        this.posterMapper = posterMapper;
        this.fileIO = fileIO;
        this.moviePerPage = moviePerPage;
        this.reviewMapper = reviewMapper;
    }

    /**
     * 입력받은 영화정보들로 movie테이블에 등록하는 메소드
     *
     * @param movie 입력받은 값들로 만든 movie객체
     */
    public void addMovie(Movie movie){
        movieMapper.save(movie);
    }

    /**
     * 등록되어있는 영화의 포스터를 movieId와 함께 포스터 테이블에 등록하는 메소드
     *
     * @param file    포스터 이미지 파일
     * @param movieId 어떤영화에 포스터를 등록할지를 알려주는 movieId
     */
    public void addPoster(MultipartFile file, Long movieId) {
        String filePath;

        try {
            filePath = fileIO.uploadFile(file);
        } catch (IOException e) {
            throw new IllegalArgumentException(FAILED_FILE_RECEIVE.getMessage());
        }

        Poster poster = Poster.builder()
                .movieId(movieId)
                .saveFilePath(filePath).build();

        posterMapper.savePoster(poster);
    }


    /**
     * 전체 영화목록 또는 현재 상영중인 영화(개봉일 1달 이내) 목록들을 불러오는 메소드
     *
     * @param currentPageNum 현재페이지를 나타내는 Integer 값
     * @param status         모든영화목록(ALL)을 가져올지 현재상영영화만 가져올지(CURRENT)에대한 String 값
     * @return PagingVO 현재페이지에 해당하는 데이터들의 값을 PagingVO 데이터타입으로 반환
     */
    public PagingVO getMovieAndPosterList(Integer currentPageNum, String status) {

        int totalPageNum;
        int startIdx = (currentPageNum - 1) * moviePerPage;
        MovieListType listStatus;

        try {
            listStatus = MovieListType.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(INVALID_STATUS.getMessage());
        }

        int total = (listStatus == ALL) ? movieMapper.getTotalRowCount()
                : movieMapper.getCurrentRowCount();
        List<MovieAndPosterVO> data = (listStatus == ALL) ? movieMapper.getAllMovieAndPosterList(startIdx, moviePerPage)
                : movieMapper.getCurrentMovieAndPosterList(startIdx, moviePerPage);

        totalPageNum = (int) Math.ceil((double) total / moviePerPage);

        PagingVO result = PagingVO.builder()
                .total(total)
                .totalPageNum(totalPageNum)
                .currentPage(currentPageNum)
                .data(data)
                .build();

        return result;
    }

    /**
     * movieId를 입력받아 해당 영화의 정보와 리뷰들의 평점을 계산하여 리턴하는 메소드
     *
     * @param movieId 정보를 받고자하는 영화의 id
     * @return movie객체의 정보와 리뷰들의 평점들의 평균
     * @throws IllegalArgumentException 만약 movieId에 해당하는 영화객체가 없을경우 발생
     */
    public MovieInfo getMovieAndRating(Long movieId){

        Optional<Movie> movieOptional = Optional.ofNullable(movieMapper.getMovie(movieId));
        Double ratingAverage = reviewMapper.getRatingAverage(movieId);
        Integer ratingCnt = reviewMapper.getRowCount(movieId);
        List<RatingVO> data = reviewMapper.getRatingList(movieId);

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