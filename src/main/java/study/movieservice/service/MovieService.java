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

    public void addMovie(Movie movie){
        movieMapper.save(movie);
    }

    public void addPoster(MultipartFile file,Long movieId){

        String FilePath=saveFile(file);

        Poster poster = Poster.builder()
                .movieId(movieId)
                .saveFilePath(FilePath).build();
        movieMapper.savePoster(poster);
    }

    public String saveFile(MultipartFile file){

        String originalFileName=file.getOriginalFilename();
        String FilePath=fixedPath+originalFileName;
        File destination=new File(FilePath);

        try {
            file.transferTo(destination);
        } catch (IOException e) {
            throw new IllegalArgumentException(FAILED_FILE_RECEIVE.getMessage());
        }
        return FilePath;
    }
}