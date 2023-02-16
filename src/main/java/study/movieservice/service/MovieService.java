package study.movieservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import study.movieservice.domain.movie.Movie;
import study.movieservice.domain.movie.Poster;
import study.movieservice.repository.MovieMapper;
import java.io.File;
import java.io.IOException;

import static study.movieservice.domain.ExceptionMessageConst.FAILED_FILE_RECEIVE;

@Service
public class MovieService {

    private final MovieMapper movieMapper;
    private final String fixedPath;

    public MovieService(MovieMapper movieMapper,@Value("${fixedPath}") String fixedPath) {
        this.movieMapper = movieMapper;
        this.fixedPath = fixedPath;
    }

    public void addMovie(Movie inputMovie){
        Movie movie = Movie.builder()
                .movieName(inputMovie.getMovieName())
                .genre(inputMovie.getGenre())
                .actor(inputMovie.getActor())
                .runningTime(inputMovie.getRunningTime())
                .supervisor(inputMovie.getSupervisor())
                .openingDate(inputMovie.getOpeningDate()).build();

        movieMapper.save(movie);
    }

    public void addPoster(MultipartFile file,Long movieId){

        String originalFileName=file.getOriginalFilename();
        String FilePath=fixedPath+originalFileName;
        File destination=new File(FilePath);

        Poster poster = Poster.builder()
                .movieId(movieId)
                .saveFilePath(FilePath).build();

        try {
            file.transferTo(destination);
            movieMapper.savePoster(poster);
        } catch (IOException e) {
            throw new IllegalArgumentException(FAILED_FILE_RECEIVE.getMessage());
        }
    }
}
