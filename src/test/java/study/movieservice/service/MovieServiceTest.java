package study.movieservice.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import study.movieservice.domain.movie.Movie;
import study.movieservice.domain.movie.Poster;
import study.movieservice.repository.MovieMapper;
import study.movieservice.repository.PosterMapper;
import study.movieservice.service.fileIO.FileIO;

import java.io.IOException;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieMapper movieMapper;
    @Mock
    private PosterMapper posterMapper;
    @Mock
    private FileIO fileIO;
    @InjectMocks
    private MovieService movieService;
    @Captor
    ArgumentCaptor<Poster> posterCaptor;

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
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "file.txt", "text/plain", "posterImg".getBytes());
        Long movieId = 1L;
        String filePath = "/path/to/file.txt";
        Poster expectedPoster = Poster.builder().movieId(movieId).saveFilePath(filePath).build();

        doReturn(filePath).when(fileIO).uploadFile(mockMultipartFile);
        doNothing().when(posterMapper).savePoster(expectedPoster);

        //when
        movieService.addPoster(mockMultipartFile, movieId);

        //then
        verify(fileIO).uploadFile(mockMultipartFile);
        verify(posterMapper).savePoster(posterCaptor.capture());

        Poster actualPoster = posterCaptor.getValue();
        Assertions.assertThat(expectedPoster).isEqualTo(actualPoster);
    }

    @Test
    @DisplayName("포스터 저장시 예외 처리")
    void exceptionPoster() throws IOException {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("wrongFile", "wrongFile.txt", "text/plain", "posterImg".getBytes());
        Long movieId = 1L;

        doThrow(new IOException()).when(fileIO).uploadFile(mockMultipartFile);

        //when
        Assertions.assertThatThrownBy(() -> movieService.addPoster(mockMultipartFile, movieId)).isInstanceOf(IllegalArgumentException.class);

        //then
        verify(posterMapper, times(0)).savePoster(any(Poster.class));
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