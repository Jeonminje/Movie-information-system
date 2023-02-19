package study.movieservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import study.movieservice.domain.movie.Movie;
import study.movieservice.domain.movie.Poster;
import study.movieservice.repository.MovieMapper;
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

    public void addMovie(Movie movie) {
        movieMapper.save(movie);
    }

    public void addPoster(MultipartFile file, Long movieId) {
        try {
            String FilePath = fileIO.uploadFile(file);
            Poster poster = Poster.builder()
                    .movieId(movieId)
                    .saveFilePath(FilePath).build();

            posterMapper.savePoster(poster);
        } catch (IOException e) {
            throw new IllegalArgumentException(FAILED_FILE_RECEIVE.getMessage());
        }
    }
}