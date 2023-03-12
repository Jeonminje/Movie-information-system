package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import study.movieservice.domain.movie.Movie;
import study.movieservice.domain.movie.MovieInfo;
import study.movieservice.domain.movie.RatingVO;
import study.movieservice.domain.movie.Poster;
import study.movieservice.repository.MovieMapper;
import study.movieservice.repository.ReviewMapper;

import java.util.List;
import java.util.Optional;

import static study.movieservice.domain.ExceptionMessageConst.ILLEGAL_MOVIE_ID;
import study.movieservice.repository.PosterMapper;
import study.movieservice.service.fileIO.FileIO;
import java.io.IOException;
import static study.movieservice.domain.ExceptionMessageConst.FAILED_FILE_RECEIVE;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieMapper movieMapper;
    private final PosterMapper posterMapper;
    private final FileIO fileIO;
    private final ReviewMapper reviewMapper;

    public void addMovie(Movie movie){
        movieMapper.save(movie);
    }

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