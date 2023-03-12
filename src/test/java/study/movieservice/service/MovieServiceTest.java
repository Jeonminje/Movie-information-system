package study.movieservice.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import study.movieservice.domain.movie.Movie;
import study.movieservice.domain.movie.Poster;
import study.movieservice.repository.MovieMapper;
import study.movieservice.repository.PosterMapper;
import study.movieservice.service.fileIO.FileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Value("${testInputFilePath}")
    private String filePath;
    @Mock
    private MovieMapper movieMapper;
    @Mock
    private PosterMapper posterMapper;
    @Mock
    private FileIO fileIO;
    @InjectMocks
    private MovieService movieService;

    @Test
    @DisplayName("영화 저장 하기")
    void addMovie() {
        // given
        doNothing().when(movieMapper).save(any(Movie.class));
        Movie movie = getMovie();

        //when
        movieService.addMovie(movie);

        //then
        verify(movieMapper).save(movie);
    }

    @Test
    @DisplayName("포스터 저장하기")
    void addPoster() throws IOException {
        //given
        doNothing().when(posterMapper).savePoster(any(Poster.class));
        MockMultipartFile mockMultipartFile = getMockMultipartFile();

        //when
        movieService.addPoster(mockMultipartFile, 3L);

        //then
        verify(posterMapper).savePoster(any(Poster.class));
    }

    @Test
    @DisplayName("포스터 저장시 예외 처리")
    void exceptionPoster() throws IOException {
        //given
        MockMultipartFile mockMultipartFile = getWrongMockMultipartFile();
        doThrow(new IOException()).when(fileIO).uploadFile(mockMultipartFile);

        //when
        Assertions.assertThatThrownBy(() -> movieService.addPoster(mockMultipartFile, 3L)).isInstanceOf(IllegalArgumentException.class);

        //then
        verify(posterMapper, times(0)).savePoster(any(Poster.class));
    }

    private MockMultipartFile getMockMultipartFile() throws IOException {
        String fileName = "testMoviePoster";
        String contentType = "PNG";
        String filePath="C:\\Users\\plus1802\\Desktop\\winter_project\\testInputImage\\앤트맨.PNG";

        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);
    }

    private MockMultipartFile getWrongMockMultipartFile() throws IOException {
        String fileName = "wrongPoster";
        String contentType = "PNG";
        String filePath="C:\\Users\\plus1802\\Desktop\\winter_project\\testInputImage\\wrongImg.xx";

        FileInputStream fileInputStream = new FileInputStream(new File(filePath));
        return new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);
    }

    private Movie getMovie(){
        LocalDate openingDate = LocalDate.of(2007,11,26);

        Movie movie = Movie.builder()
                .movieName("앤트맨슈퍼맨마리오")
                .genre("코믹/느와르")
                .actor("황정민, 조진웅, 김병만")
                .runningTime(85)
                .supervisor("봉준호")
                .openingDate(openingDate)
                .build();
        return movie;
    }
}