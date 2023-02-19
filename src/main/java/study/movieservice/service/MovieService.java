package study.movieservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import study.movieservice.domain.movie.Movie;
import study.movieservice.domain.movie.Poster;
import study.movieservice.repository.MovieMapper;
import study.movieservice.repository.PosterMapper;
import study.movieservice.service.fileIO.FileIO;
import study.movieservice.service.fileIO.FileIOLocal;

@Service
public class MovieService {

    private final MovieMapper movieMapper;
    private final PosterMapper posterMapper;
    private final FileIO fileIO;

    public MovieService(MovieMapper movieMapper, PosterMapper posterMapper, FileIOLocal fileIOLocal) {
        this.movieMapper = movieMapper;
        this.posterMapper=posterMapper;
        this.fileIO=fileIOLocal;
    }

    public void addMovie(Movie movie){
        movieMapper.save(movie);
    }

    public void addPoster(MultipartFile file,Long movieId){

        String FilePath= fileIO.saveFile(file);

        Poster poster = Poster.builder()
                .movieId(movieId)
                .saveFilePath(FilePath).build();
        posterMapper.savePoster(poster);
    }
}