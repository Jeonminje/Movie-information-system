package study.movieservice.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import study.movieservice.domain.movie.Movie;
import study.movieservice.repository.MovieMapper;
import study.movieservice.repository.PosterMapper;
import study.movieservice.service.fileIO.FileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

@SpringBootTest
class MovieServiceTest {

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private PosterMapper posterMapper;
    @Autowired
    private FileIO fileIO;
    private MovieService movieService;

    @BeforeEach
    public void beforeEach(){
        movieService = new MovieService(movieMapper, posterMapper, fileIO);
    }

    @Test
    @DisplayName("영화 저장 하기")
    @Transactional
    void addMovie() {
        LocalDate openingDate = LocalDate.of(2007,11,26);

        Movie movie = Movie.builder()
                .movieName("앤트맨슈퍼맨마리오")
                .genre("코믹/느와르")
                .actor("황정민, 조진웅, 김병만")
                .runningTime(85)
                .supervisor("봉준호")
                .openingDate(openingDate)
                .build();

        movieService.addMovie(movie);
        Long movieId = movie.getMovieId();

        Movie find = movieMapper.getMovie(movieId);

        Assertions.assertThat(movie).isEqualTo(find);
    }

    @Test
    @DisplayName("포스터 저장하기")
    void addPoster() throws IOException {

        String fileName = "testMoviePoster";
        String contentType = "PNG";
        String filePath = "C:\\Users\\plus1802\\Desktop\\winter_project\\testInputImage\\앤트맨.PNG";

        MockMultipartFile mockMultipartFile = getMockMultipartFile(fileName, contentType, filePath);

        movieService.addPoster(mockMultipartFile, 3L);

        String getFileName = mockMultipartFile.getOriginalFilename().toLowerCase();
        String fileName2 = fileName.toLowerCase() + "." + contentType;

        Assertions.assertThat(getFileName.toLowerCase()).isEqualTo(fileName2.toLowerCase());
    }

    private MockMultipartFile getMockMultipartFile(String fileName, String contentType, String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);
    }
}